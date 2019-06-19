package com.myorg.email;

/**
 * Form used for sending email addresses
 * @author KiranBoguda
 *
 */
public class EmailRequest
{
	private String emailAddress;
	private String subject;
	private String bodyText;
	
	public String getEmailAddress()
	{
		return this.emailAddress;
	}
	
	public void setEmailAddress(String inputEmailAddress)
	{
		this.emailAddress = inputEmailAddress;
	}
	
	public String getSubject() 
	{
		return this.subject;
	}
	
	public void setSubject(String inputSubject)
	{
		this.subject = inputSubject;
	}
	
	public String getBodyText()
	{
		return this.bodyText;
	}
	
	public void setBodyText(String inputBodyText)
	{
		this.bodyText = inputBodyText;
	}
}