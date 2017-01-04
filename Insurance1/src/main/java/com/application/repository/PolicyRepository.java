package com.application.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.application.model.Policy;

/**
 * Interface created to support CRUD operations on Policy collection
 * @author NS
 *
 */

@Component
public interface PolicyRepository  extends MongoRepository<Policy, String> {

}


