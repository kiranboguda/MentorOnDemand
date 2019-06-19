package com.myorg.message.response;

/**
 * Form used to return the response after the login request
 * @author KiranBoguda
 *
 */
public class JwtResponse 
{
    private String token;
    private String type = "Bearer";
    private String role;
    private String userId;
 
    public JwtResponse(String accessToken) 
    {
        this.token = accessToken;
    }
 
    public String getAccessToken() 
    {
        return token;
    }
 
    public void setAccessToken(String accessToken) 
    {
        this.token = accessToken;
    }
 
    public String getTokenType() 
    {
        return type;
    }
 
    public void setTokenType(String tokenType) 
    {
        this.type = tokenType;
    }
    
    public String getRole()
    {
    	return this.role;
    }
    
    public void setRole(String inputRole)
    {
    	this.role = inputRole;
    }
    
    public String getUserId()
    {
    	return this.userId;
    }
    
    public void setUserId(String inputUserId)
    {
    	this.userId = inputUserId;
    }
    
}