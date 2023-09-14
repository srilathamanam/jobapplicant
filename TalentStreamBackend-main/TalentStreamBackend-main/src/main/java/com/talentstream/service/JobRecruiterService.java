package com.talentstream.service;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.talentstream.entity.JobRecruiter;
import com.talentstream.repository.JobRecruiterRepository;

@Service

public class JobRecruiterService implements UserDetailsService{
	 
	    private PasswordEncoder passwordEncoder;
	    
	   @Autowired
        JobRecruiterRepository recruiterRepository;

   
	
	    public JobRecruiterService(JobRecruiterRepository recruiterRepository, PasswordEncoder passwordEncoder) {
	        this.recruiterRepository = recruiterRepository;
	        this.passwordEncoder = passwordEncoder;
	    }

    public ResponseEntity<String> saveRecruiter(JobRecruiter recruiter) {
        // Check if the email already exists in the database
        if (recruiterRepository.existsByEmail(recruiter.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // Save the recruiter if the email is not already registered
//        String encodedPassword = passwordEncoder.encode(recruiter.getPassword());
//        recruiter.setPassword(encodedPassword);
        recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
        recruiterRepository.save(recruiter);
        return ResponseEntity.ok("Recruiter registered successfully");
    }
    public boolean login(String email, String password) {
        JobRecruiter recruiter = recruiterRepository.findByEmail(email);

        if (recruiter != null && passwordEncoder.matches(password, recruiter.getPassword())) {
            return true; // Login successful
        }  else {
            return false; // Login failed
        }
    }
    public JobRecruiter findById(Long id) {
       return recruiterRepository.findByrecruiterId(id);
        
    }
    public List<JobRecruiter> getAllJobRecruiters() {
        return recruiterRepository.findAll();
    }
    public void updatePassword(String userEmail, String newPassword) {
        JobRecruiter jobRecruiter = recruiterRepository.findByEmail(userEmail);
        if (jobRecruiter != null) {
            jobRecruiter.setPassword(newPassword);
            recruiterRepository.save(jobRecruiter);
        } else {
            throw new EntityNotFoundException("JobRecruiter not found for email: " + userEmail);
        }
    }

	public JobRecruiter findByEmail(String userEmail) {
		// TODO Auto-generated method stub
		return recruiterRepository.findByEmail(userEmail);
	}
	
	 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 JobRecruiter jobRecruiter =recruiterRepository.findByEmail(username);
		 //new User(username, username, null)
		 return  new User(jobRecruiter.getEmail(), jobRecruiter.getPassword(),Arrays.stream(jobRecruiter.getRoles().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()));
	}
	

}
    

    // You can add more methods here for updating, deleting, finding recruiters, etc.

	
