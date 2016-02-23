package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.ApplicationCategoryRepository;
import com.dataart.springtraining.app.dao.ApplicationRepository;
import com.dataart.springtraining.app.service.ZipFileValidator;
import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.validation.ApplicationFileVisitor;
import com.dataart.springtraining.app.service.util.validation.ValidationContext;
import com.dataart.springtraining.app.service.util.validation.ValidationRule;
import com.dataart.springtraining.app.service.util.validation.rule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkim on 20/10/2015.
 */

@Service
public class ZipFileValidatorImpl implements ZipFileValidator {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationCategoryRepository applicationCategoryRepository;

    @Override
    public void validate(Path zipFile, ApplicationData validationData) throws ApplicationUploadException {
        try {
            FileSystem fs = FileSystems.newFileSystem(zipFile, null);

            ValidationContext validationContext = new ValidationContext();
            Files.walkFileTree(fs.getPath("/"), new ApplicationFileVisitor(validationContext));

            for (ValidationRule rule : getValidationRules()) {
                rule.validate(validationContext, validationData);
            }
        } catch (IOException e) {
            throw new ApplicationUploadException(e);
        }


    }

    private List<ValidationRule> getValidationRules() {
        List<ValidationRule> rules = new ArrayList<>();
        rules.add(new EntryFilesCountRule());
        rules.add(new EntryFilesTypeRule());
        rules.add(new CategoryValidRule(applicationCategoryRepository));
        rules.add(new TextFilePresenceAndUniquenessRule());
        rules.add(new ParametersFormatRule(applicationRepository));
        rules.add(new ImageFilesPresenceRule());
        return rules;
    }

}
