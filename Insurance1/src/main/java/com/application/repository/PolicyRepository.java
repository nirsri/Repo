package com.application.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import com.application.model.AccountHolder;
import com.application.model.Policy;

/**
 * Interface created to support CRUD operations on Policy collection
 * @author NS
 *
 */

@Component
public interface PolicyRepository  extends MongoRepository<Policy, String> {

}


