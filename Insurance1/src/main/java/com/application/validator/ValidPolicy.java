package com.application.validator;

import com.application.model.Policy;
/**
 * Class created to implement validation layer using decorator pattern
 * @author NS
 *
 */
public class ValidPolicy extends Policy {
	private Policy policy;
	public ValidPolicy(Policy policy)
	{
		this.policy = policy;
	}
	
	public boolean validatePolicy() throws Exception
	{
		new ValidHolder(policy.getAccountHolder()).validateHolder();
		if(policy.getPolicyNumber() == null || policy.getPolicyNumber().trim().equals(""))
			throw new Exception("Please fill Policy Number!");
		if(policy.getPolicyNumber().matches("[A-Za-z0-9]+"))
		{
			return true;
		}
		else 
		{
			throw new Exception("Please ensure policy number is alphanumeric!");
		}
	}
	
}

