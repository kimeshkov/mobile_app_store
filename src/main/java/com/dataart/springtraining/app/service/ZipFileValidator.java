package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by mkim on 20/10/2015.
 */
public interface ZipFileValidator {

    void validate(Path zipFile, ApplicationData validationData) throws ApplicationUploadException;

}
