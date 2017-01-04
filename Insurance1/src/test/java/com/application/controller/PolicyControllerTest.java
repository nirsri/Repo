package com.application.controller;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import com.application.model.AccountHolder;
import com.application.model.Policy;
import com.application.repository.AccountHolderRepository;
import com.application.repository.PolicyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * New test class created to test the PolicyRestController methods. Generates JSON and 
 * invokes the rest controller methods by invoking via REST WS
 * @author NS
 *
 */
@Component
@ContextConfiguration
public class PolicyControllerTest {
	
	//to construct test JSON msgs
	private RestTemplate restTemplate = new RestTemplate();
	  
	//for converting Object to JSON
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	  
	private static Logger log = Logger.getLogger(PolicyControllerTest.class);
	  
	private static final String path ="http://localhost:8091/insurance/";
	

	/**
	 * Tests policy creation for PolicyRestController.
	 * Hardcoded values used & are to be changed while re-executing test
	 * @throws JsonProcessingException
	 */
	@Test
	public void testAddPolicy() throws JsonProcessingException 
	{
		////////////////////////////////////////// 
		String newPolicyNumber =  "HLT123123";
		double premiumAmount = 100.00;
		String holderId = "123";
		String holderEmail = "abc@abc.com";
		String holderFN = "Sree";
		String holderLN = "Kripa";
		String dob = "12/12/2012";
		////////////////////////////////////////// 
		 
		log.info("Create Policy Info test case");
		 
		//Creating holder and Policy objects for Create operation
		AccountHolder holder = new AccountHolder();
		holder.setId(holderId);
		try {
			holder.setDob(new SimpleDateFormat("dd/MM/yy").parse(dob));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.setFirstName(holderFN);
		holder.setLastName(holderLN);
		holder.setEmail(holderEmail);
		Policy newPolicy = new Policy();		  
		newPolicy.setPolicyNumber(newPolicyNumber);
		newPolicy.setPremiumAmount(premiumAmount);
		newPolicy.setAccountHolder(holder);
		
		//Header for input JSON
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		//HTTP JSON Request with header and body containing policy
		HttpEntity<String> httpEntity =
		    new HttpEntity<String> (OBJECT_MAPPER.writeValueAsString(newPolicy), requestHeaders);

		log.info("*Req message::"+OBJECT_MAPPER.writeValueAsString(newPolicy));
		
		//WRITE NEW POLICY to DB (by invoking REST WS of controller class)
		Map<String,Object> apiResponse =
		     restTemplate.postForObject(path, httpEntity, Map.class, Collections.EMPTY_MAP);
		log.info(apiResponse.get("message"));
		String message = apiResponse.get("message").toString();
		log.info("Response message::"+message);
		
		
		//check if message has value success
		assertEquals("SUCCESS", message);
		
		String policyNumber = ((Map<String, Object>)apiResponse.get("policy")).get("policyNumber").toString();
		  
		//get the new policy from DB and compare values with ones passed
		Policy policyValFromDb=this.find(policyNumber);
		
		assertTrue(premiumAmount==policyValFromDb.getPremiumAmount());
		log.info("premium amount fetched is equal to passed");
		  
		assertEquals(holderId, policyValFromDb.getAccountHolder().getId());
		log.info("The entry passed and object saved in db are equal"); 
		  
	}
	

	
	/**
	 * Tests policy deletion for PolicyRestController.
	 * Hardcoded values used & are to be changed while re-executing test
	 * @throws JsonProcessingException
	 */	
	@Test
	public void testDelete() {
		log.info("Delete Policy record test case");
		////////////////////////////////
		String policyId = "231";
		///////////////////////////////

		//Invoking DELETE operation (via rest ws)
		restTemplate.delete(path+policyId, Collections.EMPTY_MAP);

		//After delete operation check that record does not exist
		Policy policyValFromDb = this.find(policyId);
		assertNull(policyValFromDb);
		  
		log.info("Deletion test completed");
	}

	
	private Policy find(String policyNumber){
		Policy policyValFromDb =
			       restTemplate.getForObject(path+policyNumber, Policy.class, Collections.EMPTY_MAP); 
		return policyValFromDb;
	}
		
	/**
	 * Tests policy update for PolicyRestController.
	 * Hardcoded values used & are to be changed while re-executing test
	 * @throws JsonProcessingException
	 */
	@Test
	public void testUpdate() throws JsonProcessingException {
		//////////////////////////////////////////
		String policyNumber =  "HLT123123";
		double premiumAmount = 100.00;
		String holderId = "123";
		String holderEmail = "bcd@abc.com";
		String holderFN = "SreeK";
		String holderLN = "KripaK";
		String dob = "12/12/2012";
		//////////////////////////////////////////  
		
		log.info("Update Policy Info test case");
		  
		//Creating holder and Policy to be updated
		AccountHolder holder = new AccountHolder();
		holder.setId(holderId);
		holder.setFirstName(holderFN);
		holder.setLastName(holderLN);
		holder.setEmail(holderEmail);
		try {
			holder.setDob(new SimpleDateFormat("dd/MM/yy").parse(dob));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		Policy policyWithUpdates = new Policy();
		  
		policyWithUpdates.setPolicyNumber(policyNumber);
		policyWithUpdates.setPremiumAmount(premiumAmount);
		policyWithUpdates.setAccountHolder(holder);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		//HTTP JSON Request with header and body containing policy
		HttpEntity<String> httpEntity =
		      new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(policyWithUpdates), requestHeaders);

		log.debug("********pol with updates" +OBJECT_MAPPER.writeValueAsString(policyWithUpdates));
		//Invoking update operation via REST WS
		Map<String, Object> apiResponse = (Map<String, Object>) restTemplate.exchange(path,
		    HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();
		assertNotNull(apiResponse);
		assertTrue(!apiResponse.isEmpty());

		//Asserting that the message is SUCCESS
		String message = apiResponse.get("message").toString();
		log.info(message);
		assertEquals("SUCCESS", message);

		//checking DB and comparing if values are updated properly
		Policy policyValFromDb=this.find(policyNumber);
		assertTrue(premiumAmount==policyValFromDb.getPremiumAmount());
		log.info("policy amount updated is equal to passed"); 
		assertEquals(holderId, policyValFromDb.getAccountHolder().getId());
		  
		log.info("The entry passed and object updated in db are equal");
		
	}

	@Test
	public void testGetPolicyDetails()
	{
		log.info("Find Policy test case");
		////////////////////////////////
		String policyNumberExisting = "HLT123123";
		String policyNumberNonExisting ="XXXXXXXX";
		///////////////////////////////

		//Invoking READ operation (via rest ws)
		Policy policyValFromDb = this.find(policyNumberExisting);
		assertNotNull(policyValFromDb);
		
		policyValFromDb = this.find(policyNumberNonExisting);
		assertNull(policyValFromDb);
		
		
		log.info("Find test completed");
	}
}
