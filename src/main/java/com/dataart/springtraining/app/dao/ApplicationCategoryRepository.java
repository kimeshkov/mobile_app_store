package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mkim on 29/10/2015.
 */
public interface ApplicationCategoryRepository extends CrudRepository<Category, Integer> {

}
