package com.branchtracker.branchtracker.repositories;
import com.branchtracker.branchtracker.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find all payments for a specific loan
    List<Payment> findByLoanId(Long loanId);

    // Find all payments received by a specific branch
    List<Payment> findByBranchId(Long branchId);

    // Get total amount collected by a specific branch
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.branch.id = :branchId AND p.status = 'SUCCESSFUL'")
    BigDecimal getTotalCollectedByBranch(Long branchId);

    // Get total amount collected across all branches
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'SUCCESSFUL'")
    BigDecimal getTotalCollected();
}

