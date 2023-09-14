package com.talentstream.controller;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.talentstream.entity.AuthenticationResponse;
import com.talentstream.entity.Login;
import com.talentstream.entity.Register;
import com.talentstream.entity.RegisterwithOTP;
import com.talentstream.response.ResponseHandler;
import com.talentstream.service.JwtUtil;
import com.talentstream.service.RegisterService;
@RestController
public class RegisterController {
	@Autowired
    private RegisterService registerService;
	
	@Autowired
 	private AuthenticationManager authManager;
	 @Autowired
	 	private JwtUtil jwtTokenUtil;
	 
    public RegisterController(RegisterService registerService)
    {
		this.registerService = registerService;
		
	}

	@PostMapping("/api/applicant-register")
    public ResponseEntity<RegisterwithOTP> register(@RequestBody Register register) throws MessagingException {
        return new ResponseEntity<>(registerService.register(register), HttpStatus.OK);
    }

    @PutMapping("/api/applicant-verifyaccount")
    public ResponseEntity<String> verifyAccount(@RequestBody String email,
                                                @RequestBody String otp) {
        return new ResponseEntity<>(registerService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @PutMapping("/api/applicant-regenerateotp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(registerService.regenerateOtp(email), HttpStatus.OK);
    }

   
    @PutMapping("/api/applicant/passwordforgotoption")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap) {
        String email = requestMap.get("email");
        return new ResponseEntity<>(registerService.forgotPassword(email), HttpStatus.OK);
    }



   /* @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        // Implement the logic to handle password reset here
        return new ResponseEntity<>("Password reset link clicked for: " + email, HttpStatus.OK);
    }*/
      @PutMapping("/api/applicant/set-newpassword")
      public ResponseEntity<String> setPassword(@RequestBody Map<String, String> requestMap) {
          String email = requestMap.get("email");
          String password = requestMap.get("password");
          return new ResponseEntity<>(registerService.setPassword(email, password), HttpStatus.OK);
      }
      
      @PostMapping("/api/applicant-authenticate")
      public ResponseEntity<Object> applicanLogin(@RequestBody Login loginRequest) throws Exception 
      {
    	  boolean result = registerService.login(loginRequest);

          System.out.println(result);
          System.out.println(loginRequest.getEmail());
          
          if (result) {
        	  System.out.println("hello");
          	return createToken(loginRequest);
          } else {
              return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
          }
      }
      
      
     // private ResponseEntity<Object> createAuthenticationToken(@RequestBody Login loginRequest) throws Exception {
  		// TODO Auto-generated method stub
    	  private ResponseEntity<Object> createToken(Login loginRequest) throws Exception {
    	  try {
    		  System.out.println("i entered ");
    		  System.out.println(loginRequest.getEmail());
    		  System.out.println(loginRequest.getPassword());
  			authManager.authenticate(
  					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
  			);
  			System.out.println("created object ");
  		}
  		catch (BadCredentialsException e) {
  			throw new Exception("Incorrect username or password", e);
  		}
      	final UserDetails userDetails = registerService.loadUserByUsername(loginRequest.getEmail());
      	System.out.println(userDetails.getUsername());
  		final String jwt = jwtTokenUtil.generateToken(userDetails);
  		System.out.println(jwt);

    
  		return ResponseHandler.generateResponse("Login successfully"+userDetails.getAuthorities(), HttpStatus.OK, new AuthenticationResponse(jwt));
  	}
      
}
