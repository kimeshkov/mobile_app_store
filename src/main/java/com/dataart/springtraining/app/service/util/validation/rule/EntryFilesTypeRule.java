package com.dataart.springtraining.app.service.util.validation.rule;

import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadError;
import com.dataart.springtraining.app.service.util.validation.ValidationContext;
import com.dataart.springtraining.app.service.util.validation.ValidationRule;

/**
 * Created by mkim on 20/10/2015.
 */
public class EntryFilesTypeRule implements ValidationRule {
    @Override
    public void validate(ValidationContext context, ApplicationData data) throws ApplicationUploadException {
        if(context.isInvalidFormatFilesPresent()) {
            throw new ApplicationUploadException(UploadError.FILE_STRUCTURE_ERROR.getMessage());
        }
    }
}
