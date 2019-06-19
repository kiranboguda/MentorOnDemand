package com.myorg.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
    	if(!response.containsHeader("Access-Control-Allow-Origin"))
    	{
    		response.addHeader("Access-Control-Allow-Origin", "*");	
    	}        
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Authorization, Content-Type");
        response.addIntHeader("Access-Control-Max-Age", 3600);
        
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) 
        {
            response.setStatus(HttpServletResponse.SC_OK);
        } 
        else 
        {
        	filterChain.doFilter(request, response);
        }
    }
}