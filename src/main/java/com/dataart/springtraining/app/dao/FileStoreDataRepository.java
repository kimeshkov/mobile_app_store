package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.FileStoreData;
import org.springframework.data.repository.CrudRepository;

public interface FileStoreDataRepository extends CrudRepository<FileStoreData, Integer> {
}
