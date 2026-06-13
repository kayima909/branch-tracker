package com.branchtracker.branchtracker.entity;

import com.branchtracker.branchtracker.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payments")
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Payment amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.SUCCESSFUL;

    private String reference;  // e.g. Mobile Money transaction ID "UG2025041012345"
    private String method;     // e.g. "MOBILE_MONEY", "CASH", "BANK_TRANSFER"
    private String notes;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan; // which loan this payment is for

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch; // which branch received this payment

    @ManyToOne
    @JoinColumn(name = "recorded_by")
    private User recordedBy; // which officer recorded this payment

    @Column(updatable = false)
    private LocalDateTime paidAt; // exact date and time payment was made

    @PrePersist
    public void prePersist() {
        this.paidAt = LocalDateTime.now();
    }
}

