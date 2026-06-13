package com.branchtracker.branchtracker.response;

import com.branchtracker.branchtracker.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private String reference;
    private String method;
    private String loanId;
    private String branchName;
    private String recordedBy;
    private LocalDateTime paidAt;
}

