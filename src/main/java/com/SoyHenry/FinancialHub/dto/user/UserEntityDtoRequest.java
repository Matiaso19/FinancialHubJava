package com.SoyHenry.FinancialHub.dto.user;

import com.SoyHenry.FinancialHub.entities.RoleEntity;
import com.SoyHenry.FinancialHub.entities.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDtoRequest {

    @NotBlank(message = "El número de identificación única no puede estar vacío.")

    @Size(min = 5, max = 20)
    private String documentId;

    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)@NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String password;


    private Boolean isEnabled = true;


    private Boolean accountNoExpired = true;


    private Boolean accountNoLocked = true;


    private Boolean credentialsNoExpired = true;


    private Set<RoleEnum> roles;

    private Long accountId;

}
