package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.model.FileStoreData;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by mkim on 29/10/2015.
 */
public interface FileSaver {

    FileStoreData saveZipFile(Path applicationFile, String packageName) throws IOException;

    FileStoreData saveImage128(Path image, String packageName) throws IOException;

    FileStoreData saveImage512(Path image, String packageName) throws IOException;

}
