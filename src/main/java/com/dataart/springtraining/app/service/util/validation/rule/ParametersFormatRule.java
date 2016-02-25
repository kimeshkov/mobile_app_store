package com.dataart.springtraining.app.service.util.validation.rule;

import com.dataart.springtraining.app.dao.ApplicationRepository;
import com.dataart.springtraining.app.service.exceptions.ApplicationUploadException;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadError;
import com.dataart.springtraining.app.service.util.validation.ValidationContext;
import com.dataart.springtraining.app.service.util.validation.ValidationRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkim on 20/10/2015.
 */
public class ParametersFormatRule implements ValidationRule {
    private static final String SEPARATE_SYMBOL = ":";

    private static final String NAME = "name";
    private static final String PACKAGE = "package";
    private static final String PICTURE_128 = "picture_128";
    private static final String PICTURE_512 = "picture_512";

    private ApplicationRepository applicationRepository;

    public ParametersFormatRule(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public void validate(ValidationContext context, ApplicationData data) throws ApplicationUploadException {

        Map<String, String> parametersMap = getParametersMap(context.getTextFileLines());

        data.setName(parametersMap.get(NAME));
        data.setPackageName(parametersMap.get(PACKAGE));

        if (data.getName() == null || data.getPackageName() == null) {
            throw new ApplicationUploadException(UploadError.TEXT_FILE_FORMAT_ERROR.getMessage());
        }

        checkPackageIsUnique(parametersMap.get(PACKAGE));

        data.setImage128(parametersMap.get(PICTURE_128));
        data.setImage512(parametersMap.get(PICTURE_512));
    }

    private Map<String, String> getParametersMap(List<String> lines) throws ApplicationUploadException {
        Map<String, String> paramsMap = new HashMap<>();

        for(String line : lines) {
            String[] splitedLine = splitLine(line);
            checkSplitedLineFormat(splitedLine);
            paramsMap.put(splitedLine[0].toLowerCase(), splitedLine[1].toLowerCase());
        }

        return paramsMap;
    }

    private String[] splitLine(String line) {
        return line.split(SEPARATE_SYMBOL);
    }

    private void checkSplitedLineFormat(String[] splitedLine) throws ApplicationUploadException {
        if (splitedLine.length != 2) {
            throw new ApplicationUploadException(UploadError.TEXT_FILE_FORMAT_ERROR.getMessage());
        }
    }

    private void checkPackageIsUnique(String packageName) throws ApplicationUploadException {
        if (applicationRepository.findByPackageName(packageName) != null) {
            throw new ApplicationUploadException(UploadError.NOT_UNIQUE_PACKAGE_NAME.getMessage());
        }
    }
}
