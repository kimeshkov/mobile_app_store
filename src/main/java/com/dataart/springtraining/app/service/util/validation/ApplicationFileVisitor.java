package com.dataart.springtraining.app.service.util.validation;

import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.FileTypeValidator;
import com.dataart.springtraining.app.service.util.validation.ValidationContext;
import com.dataart.springtraining.app.service.util.validation.ValidationRule;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * Created by mkim on 30/11/2015.
 */
public class ApplicationFileVisitor extends SimpleFileVisitor<Path> {
    private ValidationContext context;

    public ApplicationFileVisitor(ValidationContext context) {
        this.context = context;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        context.increaseFilesCount();

        if (FileTypeValidator.isTextFile(file)) {

            processTextFile(file, context);
        } else if (FileTypeValidator.isImageFile(file)) {
            processImageFile(file, context);
        } else {
            context.setInvalidFormatFilesPresent(true);
        }

        return super.visitFile(file, attrs);
    }

    private void processTextFile(Path file, ValidationContext context) throws IOException {
        if(!context.isTxtFilePresent()) {
            context.setTxtFilePresent(true);
            context.setTextFileLines(parseTextFile(file));
        } else {
            context.setNotOnlyOneTxtFile(true);
        }
    }

    private List<String> parseTextFile(Path file) throws IOException {
        return Files.readAllLines(file, Charset.defaultCharset());
    }

    private void processImageFile(Path file, ValidationContext context) {
        context.getImageFileNames().add(file.getFileName().toString());
    }
}
