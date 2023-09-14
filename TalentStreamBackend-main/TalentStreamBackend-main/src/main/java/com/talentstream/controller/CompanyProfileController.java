package com.talentstream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentstream.entity.CompanyProfile;
import com.talentstream.service.CompanyProfileService;

import java.util.Optional;

@RestController
public class CompanyProfileController {

    private final CompanyProfileService companyProfileService;

    @Autowired
    public CompanyProfileController(CompanyProfileService companyProfileService) {
        this.companyProfileService = companyProfileService;
    }

    @PostMapping("/save-company-profiles")
    public ResponseEntity<CompanyProfile> createCompanyProfile(@RequestBody CompanyProfile companyProfile) {
        CompanyProfile savedProfile = companyProfileService.saveCompanyProfile(companyProfile);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    @GetMapping("/getCompanyProfile/{id}")
    public ResponseEntity<CompanyProfile> getCompanyProfileById(@PathVariable Long id) {
        Optional<CompanyProfile> companyProfile = companyProfileService.getCompanyProfileById(id);
        return companyProfile.map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // You can add more endpoints for updating, deleting, or querying company profiles as needed
}

