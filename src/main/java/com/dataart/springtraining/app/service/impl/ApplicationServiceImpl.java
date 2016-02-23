package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.ApplicationCategoryRepository;
import com.dataart.springtraining.app.dao.ApplicationRepository;
import com.dataart.springtraining.app.model.Application;
import com.dataart.springtraining.app.service.ApplicationService;
import com.dataart.springtraining.app.service.FileStore;
import com.dataart.springtraining.app.service.ZipFileValidator;
import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadError;
import com.dataart.springtraining.app.service.util.UploadResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by mkim on 17/10/2015.
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ZipFileValidator zipFileValidator;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private ApplicationCategoryRepository applicationCategoryRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public UploadResult uploadApplication(ApplicationData data, MultipartFile multipartFile) {
        UploadResult result = new UploadResult();
        try {
            proccess(data, multipartFile);
            result.setSuccess(true);
        } catch (ApplicationUploadException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public Application findApplicationByPackageName(String packageName) {
        return applicationRepository.findByPackageName(packageName);
    }

    @Override
    public List<Application> findMostPopular() {
        return applicationRepository.findFirst5ByOrderByDownloadsDesc();
    }

    private void proccess(ApplicationData data, MultipartFile multipartFile) throws ApplicationUploadException {
        try {
            Path applicationFile = convertMultipartFile(multipartFile);

            zipFileValidator.validate(applicationFile, data);

            saveData(applicationFile, data);
        } catch (IOException e) {
            throw new ApplicationUploadException(e);
        }
    }

    private Path convertMultipartFile(MultipartFile multipartFile) throws ApplicationUploadException {
        try {
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String suffix = extension.equalsIgnoreCase("") ? null : "." + extension;
            Path convFile = Files.createTempFile(null, suffix);
            multipartFile.transferTo(convFile.toFile());
            return convFile;
        } catch (IOException e) {
            throw new ApplicationUploadException(UploadError.IO_ERROR.getMessage());
        }
    }

    private void saveData(Path zipFile, ApplicationData data) throws IOException {
        FileSystem fs = FileSystems.newFileSystem(zipFile, null);

        Application application = new Application();
        application.setName(data.getName());
        application.setPackageName(data.getPackageName());
        application.setDescription(data.getDescription());
        application.setCategory(applicationCategoryRepository.findOne(data.getCategoryId()));

        if (data.getPicture128() != null) {
            application.setPicture128(fileStore.saveImage128(fs.getPath(data.getPicture128()), data.getPackageName()));
        }
        if (data.getPicture512() != null) {
            application.setPicture512(fileStore.saveImage512(fs.getPath(data.getPicture512()), data.getPackageName()));
        }

        application.setZipFile(fileStore.saveZipFile(zipFile, data.getPackageName()));

        applicationRepository.save(application);
    }

}
