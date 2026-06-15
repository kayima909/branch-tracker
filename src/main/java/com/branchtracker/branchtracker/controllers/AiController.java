package com.branchtracker.branchtracker.controllers;

import com.branchtracker.branchtracker.services.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

    @RestController
    @RequestMapping("/api/ai")
    @RequiredArgsConstructor
    public class AiController {

        private final AiService aiService;

        // Generate AI report for a specific branch
        @GetMapping("/branch/{branchId}/report")
        @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
        public ResponseEntity<?> getBranchReport(@PathVariable Long branchId) {
            String report = aiService.generateBranchReport(branchId);
            return ResponseEntity.ok(Map.of(
                    "branchId", branchId,
                    "aiReport", report
            ));
        }

        // Assess loan risk for a specific client
        @GetMapping("/client/{clientId}/risk")
        @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
        public ResponseEntity<?> assessLoanRisk(@PathVariable Long clientId) {
            String assessment = aiService.assessLoanRisk(clientId);
            return ResponseEntity.ok(Map.of(
                    "clientId", clientId,
                    "riskAssessment", assessment
            ));
        }

        // Generate company-wide AI financial summary
        @GetMapping("/company/summary")
        @PreAuthorize("hasRole('HQ_ADMIN')")
        public ResponseEntity<?> getCompanySummary() {
            String summary = aiService.generateCompanySummary();
            return ResponseEntity.ok(Map.of(
                    "aiSummary", summary
            ));
        }
    }

