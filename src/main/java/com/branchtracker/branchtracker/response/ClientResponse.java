package com.branchtracker.branchtracker.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientResponse {
    private Long id;
    private String fullName;
    private String nationalId;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private String branchName;
    private boolean active;
    private LocalDateTime createdAt;
}

