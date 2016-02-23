package com.dataart.springtraining.app.service.util.validation.rule;

import com.dataart.springtraining.app.dao.ApplicationCategoryRepository;
import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadError;
import com.dataart.springtraining.app.service.util.validation.ValidationContext;
import com.dataart.springtraining.app.service.util.validation.ValidationRule;

/**
 * Created by kimeshkov on 24.02.2016.
 */
public class CategoryValidRule implements ValidationRule {
    private ApplicationCategoryRepository applicationCategoryRepository;

    public CategoryValidRule(ApplicationCategoryRepository applicationCategoryRepository) {
        this.applicationCategoryRepository = applicationCategoryRepository;
    }

    @Override
    public void validate(ValidationContext context, ApplicationData data) throws ApplicationUploadException {
        if (data.getCategoryId() == null || applicationCategoryRepository.findOne(data.getCategoryId()) == null) {
            throw new ApplicationUploadException(UploadError.CATEGORY_IS_NOT_VALID.getMessage());
        }
    }
}
