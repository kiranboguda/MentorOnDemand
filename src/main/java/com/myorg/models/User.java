package com.myorg.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

/**
 * Table used to store the Users details once signup has been completed
 * @author KiranBoguda
 *
 */
@Entity
@Table(name = "users")
public class User
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @NotBlank
    @Size(min=3, max = 50)
    private String name;
 
    @NotBlank
    @Size(min=3, max = 50)
    private String username;
 
    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
 
    @NotBlank
    @Size(min=6, max = 100)
    private String password;
    
    @NotBlank
    @Size(min=6, max = 20)
    private String contactNum;
    
    @Column(name = "createdAt")
    private Date createdAt;
    
    @Column
    @Size(max = 1)
    private String userBlocked;
    
    @Column
    @Size(max = 1)
    private String emailConfirmed;
 
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
 
    public User() {}
 
    public User(String name, String username, String email, String password, String contactNum) 
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contactNum = contactNum;
        this.userBlocked = "N";
        this.emailConfirmed = "N";
    }
 
    public Integer getId() 
    {
        return id;
    }
 
    public User setId(Integer inputId) 
    {
        this.id = inputId;
        return this;
    }
 
    public String getUsername() 
    {
        return username;
    }
 
    public User setUsername(String username) 
    {
        this.username = username;
        return this;
    }
 
    public String getName() 
    {
        return name;
    }
 
    public User setName(String name) 
    {
        this.name = name;
        return this;
    }
 
    public String getEmail() 
    {
        return email;
    }
 
    public User setEmail(String email) 
    {
        this.email = email;
        return this;
    }
 
    public String getPassword() 
    {
        return password;
    }
 
    public User setPassword(String password) 
    {
        this.password = password;
        return this;
    }
 
    public Set<Role> getRoles() 
    {
        return roles;
    }
 
    public User setRoles(Set<Role> roles2) 
    {
        this.roles = roles2;
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
    
    public User setContactNum(String contactNum) 
    {
      this.contactNum = contactNum;
      return this;
      
    } 
    
    public String getContactNum()
    {
    	return this.contactNum;
    }
    
    public User setUserBlocked(String userBlocked)
    {
    	this.userBlocked = userBlocked;
    	return this;
    }
    
    public String getUserBlocked()
    {
    	return this.userBlocked;
    }
    
    public User setEmailConfirmed(String emailConfirmed)
    {
    	this.emailConfirmed = emailConfirmed;
    	return this;
    }
    
    public String getEmailConfirmed()
    {
    	return this.emailConfirmed;
    }
}