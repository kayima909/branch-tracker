package com.branchtracker.branchtracker.services;

import com.branchtracker.branchtracker.entity.Loan;
import com.branchtracker.branchtracker.entity.Payment;
import com.branchtracker.branchtracker.enums.LoanStatus;
import com.branchtracker.branchtracker.repositories.LoanRepository;
import com.branchtracker.branchtracker.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;

    // Record a new payment
    public Payment recordPayment(Payment payment) {
        // Get the loan this payment is for
        Loan loan = loanRepository.findById(payment.getLoan().getId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Update the loan's amountPaid and outstandingBalance
        BigDecimal newAmountPaid = loan.getAmountPaid().add(payment.getAmount());
        BigDecimal newOutstandingBalance = loan.getOutstandingBalance().subtract(payment.getAmount());

        loan.setAmountPaid(newAmountPaid);
        loan.setOutstandingBalance(newOutstandingBalance);

        // If fully paid, mark loan as COMPLETED
        if (newOutstandingBalance.compareTo(BigDecimal.ZERO) <= 0) {
            loan.setStatus(LoanStatus.COMPLETED);
        }

        loanRepository.save(loan);
        return paymentRepository.save(payment);
    }

    // Get all payments for a specific loan
    public List<Payment> getPaymentsByLoan(Long loanId) {
        return paymentRepository.findByLoanId(loanId);
    }

    // Get all payments for a specific branch
    public List<Payment> getPaymentsByBranch(Long branchId) {
        return paymentRepository.findByBranchId(branchId);
    }

    // Get total amount collected by a branch
    public BigDecimal getTotalCollectedByBranch(Long branchId) {
        return paymentRepository.getTotalCollectedByBranch(branchId);
    }

    // Get total amount collected across all branches
    public BigDecimal getTotalCollected() {
        return paymentRepository.getTotalCollected();
    }
}
