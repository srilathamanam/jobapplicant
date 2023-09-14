package com.talentstream.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.talentstream.entity.Eligibility;
import com.talentstream.entity.Job;
import com.talentstream.entity.JobRecruiter;
import com.talentstream.service.EligibilityService;
import com.talentstream.service.JobRecruiterService;
import com.talentstream.service.JobService;

@RestController

public class JobController {
    private final JobService jobService;
//    private final EligibilityService eligibilityService;
//    JobRecruiterService recruiterService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
//        this.eligibilityService = eligibilityService;
//        this.recruiterService=recruiterService;
    }

    @PostMapping("/saveJob")
    public ResponseEntity<String> saveJob(@RequestBody @Valid Job job) {
         // Associate eligibility with the job
        Job savedJob = jobService.saveJob(job);
        return ResponseEntity.ok("Job saved successfully with ID: " + savedJob.getId());
        
        
    }
    
    @GetMapping("/viewJobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    // Other endpoints and methods...
}
