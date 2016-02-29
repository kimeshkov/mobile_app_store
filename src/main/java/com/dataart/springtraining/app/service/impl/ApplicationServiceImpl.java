package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.ApplicationCategoryRepository;
import com.dataart.springtraining.app.dao.ApplicationRepository;
import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.Application;
import com.dataart.springtraining.app.model.Category;
import com.dataart.springtraining.app.model.Rate;
import com.dataart.springtraining.app.model.User;
import com.dataart.springtraining.app.service.ApplicationService;
import com.dataart.springtraining.app.service.FileStore;
import com.dataart.springtraining.app.service.ZipFileValidator;
import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadError;
import com.dataart.springtraining.app.service.util.UploadResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mkim on 17/10/2015.
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final String DOWNLOADS_FIELD = "downloads";
    private static final String CREATION_DATE_FIELD = "creationDate";

    @Autowired
    private ZipFileValidator zipFileValidator;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private ApplicationCategoryRepository applicationCategoryRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional
    @CacheEvict(value = "popular", allEntries = true)
    public UploadResult uploadApplication(ApplicationData data, MultipartFile multipartFile) {
        UploadResult result = new UploadResult();
        try {
            process(data, multipartFile);
            result.setSuccess(true);
        } catch (ApplicationUploadException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Application getByPackageName(String packageName) {
        return applicationRepository.findByPackageName(packageName);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountByCategoryId(Integer categoryId) {
        return applicationRepository.countByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getByCategoryId(Integer categoryId, Integer page, Integer size, String sortBy) {
        PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, getSortField(sortBy));
        return applicationRepository.findByCategoryId(categoryId, pageRequest);
    }

    private String getSortField(String sortBy) {
        boolean validName = DOWNLOADS_FIELD.equalsIgnoreCase(sortBy)
                || CREATION_DATE_FIELD.equalsIgnoreCase(sortBy);

        if (!StringUtils.isEmpty(sortBy) && validName) {
            return sortBy.toLowerCase();
        } else {
            return DOWNLOADS_FIELD;
        }
    }

    @Override
    @Cacheable("popular")
    @Transactional(readOnly = true)
    public List<Application> getMostPopular() {
        return applicationRepository.findFirst5ByOrderByDownloadsDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        for (Category category : applicationCategoryRepository.findAll()) {
            categories.add(category);
        }
        return categories;
    }

    @Override
    @Transactional
    public void rateByPackageName(String packageName, Integer rateValue) {
        Application application = applicationRepository.findByPackageName(packageName);
        application.addRate(createRate(rateValue));
        applicationRepository.save(application);
    }

    private Rate createRate(Integer rateValue) {
        Rate rate = new Rate();
        rate.setRateByUser(getCurrentUser());
        rate.setValue(rateValue);
        return rate;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return usersRepository.findByUsername(userDetails.getUsername());
    }

    private void process(ApplicationData data, MultipartFile multipartFile) throws ApplicationUploadException {
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

        if (data.getImage128() != null) {
            application.setPicture128(fileStore.saveImage128(fs.getPath(data.getImage128()), data.getPackageName()));
        }
        if (data.getImage512() != null) {
            application.setPicture512(fileStore.saveImage512(fs.getPath(data.getImage512()), data.getPackageName()));
        }

        application.setZipFile(fileStore.saveZipFile(zipFile, data.getPackageName()));
        application.setCreationDate(Calendar.getInstance().getTime());
        applicationRepository.save(application);
    }

}
