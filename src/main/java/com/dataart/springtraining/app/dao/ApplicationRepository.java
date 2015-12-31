package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mkim on 20/10/2015.
 */
public interface ApplicationRepository extends CrudRepository<Application, Integer> {

    @Query("select app from Application app where packageName = :packageName")
    Application findByPackage(@Param("packageName") String packageName);
}
