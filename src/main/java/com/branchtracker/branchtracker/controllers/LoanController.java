package com.branchtracker.branchtracker.controllers;

import com.branchtracker.branchtracker.entity.Loan;
import com.branchtracker.branchtracker.enums.LoanStatus;
import com.branchtracker.branchtracker.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/loans")
@RestController
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    // Create a new loan — LOAN_OFFICER and above
    @PostMapping
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.createLoan(loan));
    }

    // Get all loans — HQ_ADMIN only
    @GetMapping
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    // Get loans by branch — HQ_ADMIN and BRANCH_MANAGER
    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<List<Loan>> getLoansByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(loanService.getLoansByBranch(branchId));
    }

    // Get loans by client
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<List<Loan>> getLoansByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(loanService.getLoansByClient(clientId));
    }

    // Get loans by status — HQ_ADMIN only
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.getLoansByStatus(status));
    }

    // Get loans by branch and status
    @GetMapping("/branch/{branchId}/status/{status}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<List<Loan>> getLoansByBranchAndStatus(@PathVariable Long branchId,
                                                                @PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.getLoansByBranchAndStatus(branchId, status));
    }

    // Get a loan by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    // Update loan status — BRANCH_MANAGER and above
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable Long id,
                                                 @RequestBody Map<String, String> request) {
        LoanStatus status = LoanStatus.valueOf(request.get("status"));
        return ResponseEntity.ok(loanService.updateLoanStatus(id, status));
    }

    // Get total outstanding balance — HQ_ADMIN only
    @GetMapping("/analytics/outstanding")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<?> getTotalOutstandingBalance() {
        return ResponseEntity.ok(Map.of(
                "totalOutstandingBalance", loanService.getTotalOutstandingBalance()
        ));
    }

    // Get total outstanding balance for a branch
    @GetMapping("/analytics/outstanding/branch/{branchId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<?> getTotalOutstandingBalanceByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(Map.of(
                "branchId", branchId,
                "totalOutstandingBalance", loanService.getTotalOutstandingBalanceByBranch(branchId)
        ));
    }
}

