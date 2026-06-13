package com.branchtracker.branchtracker.entity;

import com.branchtracker.branchtracker.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="loans")
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Principal amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal principalAmount; // original amount borrowed

    @Column(precision = 15, scale = 2)
    private BigDecimal interestRate; // e.g. 15.00 means 15%

    @Column(precision = 15, scale = 2)
    private BigDecimal totalRepayable; // principalAmount + interest

    @Column(precision = 15, scale = 2)
    private BigDecimal amountPaid; // how much has been paid so far

    @Column(precision = 15, scale = 2)
    private BigDecimal outstandingBalance; // totalRepayable - amountPaid

    private Integer termMonths; // how many months to repay

    private LocalDate startDate;  // when loan was disbursed
    private LocalDate dueDate;    // when loan must be fully repaid

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status = LoanStatus.PENDING;

    private String purpose; // e.g. "School fees", "Business capital"
    private String notes;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // who borrowed this loan

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch; // which branch gave out this loan

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy; // which loan officer created this record

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.amountPaid == null) this.amountPaid = BigDecimal.ZERO;
        if (this.outstandingBalance == null && this.totalRepayable != null) {
            this.outstandingBalance = this.totalRepayable;
        }
    }
}
