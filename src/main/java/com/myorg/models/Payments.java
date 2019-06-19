package com.myorg.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "Payments")
public class Payments
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "paymentId")
	private int paymentId;
	
	@Column (name = "Amount")
	private int amount;

	@Column (name = "UserId")
	private int userId;
	
	@Column (name = "TranactionId")
	private String transactionId;
	
	@Column (name = "TrainingsId")
	private int trainingsId;

	public int getPaymentId()
	{
		return this.paymentId;
	}
	
	public void setId(int inputId)
	{
		this.paymentId = inputId;
	}
	
	public int getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(int inputUserId)
	{
		this.userId = inputUserId;
	}
	
	public String getTransactionId()
	{
		return this.transactionId;
	}
	
	public void setTransactionId(String inputTransactionId)
	{
		this.transactionId = inputTransactionId;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public void setAmount(int inputAmount)
	{
		this.amount = inputAmount;
	}
}