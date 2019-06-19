package com.myorg.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Form used to log into the Service
 * 
 * @author KiranBoguda
 */
public class LoginForm 
{
    @NotBlank
    @Size(min=3, max = 60)
    private String username;
 
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
 
    public String getUsername() 
    {
        return this.username;
    }
 
    public void setUsername(String inputUsername) 
    {
        this.username = inputUsername;
    }
 
    public String getPassword() 
    {
        return this.password;
    }
 
    public void setPassword(String inputPassword) 
    {
        this.password = inputPassword;
    }
}