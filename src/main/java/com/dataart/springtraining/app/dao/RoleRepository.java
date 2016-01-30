package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kimeshkov on 31.01.2016.
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query("select role from Role role where role_value = :roleValue")
    Role findByValue(String roleValue);

}
