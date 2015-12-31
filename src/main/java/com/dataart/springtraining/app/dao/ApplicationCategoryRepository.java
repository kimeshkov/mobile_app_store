package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.ApplicationCategory;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mkim on 29/10/2015.
 */
public interface ApplicationCategoryRepository extends CrudRepository<ApplicationCategory, Integer> {

}
