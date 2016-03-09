package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;

import java.io.File;
import java.nio.file.Path;

public interface ZipFileValidator {

    void validate(Path zipFile, ApplicationData validationData) throws ApplicationUploadException;

}
