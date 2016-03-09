package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.model.FileStoreData;

import java.io.IOException;
import java.nio.file.Path;

public interface FileStore {

    FileStoreData saveZipFile(Path applicationFile, String packageName) throws IOException;

    FileStoreData saveImage128(Path image, String packageName) throws IOException;

    FileStoreData saveImage512(Path image, String packageName) throws IOException;

    byte[] getFileAsByteArray(FileStoreData fileStoreData) throws IOException;

}
