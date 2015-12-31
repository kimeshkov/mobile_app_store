package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.service.util.ApplicationData;
import com.dataart.springtraining.app.service.util.UploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mkim on 17/10/2015.
 */
public interface ApplicationService {

    UploadResult uploadApplication(ApplicationData data, MultipartFile multipartFile);

}
