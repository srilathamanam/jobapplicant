package com.talentstream.entity;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class RegisterwithOTP {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String email;
    private String mobilenumber;
    
    @Column(nullable = false)
    private String password;
    
    private String otp;
    private LocalDateTime otpGeneratedTime;
    
    @Column(nullable = false)
    private String roles="ROLE_JOBAPPLICANT";
    
	  public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	  public String getRoles() {
			return roles;
		}
		public void setId(String roles) {
			this.roles = roles;
		}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getOtpGeneratedTime() {
		return otpGeneratedTime;
	}
	public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}
	
		

}
