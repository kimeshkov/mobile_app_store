package com.dataart.springtraining.app.service.util;

/**
 * Created by mkim on 20/10/2015.
 */
public class UploadResult {
    private boolean isSuccess;
    private String errorMessage;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
