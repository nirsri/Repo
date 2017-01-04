package com.application.validator;

import com.application.model.AccountHolder;
/**
 * Class created to implement validation layer using decorator pattern
 * @author NS
 *
 */
public class ValidHolder extends AccountHolder{
	private AccountHolder holder;
	public ValidHolder(AccountHolder holder)
	{
		this.holder = holder;
	}

	public boolean validateHolder() throws Exception
	{		
		if(holder.getEmail()!=null && !isEmailValid())
			throw new Exception("invalid email!");
		if(!namesValid())
			throw new Exception("First and last names cannot contain numbers/special characters!");
		return true;
	}
	
	public boolean namesValid()
	{
		if((holder.getFirstName() == null || holder.getFirstName().matches("[A-Za-z]+")) && 
				 (holder.getLastName() == null || holder.getLastName().matches("[A-Za-z]+")))
			return true;
		else return false;
	}
	
	public boolean isEmailValid()
	{
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(holder.getEmail());
        return m.matches();
	}
}
