package com.dataart.springtraining.app.service.util.validation.rule;

import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadError;
import com.dataart.springtraining.app.service.util.validation.ValidationContext;
import com.dataart.springtraining.app.service.util.validation.ValidationRule;

/**
 * Created by mkim on 01/12/2015.
 */
public class ImageFilesPresenceRule implements ValidationRule {

    @Override
    public void validate(ValidationContext context, ApplicationData data) throws ApplicationUploadException {
        boolean image128NotFound = data.getPicture128().isPresent() && !context.getImageFileNames().contains(data.getPicture128().get());
        boolean image512NotFound = data.getPicture512().isPresent() && !context.getImageFileNames().contains(data.getPicture512().get());

        if (image128NotFound || image512NotFound) {
            throw new ApplicationUploadException(UploadError.FILE_STRUCTURE_ERROR.getMessage());
        }
    }
}
