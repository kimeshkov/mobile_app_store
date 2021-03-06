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

@RestController
@RequestMapping("/api/app")
public class ApplicationRestController {

    private static final String IMAGE_URL_TEMPLATE = "api/app/image/%s/%s";
    private static final String DEFAULT_IMAGE_URL_TEMPLATE = "static/images/%s.png";

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    FileStore fileStore;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadApplication(ApplicationData data, @RequestParam MultipartFile file) {
        applicationService.uploadApplication(data, file);
    }

    @RequestMapping(value = "/download/{packageName}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> downloadApplication(@PathVariable String packageName) throws IOException {
        Application application = applicationService.downloadByPackageName(packageName);

        byte[] zipFile = fileStore.getFileAsByteArray(application.getZipFile());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(zipFile.length);

        return new HttpEntity<>(zipFile, headers);
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

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public CountResponse getCountByCategory(@PathVariable Integer categoryId) {
        return new CountResponse(applicationService.getCountByCategoryId(categoryId));
    }

    @RequestMapping(value = "/category/{categoryId}/{page}/{size}/{sortBy}", method = RequestMethod.GET)
    @ResponseBody
    public List<ApplicationData> getByCategory(@PathVariable Integer categoryId,
                                               @PathVariable Integer page,
                                               @PathVariable Integer size,
                                               @PathVariable String sortBy) {
        List<ApplicationData> response = new ArrayList<>();
        for (Application application : applicationService.getByCategoryId(categoryId, page, size, sortBy)) {
            response.add(getApplicationData(application));
        }
        return response;
    }

    @RequestMapping(value = "/updateRate/{packageName}", method = RequestMethod.PUT)
    @ResponseBody
    public ApplicationData rate(@RequestBody ApplicationData data, @PathVariable String packageName) {
        applicationService.rateByPackageName(packageName, data.getRate());

        return getApplicationData(applicationService.getByPackageName(packageName));
    }

    @RequestMapping(value = "/application/{packageName}", method = RequestMethod.GET)
    @ResponseBody
    public ApplicationData getByPackageName(@PathVariable String packageName) {
        return getApplicationData(applicationService.getByPackageName(packageName));
    }

    private ApplicationData getApplicationData(Application application) {
        ApplicationData applicationData = new ApplicationData();

        applicationData.setDescription(application.getDescription());
        applicationData.setCategoryId(application.getCategory().getId());
        applicationData.setName(application.getName());
        applicationData.setPackageName(application.getPackageName());
        applicationData.setImage128(getImageUrl(application, ImageSize.IMAGE_128));
        applicationData.setImage512(getImageUrl(application, ImageSize.IMAGE_512));
        applicationData.setRate(application.getAverageRate());
        applicationData.setReviews(application.getRates().size());
        applicationData.setDownloads(application.getDownloads());

        return applicationData;
    }

    private String getImageUrl(Application app, ImageSize size) {
        String url;
        FileStoreData image = ImageSize.IMAGE_128.equals(size) ? app.getPicture128() : app.getPicture512();
        if (image != null) {
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
        Application application = applicationService.getByPackageName(packageName);

        byte[] image;

        if (ImageSize.IMAGE_128.getSize() == size) {
            image = fileStore.getFileAsByteArray(application.getPicture128());
        } else {
            image = fileStore.getFileAsByteArray(application.getPicture512());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }

    private static class CountResponse {
        private Long count;

        public CountResponse(Long count) {
            this.count = count;
        }

        public Long getCount() {
            return count;
        }
    }
}
