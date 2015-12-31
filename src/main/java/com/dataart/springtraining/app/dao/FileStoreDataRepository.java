package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.FileStoreData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mkim on 30/11/2015.
 */
public interface FileStoreDataRepository extends CrudRepository<FileStoreData, Integer> {
}
