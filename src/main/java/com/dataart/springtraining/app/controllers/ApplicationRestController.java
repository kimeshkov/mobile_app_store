package com.dataart.springtraining.app.controllers;

import com.dataart.springtraining.app.model.Application;
import com.dataart.springtraining.app.model.Category;
import com.dataart.springtraining.app.model.FileStoreData;
import com.dataart.springtraining.app.service.ApplicationService;
import com.dataart.springtraining.app.service.FileStore;
import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.ImageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkim on 17/10/2015.
 */

@RestController
@RequestMapping("/api/app")
public class ApplicationRestController {

    private static final String IMAGE_URL_TEMPLATE = "api/app/image/%s/%s";
    private static final String DEFAULT_IMAGE_URL_TEMPLATE = "static/%s.png";

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    FileStore fileStore;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadApplication(ApplicationData data, @RequestParam MultipartFile file) {
        applicationService.uploadApplication(data, file);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getCategories() {
        return applicationService.getCategories();
    }

    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    @ResponseBody
    public List<ApplicationData> getPopular() {
        List<ApplicationData> response = new ArrayList<>();
        for (Application application : applicationService.getMostPopular()) {
            response.add(getApplicationData(application));
        }
        return response;
    }

    private ApplicationData getApplicationData(Application application) {
        ApplicationData applicationData = new ApplicationData();

        applicationData.setDescription(application.getDescription());
        applicationData.setCategoryId(application.getCategory().getId());
        applicationData.setName(application.getName());
        applicationData.setPackageName(application.getPackageName());
        applicationData.setImage128(getImageUrl(application, ImageSize.IMAGE_128));
        applicationData.setImage512(getImageUrl(application, ImageSize.IMAGE_512));

        return applicationData;
    }

    private String getImageUrl(Application app, ImageSize size) {
        String url;
        FileStoreData image = ImageSize.IMAGE_128.equals(size) ? app.getPicture128() : app.getPicture512();
        if(image != null) {
            url = String.format(IMAGE_URL_TEMPLATE, app.getPackageName(), size.getSize());
        } else {
            url = String.format(DEFAULT_IMAGE_URL_TEMPLATE, size.getSize());
        }
        return url;
    }

    @RequestMapping("/image/{packageName}/{size}")
    @ResponseBody
    public HttpEntity<byte[]> getImage(@PathVariable String packageName,
                                       @PathVariable int size) throws IOException {
        Application application = applicationService.getApplicationByPackageName(packageName);

        byte[] image = fileStore.getFileAsByteArray(application.getPicture512());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}
