package com.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 
 * @author NS
 * Represents Policy collection on MongoDB 
 */

@Document(collection = "Policy")
public class Policy {
	
	@Id private String policyNumber;
	private double premiumAmount;
	
	@DBRef
	private AccountHolder accountHolder;

	/**
	 * default empty constructor for easy creation
	 */
	public Policy()
	{
		
	}
	public Policy(String policyNumber, double premiumAmount, AccountHolder accountHolder)
	{
		this.policyNumber = policyNumber;
		this.premiumAmount = premiumAmount;
		this.accountHolder = accountHolder;
	}
	
	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}
	

}
