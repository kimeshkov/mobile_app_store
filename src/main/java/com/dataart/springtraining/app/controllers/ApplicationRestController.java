package com.dataart.springtraining.app.controllers;

import com.dataart.springtraining.app.model.Application;
import com.dataart.springtraining.app.service.ApplicationService;
import com.dataart.springtraining.app.service.FileStore;
import com.dataart.springtraining.app.service.util.ApplicationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by mkim on 17/10/2015.
 */

@RestController
@RequestMapping("/api/app")
public class ApplicationRestController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    FileStore fileStore;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadApplication(ApplicationData data, @RequestParam MultipartFile file) {
        applicationService.uploadApplication(data, file);
    }

    @RequestMapping("/image/{packageName}/{size}")
    @ResponseBody
    public HttpEntity<byte[]> getPhoto(@PathVariable String packageName,
                                       @PathVariable int size) throws IOException {
        //byte[] image = org.apache.commons.io.FileUtils.readFileToByteArray(new File([YOUR PATH] + File.separator + personId + ".png"));
        Application application = applicationService.findApplicationByPackageName(packageName);

        byte[] image = fileStore.getFileAsByteArray(application.getPicture512());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}
