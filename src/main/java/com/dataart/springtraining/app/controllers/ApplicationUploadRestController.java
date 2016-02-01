package com.dataart.springtraining.app.controllers;

import com.dataart.springtraining.app.service.ApplicationService;
import com.dataart.springtraining.app.service.util.ApplicationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mkim on 17/10/2015.
 */

@RestController
@RequestMapping("/api/upload")
public class ApplicationUploadRestController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/app", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadApplication(ApplicationData data
                                  /*@RequestParam MultipartFile file*/) {
        applicationService.uploadApplication(data, null);
    }
}
