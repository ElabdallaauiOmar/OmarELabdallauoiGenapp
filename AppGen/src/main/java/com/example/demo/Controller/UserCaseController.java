package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitie.Case;
import com.example.demo.entitie.UserCase;
import com.example.demo.service.UserCaseService;

@RestController
@RequestMapping("/api/user-cases")
public class UserCaseController {

	@Autowired
    private UserCaseService userCaseService;

    /**
     * Split a UserCase by ID and save the split cases in the Case table.
     */
    @PostMapping("/split/{id}")
    public ResponseEntity<List<Case>> splitUserCase(@PathVariable Long id) {
        List<Case> splitCases = userCaseService.splitAndSaveCases(id);
        return ResponseEntity.ok(splitCases);
    }

    /**
     * Retrieve all Cases for a specific UserCase.
     */
    @GetMapping("/{id}/cases")
    public ResponseEntity<List<Case>> getCasesForUserCase(@PathVariable Long id) {
        List<Case> cases = userCaseService.getCasesForUserCase(id);
        return ResponseEntity.ok(cases);
    }
}
