package com.dataart.springtraining.app.service.exceptions;

public class ApplicationUploadException extends Exception {
    public ApplicationUploadException(String msg) {
        super(msg);
    }

    public ApplicationUploadException(Exception exception) {
        super(exception);
    }
}
