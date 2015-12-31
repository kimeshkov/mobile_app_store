package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.FileStoreDataRepository;
import com.dataart.springtraining.app.model.FileStoreData;
import com.dataart.springtraining.app.service.FileSaver;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mkim on 29/10/2015.
 */

public class FileSaverImpl implements FileSaver {
    private static final String IMAGE_DIR_NAME = "images";
    private static final String ZIP_FILE_DIR_NAME = "zip_files";
    private static final String PIC_128_PREFIX = "IMG_128_";
    private static final String PIC_512_PREFIX = "IMG_512_";
    private static final String ZIP_PREFIX = "ZIP_";



    private Path rootPath;
    private Path imagePath;
    private Path zipFilePath;

    @Autowired
    private FileStoreDataRepository fileStoreDataRepository;

    public FileSaverImpl(String fileStoreRootPath) throws IOException {
        this.rootPath = prepareRootDirectory(fileStoreRootPath);
        this.imagePath = prepareDirectoryInPath(this.rootPath, IMAGE_DIR_NAME);
        this.zipFilePath = prepareDirectoryInPath(this.rootPath, ZIP_FILE_DIR_NAME);
    }

    private Path prepareRootDirectory(String rootPath) throws IOException {
        Path path = Paths.get(rootPath);
        return createIfNotExist(path);
    }

    private Path prepareDirectoryInPath(Path rootPath, String dirName) throws IOException {
        Path path = rootPath.resolve(dirName);
        return createIfNotExist(path);
    }

    private Path createIfNotExist(Path path) throws IOException {
        if(Files.notExists(path)) {
            Files.createDirectories(path);
        }
        return path;
    }

    @Override
    @Transactional
    public FileStoreData saveZipFile(Path applicationFile, String packageName) throws IOException {
        Path toPath = zipFilePath.resolve(createFileName(applicationFile, packageName, ZIP_PREFIX));

        FileStoreData fileStoreData = new FileStoreData();
        fileStoreData.setFileName(rootPath.relativize(toPath).toString());
        fileStoreDataRepository.save(fileStoreData);

        Files.copy(applicationFile, toPath);
        return fileStoreData;
    }

    @Override
    @Transactional
    public FileStoreData saveImage128(Path image, String packageName) throws IOException {
        return saveImage(image, packageName, PIC_128_PREFIX);
    }

    @Override
    @Transactional
    public FileStoreData saveImage512(Path image, String packageName) throws IOException {
        return saveImage(image, packageName, PIC_512_PREFIX);
    }

    private FileStoreData saveImage(Path image, String packageName, String prefix) throws IOException {
        Path toPath = imagePath.resolve(createFileName(image, packageName, prefix));

        FileStoreData fileStoreData = new FileStoreData();
        fileStoreData.setFileName(rootPath.relativize(toPath).toString());
        fileStoreDataRepository.save(fileStoreData);

        //Copy to image folder
        Files.copy(image, toPath);

        return fileStoreData;
    }

    private String createFileName(Path image, String packageName, String prefix) {
        return prefix + packageName + "." + FilenameUtils.getExtension(image.getFileName().toString());
    }

}
