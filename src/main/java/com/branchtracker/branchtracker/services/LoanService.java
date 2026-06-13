package com.branchtracker.branchtracker.services;

import com.branchtracker.branchtracker.entity.Loan;
import com.branchtracker.branchtracker.enums.LoanStatus;
import com.branchtracker.branchtracker.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service

public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // Create a new loan
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    // Get all loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Get all loans for a specific branch
    public List<Loan> getLoansByBranch(Long branchId) {
        return loanRepository.findByBranchId(branchId);
    }

    // Get all loans for a specific client
    public List<Loan> getLoansByClient(Long clientId) {
        return loanRepository.findByClientId(clientId);
    }

    // Get loans by status
    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status);
    }

    // Get loans by branch and status
    public List<Loan> getLoansByBranchAndStatus(Long branchId, LoanStatus status) {
        return loanRepository.findByBranchIdAndStatus(branchId, status);
    }

    // Get a loan by ID
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));
    }

    // Update loan status
    public Loan updateLoanStatus(Long id, LoanStatus status) {
        Loan loan = getLoanById(id);
        loan.setStatus(status);
        return loanRepository.save(loan);
    }

    // Get total outstanding balance across all branches
    public BigDecimal getTotalOutstandingBalance() {
        return loanRepository.getTotalOutstandingBalance();
    }

    // Get total outstanding balance for a specific branch
    public BigDecimal getTotalOutstandingBalanceByBranch(Long branchId) {
        return loanRepository.getTotalOutstandingBalanceByBranch(branchId);
    }

    // Count loans by status for a branch
    public long countLoansByBranchAndStatus(Long branchId, LoanStatus status) {
        return loanRepository.countByBranchIdAndStatus(branchId, status);
    }
}

