package com.branchtracker.branchtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="clients")
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String nationalId; // Uganda National ID e.g. CM90012345PDFL

    private String phone;

    @Email(message = "Enter a valid email")
    private String email;

    private String address;

    private LocalDate dateOfBirth; // stores date only — no time needed

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch; // which branch registered this client

    private Boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if(this.active==null) this.active=true;
    }
}
