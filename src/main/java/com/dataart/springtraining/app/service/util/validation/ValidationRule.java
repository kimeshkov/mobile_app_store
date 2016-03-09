package com.dataart.springtraining.app.service.util.validation;

import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;

public interface ValidationRule {

    void validate(ValidationContext context, ApplicationData data) throws ApplicationUploadException;

}
