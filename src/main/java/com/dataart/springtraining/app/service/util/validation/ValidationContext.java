package com.dataart.springtraining.app.service.util.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkim on 30/11/2015.
 */
public class ValidationContext {
    private int filesCount;
    private boolean isTxtFilePresent;
    private boolean isNotOnlyOneTxtFile;
    private boolean isInvalidFormatFilesPresent;

    private List<String> textFileLines = new ArrayList<>();
    private List<String> imageFileNames = new ArrayList<>();

    public int getFilesCount() {
        return filesCount;
    }

    public void increaseFilesCount(){
        filesCount++;
    }

    public boolean isTxtFilePresent() {
        return isTxtFilePresent;
    }

    public void setTxtFilePresent(boolean isTxtFilePresent) {
        this.isTxtFilePresent = isTxtFilePresent;
    }

    public boolean isNotOnlyOneTxtFile() {
        return isNotOnlyOneTxtFile;
    }

    public void setNotOnlyOneTxtFile(boolean isOnlyOneTxtFile) {
        this.isNotOnlyOneTxtFile = isOnlyOneTxtFile;
    }

    public boolean isInvalidFormatFilesPresent() {
        return isInvalidFormatFilesPresent;
    }

    public void setInvalidFormatFilesPresent(boolean isInvalidFormatFilesPresent) {
        this.isInvalidFormatFilesPresent = isInvalidFormatFilesPresent;
    }

    public List<String> getTextFileLines() {
        return textFileLines;
    }

    public void setTextFileLines(List<String> textFileLines) {
        this.textFileLines = textFileLines;
    }

    public List<String> getImageFileNames() {
        return imageFileNames;
    }

    public void setImageFileNames(List<String> imageFileNames) {
        this.imageFileNames = imageFileNames;
    }
}
