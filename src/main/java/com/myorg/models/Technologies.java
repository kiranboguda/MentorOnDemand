package com.myorg.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity (name = "Technologies")
public class Technologies
{
	private Integer techId;
	private String techName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "TechId")
	public Integer getTechId()
	{
		return this.techId;
	}
	
	public void setTechId(Integer inputTechId)
	{
		this.techId = inputTechId;
	}
	
	@Column(name = "TechnologyName")
	public String getTechName()
	{
		return this.techName;
	}
	
	public void setTechName(String inputTechName)
	{
		this.techName = inputTechName;
	}
}