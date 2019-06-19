package com.myorg.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "Mentor")
public class Mentor
{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mentorId")
    private Integer mentorId;
	
	@Column(name = "userId")
	private Integer userId;

	@Column(name = "createdAt")
    private Date createdAt;
	
	@Column (name = "LinkedIn")
	private String linkedIn;
	
	@Column (name = "YearsOfExp")
	private int yearsOfExp;
	
	@Column (name = "Facilities")
	private String facilities;
	
	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
	private User users;
	
	public Mentor()
	{}
	
	public Mentor(String inputUsername, String inputLinkedIn, int inputYearsOfExp, String inputFacilities)
	{
		this.linkedIn = inputLinkedIn;
		this.yearsOfExp = inputYearsOfExp;
		this.facilities = inputFacilities;	
	}
	
	public Integer getMentorId()
	{
		return this.mentorId;
	}
	
	public Mentor setMentorId(Integer inputMentorId)
	{
		this.mentorId = inputMentorId;
		return this;
	}
	
	public Integer getUserId()
	{
		return this.userId;
	}
	
	public Mentor setUserId(Integer inputUserId)
	{
		this.userId = inputUserId;
		return this;
	}
	
	@PrePersist
    public void setCreatedAt() 
    {
      this.createdAt = new Date();
    } 
    
    public Date getCreatedAt()
    {
    	return this.createdAt;
    }
	
	public String getLinkedIn()
	{
		return this.linkedIn;
	}
	
	public Mentor setLinkedIn(String inputLinkedIn)
	{
		this.linkedIn = inputLinkedIn;
		return this;
	}
	
	public int getYearsOfExp()
	{
		return this.yearsOfExp;
	}
	
	public Mentor setYearsOfExp(int inputYearsOfExp)
	{
		this.yearsOfExp = inputYearsOfExp;
		return this;
	}	
	
	public String getFacilities()
	{ 
		return this.facilities;
	}
	
	public Mentor setFacilities(String inputFacilities)
	{
		this.facilities = inputFacilities;
		return this;
	}
}