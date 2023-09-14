package com.talentstream.entity;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
public class Login {
	 private String email; 	
	 public Login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	private String password;
	 
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	    

}
