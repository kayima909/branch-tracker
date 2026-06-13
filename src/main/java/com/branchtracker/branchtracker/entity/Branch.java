package com.branchtracker.branchtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @NotBlank(message = "Branch name is required")
    @Column(nullable = false,  unique = true)
    private String name;

    @NotBlank(message = "location is required")
    @Column(nullable = false)
    private String location;

    private String region;

    private String contactPhone;
    @Email(message = "Enter valid Email")
    private String contactEmail;

    private Boolean active = true;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }


}
