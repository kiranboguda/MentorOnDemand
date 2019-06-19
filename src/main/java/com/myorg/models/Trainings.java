package com.myorg.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "Trainings")
public class Trainings
{
	@Column (name = "Status")
	private char status;
	
	@Column (name = "Cost")
	private int cost;
	
	@Column (name = "StartDate", nullable=false)
	private Date startDate;
	
	@Column (name = "EndDate", nullable=false)
	private Date endDate;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "TrainingsId")
	private int trainingsId;
	
	@Column (name = "MentorId")
	private int mentorId;
	
	@Column (name = "PaymentFromUser")
	private int paymentFromUser;
	
	@Column (name = "PaymentToMentor")
	private int paymentToMentor;
	
	@Column (name = "Progress")
	private int progress;
	
	@Column (name = "Rating")
	private int rating;
	
	@Column (name = "TechId")
	private int techId;
	
	@Column (name = "UserId")
	private int userId;
	
	@Column (name = "CommissionPerc")
	private int commissionProc;
	
	public Integer getTrainingsId()
	{
		return this.trainingsId;
	}
	
	public Trainings setTrainingsId(Integer inputId)
	{
		this.trainingsId = inputId;
		return this;
	}
	
	public int getUserId()
	{
		return this.userId;
	}
	
	public Trainings setUserId(int inputUserId)
	{
		this.userId = inputUserId;
		return this;
	}
	
	public int getCost()
	{
		return this.cost;
	}
	
	public void setCost(int inputCost) 
	{
		this.cost = inputCost;
	}
	
	public int getTechId()
	{
		return this.techId;
	}
	
	public Trainings setTechId(int inputTechId)
	{
		this.techId = inputTechId;
		return this;
	}
	
	public int getMentorId()
	{
		return this.mentorId;
	}
	
	public Trainings setMentorId(int inputMentorId)
	{
		this.mentorId = inputMentorId;
		return this;
	}
	
	public char getStatus()
	{
		return this.status;
	}
	
	public Trainings setStatus(char inputStatus)
	{
		this.status = inputStatus;
		return this;
	}
	
	public int getProgress()
	{
		return this.progress;
	}
	
	public Trainings setProgress(int inputProgress)
	{
		this.progress = inputProgress;
		return this;
	}
	
	//TODO some thought around implementation is required
	public int getRating()
	{
		return this.rating;
	}
	
	public Trainings setRating(int inputRating)
	{
		this.rating = inputRating;
		return this;
	}
	
	
	public Date getStartDate()
	{
		return this.startDate;
	}
	
	public Trainings setStartDate(Date inputStartDate)
	{
		this.startDate = inputStartDate;
		return this;
	}
	
	public Date getEndDate()
	{
		return this.endDate;
	}
	
	public Trainings setEndDate(Date inputEndDate)
	{
		this.endDate = inputEndDate;
		return this;
	}
	
	public int getPaymentFromUser()
	{
		return this.paymentFromUser;
	}
	
	public Trainings setPaymentFromUser(int inputPaymentFromUser)
	{
		this.paymentFromUser = inputPaymentFromUser;
		return this;
	}
	
	public int getPaymentToMentor()
	{
		return this.paymentToMentor;
	}
	
	public Trainings setPaymentToMentor(int inputPaymentToMentor)
	{
		this.paymentToMentor = inputPaymentToMentor;
		return this;
	}
	
	public void setCommissionPerc(int inputCommissionPerc)
	{
		this.commissionProc = inputCommissionPerc;
	}
	
	public int getCommissionPerc() {
		return this.commissionProc;
	}
}
	