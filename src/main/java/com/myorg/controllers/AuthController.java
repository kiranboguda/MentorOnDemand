package com.myorg.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.exceptions.ResourceNotFoundException;
import com.myorg.message.request.LoginForm;
import com.myorg.message.request.SignUpForm;
import com.myorg.message.response.JwtResponse;
import com.myorg.models.Role;
import com.myorg.models.RoleName;
import com.myorg.models.User;
import com.myorg.repositories.RoleRepository;
import com.myorg.repositories.UserRepository;
import com.myorg.security.JwtProvider;

/**
 * This class is used to implement the endpoints for the Authorisation service
 * @author KiranBoguda
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
 
    @Autowired
    AuthenticationManager authenticationManager;
 
    @Autowired
    UserRepository userRepository;
 
    @Autowired
    RoleRepository roleRepository;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtProvider jwtProvider;
 
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) 
    {
 
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                     
                )
        );
        if(!checkUserBlocked(loginRequest.getUsername()))
        {
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        	 
            String jwt = jwtProvider.generateJwtToken(authentication);
            JwtResponse jwtRes = new JwtResponse(jwt);
            jwtRes.setRole(getUserRole(loginRequest.getUsername()).toString());
            jwtRes.setUserId(getUserId(loginRequest.getUsername()).toString());
            return ResponseEntity.ok(jwtRes);
        }
        else
        {
        	return new ResponseEntity<String>("Account has been locked, please contact support",HttpStatus.LOCKED); 
        }
    }
    
   /**
    * 
    * @param userId
    * @return true if the user is blocked, false if the user is not blocked
    */
    public boolean checkUserBlocked(String username)
	{
    	User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
		String userBlocked = user.getUserBlocked();
		if (userBlocked.compareTo("N") == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
    
    public Long getUserRole(String username)
    {
    	User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    	Set<Role> roles = user.getRoles();
    	Role currentRole = roles.iterator().next(); 
    	return currentRole.getId();
    }
    
    public String getUserId(String username)
    {
    	User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    	return user.getId().toString();
    }
 
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) 
    {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) 
        {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
 
        if(userRepository.existsByEmail(signUpRequest.getEmail())) 
        {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
 
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getContactNum());
 
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
 
        strRoles.forEach(role -> 
        {
        	switch(role) 
        	{
	    		case "admin":
	    			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
	    			roles.add(adminRole);
	    			break;
	    			
	    		case "mentor":
	    			Role mentorRole = roleRepository.findByName(RoleName.ROLE_MENTOR)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
	    			roles.add(mentorRole);
	    			break;
	    			
	    		default:
	        		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
	        		roles.add(userRole);        			
        	}
        });
        
        user.setRoles(roles);
        userRepository.save(user);
 
        return ResponseEntity.ok().body("User registered successfully!");
    }
}