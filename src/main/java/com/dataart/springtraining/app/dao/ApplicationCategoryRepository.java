package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationCategoryRepository extends CrudRepository<Category, Integer> {

}
