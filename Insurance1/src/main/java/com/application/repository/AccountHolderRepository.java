package com.application.repository;

import com.application.model.AccountHolder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
/**
 * Interface created to support CRUD operations on AccountHolder collection
 * @author NS
 *
 */
@Component
public interface AccountHolderRepository extends MongoRepository<AccountHolder, String> {


}

	

