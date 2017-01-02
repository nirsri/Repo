package com.application.model;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author NS
 * Represents AccountHolder collection in DB
 */
@Document(collection = "AccountHolder")
public class AccountHolder {
	
	@Id private String id;
	
	private String firstName;
	private String lastName;
	private Date dob;
	
	private String email;

	public AccountHolder()
	{
		
	}
	public AccountHolder(String id, String firstName, String lastName, Date dob, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.email = email;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
}
