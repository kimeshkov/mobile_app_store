package com.dataart.springtraining.config;

import com.dataart.springtraining.app.service.FileStore;
import com.dataart.springtraining.app.service.impl.FileStoreImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableCaching
@ComponentScan({"com.dataart.springtraining.app", "com.dataart.springtraining.config.util"})
@PropertySource("config.properties")
public class ApplicationConfig {

    @Value("${filestore.root}")
    public String fileStoreRootPath;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("popular")));
        return cacheManager;
    }

    @Bean(name = "fileStoreRootPath")
    public String getFileStoreRootPath() {
        return fileStoreRootPath;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

        //max upload size in bytes (10 mb):
        multipartResolver.setMaxUploadSize(10485760);
        return multipartResolver;
    }

    @Bean
    public FileStore fileStore() throws IOException {
        return new FileStoreImpl(fileStoreRootPath);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
