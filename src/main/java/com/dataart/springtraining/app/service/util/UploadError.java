package com.dataart.springtraining.app.service.util;

/**
 * Created by mkim on 20/10/2015.
 */
public enum UploadError {
    IO_ERROR("Error while reading file"),
    TEXT_FILE_FORMAT_ERROR("Text file has wrong format"),
    NOT_UNIQUE_PACKAGE_NAME("Not unique package name"),
    FILE_STRUCTURE_ERROR("Bad file structure"),
    CATEGORY_IS_NOT_VALID("Category is not exist or empty");

    private final String message;

    private UploadError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
