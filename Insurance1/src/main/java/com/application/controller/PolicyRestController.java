package com.application.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.application.model.AccountHolder;
import com.application.model.Policy;
import com.application.repository.AccountHolderRepository;
import com.application.repository.PolicyRepository;
import com.application.validator.ValidPolicy;

/**
 * 
 * @author NS
 * Rest controller class to support CRUD operations
 */

@RestController
@RequestMapping("/insurance")
public class PolicyRestController {

	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private AccountHolderRepository holderRepository;
	
	//for Log4J
	private static Logger log = Logger.getLogger(PolicyRestController.class);
	
	
	/**
	 * HTTP GET to find all policies
	 */
	@RequestMapping(method = RequestMethod.GET , value="/findAllPolicies")
	public List<Policy> findAllPolicies()
	{
		return policyRepository.findAll();
	}
	
	
	
	/**
	 * HTTP GET to find 1 policy matching number
	 * @param policyId - to be passed in the URL e.g. ..../insurance/HLT1111
	 * @return single policy matching policy number passed in URL
	 */
	@RequestMapping(method = RequestMethod.GET, value="/{policyId}")
	public Policy getPolicyDetails(@PathVariable("policyId") String policyId)
	{
		return policyRepository.findOne(policyId);
	}
	
	
	
	/**
	 * To create a policy in DB. Checks if holder id passed in JSON actually exists. 
	 * If it does not exist, holder is also created
	 * If it exists already, then the holder id is set in the policy collection (holder will not be updated)
	 * Does not error out if existing policy is inserted - does no action & returns success
	 * @param policy to be passed as JSON
	 * @return a Map object with 2 entries - 1st is <message, message-text> , 2nd is <policy, policy object from DB>
	 */
	@RequestMapping(method=RequestMethod.POST)
	public  @ResponseBody  Map<String, Object> addPolicy(@RequestBody Policy policy)  
	{
		log.info("Entered addPolicy method");
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		////////////////////Validate policy and holder details///////////////////////////
		ValidPolicy vPolicy = new ValidPolicy(policy);
		try {
			vPolicy.validatePolicy();
		} catch (Exception e) {
			response.put("message", e.getMessage());
			return response;
		}
		///////////////////////////////////////////////
		
		
		AccountHolder holder = holderRepository.findOne(policy.getAccountHolder().getId());
		if(holder==null)
		{
			log.info("account holder is null");
			holderRepository.save(policy.getAccountHolder());
			policyRepository.save(policy);
		}
		else
		{
			log.info("holder not null");
			policyRepository.save(policy);
		}
		Policy dbPolicy = policyRepository.findOne(policy.getPolicyNumber());
		response.put("message", "SUCCESS");
		response.put("policy", dbPolicy);
		return response; 
	  }
	
	
	
	/**
	 * Deletes policy and returns success after deletion
	 * Does not delete related account holder as it may be associated to other policies
	 * @param policyId to be passed in URL as path variable
	 * @return hash map containing message
	 */
	 @RequestMapping(method=RequestMethod.DELETE, value="/{policyId}")
	 public Map<String, String> delete(@PathVariable String policyId) 
	 {
		Map<String, String> response = new HashMap<String, String>();
		policyRepository.delete(policyId);		
		response.put("message", "SUCCESS");
		return response;
	 }
	  
	/**
	 * To update -
	 * --policy details
	 * --holder details
	 * If holder does not exist, it is created
	 * If holder exists, it is updated
	 * 
	 * @param policy containing updates
	 * @return
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public  Map<String, Object>  update(@RequestBody Policy policy)  
	{
		log.info("********INSIDE UPDATE*********");
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		////////////////////Validate policy and holder details///////////////////////////
		ValidPolicy vPolicy = new ValidPolicy(policy);
		try {
			vPolicy.validatePolicy();
		} catch (Exception e) {
			response.put("message", e.getMessage());
			return response;
		}
		///////////////////////////////////////////////
		
		Policy dbPolicy = policyRepository.findOne(policy.getPolicyNumber());
		
		if(dbPolicy!=null)
		{
		    dbPolicy.setPolicyNumber(policy.getPolicyNumber());
		    dbPolicy.setPremiumAmount(policy.getPremiumAmount());
		    if(policy.getAccountHolder()!=null)
		    {
			    AccountHolder dbHolder = holderRepository.findOne(policy.getAccountHolder().getId());
				  if(dbHolder==null)
				  {
					  log.info("New holder creation");
					  holderRepository.save(policy.getAccountHolder());
				  }
				  else
				  {
					  AccountHolder inputHolder= policy.getAccountHolder();
					  dbHolder.setDob(inputHolder.getDob());
					  dbHolder.setFirstName(inputHolder.getFirstName());
					  dbHolder.setLastName(inputHolder.getLastName());
					  dbHolder.setEmail(inputHolder.getEmail());
					  holderRepository.save(dbHolder);
				  }
		    }
		    dbPolicy.setAccountHolder(policy.getAccountHolder());
		    policyRepository.save(dbPolicy);
		    log.info("update part");
	  	    response.put("message", "SUCCESS");
	  	    response.put("policyInfo", policy); 
		}
		else
		{
			log.info("else part");
			response.put("message", "FAILURE - Does not exist");
	  	    response.put("policyInfo", policy);
		}
		return response;
	}
	
}
