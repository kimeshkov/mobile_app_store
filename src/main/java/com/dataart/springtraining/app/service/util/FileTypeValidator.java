package com.dataart.springtraining.app.service.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple validator. Used to detect is file is text or image by file name.
 */
public class FileTypeValidator {

    public static boolean isTextFile(Path file) throws IOException {
        return Files.probeContentType(file).toLowerCase().contains("text");
    }

    public static boolean isImageFile(Path file) throws IOException {
        return Files.probeContentType(file).toLowerCase().contains("image");
    }
}
