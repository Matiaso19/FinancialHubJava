package com.SoyHenry.FinancialHub.dto.role;

import com.SoyHenry.FinancialHub.entities.RoleEnum;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoRequest {

    @NotNull(message = "El rol no puede estar vacío.")
    private RoleEnum roleEnum;



}
