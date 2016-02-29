package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by mkim on 20/10/2015.
 */
public interface ApplicationRepository extends CrudRepository<Application, Integer> {

    Application findByPackageName(String packageName);

    List<Application> findFirst5ByOrderByDownloadsDesc();

    Long countByCategoryId(Integer categoryId);

    List<Application> findByCategoryId(Integer categoryId, Pageable pageable);

}
