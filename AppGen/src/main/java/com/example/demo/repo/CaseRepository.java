package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitie.Case;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
}