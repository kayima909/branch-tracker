package com.branchtracker.branchtracker.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BranchResponse {
    private Long id;
    private String name;
    private String location;
    private String region;
    private String contactPhone;
    private String contactEmail;
    private boolean active;
    private LocalDateTime createdAt;
}

