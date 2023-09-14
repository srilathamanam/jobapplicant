package com.talentstream.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CompanyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jobrecruiterId", referencedColumnName = "recruiterId")
    private JobRecruiter jobRecruiter;

    private String companyName; // Fetched from JobRecruiter
    private String website;
    private String phoneNumber; // Fetched from JobRecruiter
    private String email; // Fetched from JobRecruiter
    private String headOffice;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] companyLogo; // Store the company logo as a byte array

    @ElementCollection
    private List<String> socialProfiles; // Store social profiles as a list of strings

    private String roles="ROLE_JOBRECRUITER"; // Add the role field

    // Constructors, getters, and setters

    // Constructors

    public CompanyProfile() {
        // Default constructor
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobRecruiter getJobRecruiter() {
        return jobRecruiter;
    }

    public void setJobRecruiter(JobRecruiter jobRecruiter) {
        this.jobRecruiter = jobRecruiter;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public List<String> getSocialProfiles() {
        return socialProfiles;
    }

    public void setSocialProfiles(List<String> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String role) {
        this.roles = role;
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
                "id=" + id +
                ", jobRecruiter=" + jobRecruiter +
                ", companyName='" + companyName + '\'' +
                ", website='" + website + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", headOffice='" + headOffice + '\'' +
                ", companyLogo='" + (companyLogo != null ? "Present" : "Not Present") + '\'' +
                ", socialProfiles=" + socialProfiles +
                ", roles='" + roles + '\'' +
                '}';
    }
}
