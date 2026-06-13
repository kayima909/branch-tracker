package com.branchtracker.branchtracker.response;

import com.branchtracker.branchtracker.enums.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoanResponse {
    private Long id;
    private String clientName;
    private String branchName;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private BigDecimal totalRepayable;
    private BigDecimal amountPaid;
    private BigDecimal outstandingBalance;
    private Integer termMonths;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LoanStatus status;
    private String purpose;
    private LocalDateTime createdAt;
}

