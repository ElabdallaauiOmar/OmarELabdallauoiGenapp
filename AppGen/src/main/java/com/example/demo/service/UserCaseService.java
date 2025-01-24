package com.example.demo.service;

import com.example.demo.entitie.Case;
import com.example.demo.entitie.UserCase;
import com.example.demo.repo.CaseRepository;
import com.example.demo.repo.UserCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCaseService {

    @Autowired
    private com.example.demo.repo.UserCaseRepository userCaseRepository;

    @Autowired
    private CaseRepository caseRepository;

    /**
     * Split the content of a UserCase into smaller cases and save them in the Case table.
     */
    public List<Case> splitAndSaveCases(Long userCaseId) {
        // Retrieve the original UserCase
        UserCase originalUserCase = userCaseRepository.findById(userCaseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCase not found with ID: " + userCaseId));

        // Split the content into smaller cases
        List<Case> splitCases = splitContentIntoCases(originalUserCase);

        // Save the split cases to the database
        return caseRepository.saveAll(splitCases);
    }

    /**
     * Helper method to split the content of a UserCase into smaller cases.
     */
    private List<Case> splitContentIntoCases(UserCase userCase) {
        // Split the content by period or custom delimiter (e.g., ";")
        String[] splitContents = userCase.getContent().split("\\."); // You can adjust the regex as needed

        List<Case> cases = new ArrayList<>();
        for (String caseContent : splitContents) {
            if (!caseContent.trim().isEmpty()) {
                cases.add(new Case(caseContent.trim(), userCase));
            }
        }
        return cases;
    }

    /**
     * Retrieve all Cases for a specific UserCase.
     */
    public List<Case> getCasesForUserCase(Long userCaseId) {
        UserCase userCase = userCaseRepository.findById(userCaseId)
                .orElseThrow(() -> new IllegalArgumentException("UserCase not found with ID: " + userCaseId));
        return userCase.getCases();
    }

    public UserCase saveUserCase(String content) {
        // Create a new UserCase entity
        UserCase userCase = new UserCase(content);

        // Save it to the database
        return userCaseRepository.save(userCase);
    }
}
