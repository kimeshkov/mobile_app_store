package com.dataart.springtraining.config;

import com.dataart.springtraining.app.service.FileSaver;
import com.dataart.springtraining.app.service.impl.FileSaverImpl;
import com.dataart.springtraining.app.service.util.filestore.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

/**
 * Created by mkim on 17/10/2015.
 */

@Configuration
@ComponentScan({"com.dataart.springtraining.app"})
@PropertySource("config.properties")
public class ApplicationConfig {

    @Value("${filestore.root}")
    public String fileStoreRootPath;

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

        //max upload size in bytes (10 mb):
        multipartResolver.setMaxUploadSize(10485760);
        return multipartResolver;
    }

    @Bean
    public FileStorage fileStorage() throws IOException {
        return new FileStorage(fileStoreRootPath);
    }

    @Bean
    public FileSaver fileSaver() throws IOException {
        return new FileSaverImpl(fileStoreRootPath);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
