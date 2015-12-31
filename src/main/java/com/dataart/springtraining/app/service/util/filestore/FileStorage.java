package com.dataart.springtraining.app.service.util.filestore;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mkim on 21/10/2015.
 */

public class FileStorage {
    private static final String IMAGE_DIR_NAME = "images";
    private static final String ZIP_FILE_DIR_NAME = "zip_files";

    private Path rootPath;
    private Path imagePath;
    private Path zipFilePath;

    public FileStorage(String rootPath) throws IOException {
        this.rootPath = prepareRootDirectory(rootPath);
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

    public void saveZipFile(File zipFile) throws IOException {
        Path destination = zipFilePath.resolve(zipFile.getName());
        Files.copy(zipFile.toPath(), destination);
    }

}
