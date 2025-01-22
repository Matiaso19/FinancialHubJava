package com.SoyHenry.FinancialHub.dto.user;

import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDtoResponse {

    private Long id;
    private String documentId;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Set<RoleEnum> roles;
    private Boolean isEnabled;
    private Boolean accountNoExpired;
    private Boolean accountNoLocked;
    private Boolean credentialsNoExpired;

    private Account account;

}
