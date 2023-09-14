package com.talentstream.service;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talentstream.entity.Login;
import com.talentstream.entity.Register;
import com.talentstream.entity.RegisterwithOTP;
import com.talentstream.repository.RegisterRepository;
import com.talentstream.util.EmailUtil;
import com.talentstream.util.OtpUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class RegisterService implements UserDetailsService {
	 @Autowired
	    private OtpUtil otpUtil;
	    @Autowired
	    private EmailUtil emailUtil;
	    @Autowired
	    private RegisterRepository registerRepository;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	      
	        
	    public RegisterService( RegisterRepository registerRepository)
	    {
		
			this.registerRepository = registerRepository;
		
		}
	   
		public RegisterwithOTP register(Register register) throws javax.mail.MessagingException {

	        Optional<RegisterwithOTP> existingApplicant = registerRepository.findByEmail(register.getEmail());

	        if (existingApplicant.isPresent()) {
	            throw new IllegalArgumentException("Email already registered");
	        }
	        else {

	            String otp = otpUtil.generateOtp();
	            try {
	                emailUtil.sendOtpEmail(register.getEmail(), otp);
	            } catch (Exception e) {
	                throw new RuntimeException("Unable to send otp please try again");
	            }
	            RegisterwithOTP user = new RegisterwithOTP();
	            user.setName(register.getName());
	            user.setEmail(register.getEmail());
	            System.out.println(register.getPassword());
	          // System.out.println(passwordEncoder.encode(user.getPassword()));
	          // user.setPassword(passwordEncoder.encode(user.getPassword()));
	            user.setPassword(register.getPassword());
	            user.setOtp(otp);
	            user.setOtpGeneratedTime(LocalDateTime.now());
	           // userRepository.save(user);

	            return registerRepository.save(user);  }

	    }
	    public String verifyAccount(String email, String otp) {
	        RegisterwithOTP user = registerRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
	        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
	                LocalDateTime.now()).getSeconds() < (1 * 120)) {
	            user.setOtp(otp);
	           registerRepository.save(user);
	            return "OTP verified You can continue";
	        }
	        return "Please regenerate otp and try again";
	    }
	    public String regenerateOtp(String email) {
	        RegisterwithOTP user = registerRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
	        String otp = otpUtil.generateOtp();
	        try {
	            emailUtil.sendOtpEmail(email, otp);
	        } catch (Exception e) {
	            throw new RuntimeException("Unable to send otp please try again");
	        }
	        user.setOtp(otp);
	        user.setOtpGeneratedTime(LocalDateTime.now());
	        registerRepository.save(user);
	        return "Email sent... please verify account within 2 minute";
	    }

	    public boolean login(Login login) {
	     RegisterwithOTP user = registerRepository.findByEmail(login.getEmail())
	                .orElseThrow(
	                        () -> new RuntimeException("User not found with this email: " + login.getEmail()));
	        //if (user != null && passwordEncoder.matches(login.getPassword(),user.getPassword())) 
	    System.out.println(user.getEmail());
	     System.out.println(user.getPassword());
	     System.out.println(login.getPassword());
	    if (user != null && user.getPassword().equals(login.getPassword()))   
	     {
	    	 	
	            return true;
	        }
	        return false;
	    }
	public String forgotPassword(String email) {

	    RegisterwithOTP user = registerRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with email" + email));


	    String otp = otpUtil.generateOtp();
	    try {
	        emailUtil.sendOtpEmail(user.getEmail(), otp);
	    } catch (Exception e) {
	        throw new RuntimeException("Unable to send otp please try again");
	    }

	    user.setOtp(otp);
	    user.setOtpGeneratedTime(LocalDateTime.now());
	    registerRepository.save(user);
	    return user.getEmail();

	}
	public String setPassword(String email, String newpassword)
	{
	    RegisterwithOTP user = registerRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with email" + email));
	   String encodedPassword = passwordEncoder.encode(newpassword);
        user.setPassword(encodedPassword);
	    //user.setPassword(newpassword);
	    
	    registerRepository.save(user);
	    return "new password set sucessfully";
	}
	public List<RegisterwithOTP> getAllJobRecruiters() {
        return registerRepository.findAll();
    }
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<RegisterwithOTP> register =registerRepository.findByEmail(username);
		
		
		if (register.isPresent()) {
		    RegisterwithOTP registration = register.get();

		    String email = registration.getEmail();
		    String password = registration.getPassword();
		    String role=registration.getRoles();
		    // Access other fields as needed
		
		 return  new User(email, password,Arrays.stream(role.split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()));
	}
	 else {
		 throw new IllegalArgumentException("Email id not found Exception");
	}
	
}
}
