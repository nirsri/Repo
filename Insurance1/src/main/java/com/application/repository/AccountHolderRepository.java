package com.application.repository;

import java.util.List;
import com.application.Insurance1Application;
import com.application.model.AccountHolder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
/**
 * Interface created to support CRUD operations on AccountHolder collection
 * @author NS
 *
 */
@Component
public interface AccountHolderRepository extends MongoRepository<AccountHolder, String> {


}

	

