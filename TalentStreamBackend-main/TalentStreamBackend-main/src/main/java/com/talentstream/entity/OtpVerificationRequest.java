package com.talentstream.entity;

public class OtpVerificationRequest {

    private String enteredOtp;

    public String getEnteredOtp() {
        return enteredOtp;
    }

    public void setEnteredOtp(String enteredOtp) {
        this.enteredOtp = enteredOtp;
    }

	public OtpVerificationRequest(String enteredOtp) {
		super();
		this.enteredOtp = enteredOtp;
	}

	public OtpVerificationRequest() {
		super();
	}
    
    // Constructors, getters, setters, etc.
}

