package com.SoyHenry.FinancialHub.dto.role;

import com.SoyHenry.FinancialHub.entities.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoResponse {

    private Long id;
    private RoleEnum roleEnum;

}
