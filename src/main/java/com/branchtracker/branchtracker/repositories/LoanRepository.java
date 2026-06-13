package com.branchtracker.branchtracker.repositories;

import com.branchtracker.branchtracker.entity.Loan;
import com.branchtracker.branchtracker.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Find all loans belonging to a specific branch
    List<Loan> findByBranchId(Long branchId);

    // Find all loans for a specific client
    List<Loan> findByClientId(Long clientId);

    // Find all loans by status (e.g. all DEFAULTED loans)
    List<Loan> findByStatus(LoanStatus status);

    // Find all loans for a branch filtered by status
    List<Loan> findByBranchIdAndStatus(Long branchId, LoanStatus status);

    // Count loans by status for a specific branch
    long countByBranchIdAndStatus(Long branchId, LoanStatus status);

    // Get total outstanding balance across all branches
    @Query("SELECT SUM(l.outstandingBalance) FROM Loan l WHERE l.status = 'ACTIVE'")
    BigDecimal getTotalOutstandingBalance();

    // Get total outstanding balance for a specific branch
    @Query("SELECT SUM(l.outstandingBalance) FROM Loan l WHERE l.branch.id = :branchId AND l.status = 'ACTIVE'")
    BigDecimal getTotalOutstandingBalanceByBranch(Long branchId);
}

