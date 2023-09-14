package com.talentstream.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company_profile_id",referencedColumnName = "id")
    private CompanyProfile companyProfile;

    @Column(nullable = false)
    private String jobTitle;

    public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}

	@Column(nullable = false)
    private int minimumExperience;

    @Column(nullable = false)
    private int maximumExperience;

    @Column(nullable = false)
    private double minSalary;

    @Column(nullable = false)
    private double maxSalary;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String employeeType;

    @Column(nullable = false)
    private String industryType;

    @Column(nullable = false)
    private String minimumQualification;

    @Column(nullable = false)
    private String specialization;

    @ElementCollection
    //@CollectionTable(name = "job_skills", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill")
    private List<String> skillsRequired;

    @Column
    private String jobHighlights;

    @Column(nullable = false, length = 2000)
    private String description;

    @Lob
    @Column
    private byte[] uploadDocument; // Use byte[] to store the file content

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getMinimumExperience() {
		return minimumExperience;
	}

	public void setMinimumExperience(int minimumExperience) {
		this.minimumExperience = minimumExperience;
	}

	public int getMaximumExperience() {
		return maximumExperience;
	}

	public void setMaximumExperience(int maximumExperience) {
		this.maximumExperience = maximumExperience;
	}

	public double getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(double minSalary) {
		this.minSalary = minSalary;
	}

	public double getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(double maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getMinimumQualification() {
		return minimumQualification;
	}

	public void setMinimumQualification(String minimumQualification) {
		this.minimumQualification = minimumQualification;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public List<String> getSkillsRequired() {
		return skillsRequired;
	}

	public void setSkillsRequired(List<String> skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	public String getJobHighlights() {
		return jobHighlights;
	}

	public void setJobHighlights(String jobHighlights) {
		this.jobHighlights = jobHighlights;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getUploadDocument() {
		return uploadDocument;
	}

	public void setUploadDocument(byte[] uploadDocument) {
		this.uploadDocument = uploadDocument;
	}

    // Constructors, getters, setters, and other methods
    
}

