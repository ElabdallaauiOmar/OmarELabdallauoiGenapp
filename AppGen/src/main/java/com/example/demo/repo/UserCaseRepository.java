package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entitie.UserCase;

public interface UserCaseRepository extends JpaRepository<UserCase, Long> {}
