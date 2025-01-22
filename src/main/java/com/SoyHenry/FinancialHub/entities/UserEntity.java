package com.SoyHenry.FinancialHub.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String documentId;

    @Column(unique = true)
    private String username;


    private String name;


    private String lastName;

    @Column(unique = true)
    private String email;


    private LocalDate dateOfBirth;


    private String password;

    @Column(name = "is_Enabled", nullable = false)
    private Boolean isEnabled = true;

    @Column(name = "account_No_Expired", nullable = false)
    private Boolean accountNoExpired = true;

    @Column(name = "account_No_Locked", nullable = false)
    private Boolean accountNoLocked = true;

    @Column(name = "credentials_No_Expired", nullable = false)
    private Boolean credentialsNoExpired = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;



}
