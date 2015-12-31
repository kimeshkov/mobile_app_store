package com.dataart.springtraining.app.service.exceptions;

/**
 * Created by mkim on 20/10/2015.
 */
public class ApplicationUploadException extends Exception {
    public ApplicationUploadException(String msg) {
        super(msg);
    }

    public ApplicationUploadException(Exception exception) {
        super(exception);
    }
}
