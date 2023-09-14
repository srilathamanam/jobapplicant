package com.talentstream.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentstream.entity.RegisterwithOTP;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterwithOTP, Integer> {
    Optional<RegisterwithOTP> findByEmail(String email);

}