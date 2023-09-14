package com.talentstream.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.talentstream.service.JwtUtil;

import jakarta.inject.Qualifier;

import com.talentstream.service.JobRecruiterService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
    private JwtUtil jwtUtil;

	private final UserDetailsService applicantregisterService;
    private final UserDetailsService jobrecuriterService;
    private PasswordEncoder passwordEncoder;
    
    public JwtRequestFilter()
    {
		this.applicantregisterService = null;
		this.jobrecuriterService = null;
    	
    }
    
    public JwtRequestFilter(UserDetailsService applicantregisterService, UserDetailsService jobrecuriterService) {
        this.applicantregisterService = applicantregisterService;
        this.jobrecuriterService = jobrecuriterService;
    }
   
   

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null ) {
            jwt = authorizationHeader;
            username = jwtUtil.extractUsername(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	 
        	UserDetails userdetails = null;
        	if (isApplicantRequest(request)) {
                userdetails = applicantregisterService.loadUserByUsername(username);
            }
        	else if (isRecruiterRequest(request))
        	{
                userdetails = jobrecuriterService.loadUserByUsername(username);
            }
            

            /*if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }*/
        	if (userdetails != null && jwtUtil.validateToken(jwt, userdetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userdetails, null, userdetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    
    private boolean isApplicantRequest(HttpServletRequest request) {
    	 String requestURI = request.getRequestURI();
    	    return requestURI.contains("/applicant/");
     
    }

    private boolean isRecruiterRequest(HttpServletRequest request) {
       
    	String requestURI = request.getRequestURI();
        return requestURI.contains("/recruiter/");
    }
 

}
