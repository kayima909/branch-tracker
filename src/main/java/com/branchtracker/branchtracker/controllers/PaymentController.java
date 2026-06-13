package com.branchtracker.branchtracker.controllers;

import com.branchtracker.branchtracker.entity.Payment;
import com.branchtracker.branchtracker.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    // Record a new payment — LOAN_OFFICER and above
    @PostMapping
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<Payment> recordPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.recordPayment(payment));
    }

    // Get payments by loan
    @GetMapping("/loan/{loanId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<List<Payment>> getPaymentsByLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(paymentService.getPaymentsByLoan(loanId));
    }

    // Get payments by branch
    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<List<Payment>> getPaymentsByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBranch(branchId));
    }

    // Get total collected by branch
    @GetMapping("/analytics/collected/branch/{branchId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<?> getTotalCollectedByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(Map.of(
                "branchId", branchId,
                "totalCollected", paymentService.getTotalCollectedByBranch(branchId)
        ));
    }

    // Get total collected across all branches — HQ_ADMIN only
    @GetMapping("/analytics/collected")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<?> getTotalCollected() {
        return ResponseEntity.ok(Map.of(
                "totalCollected", paymentService.getTotalCollected()
        ));
    }
}

