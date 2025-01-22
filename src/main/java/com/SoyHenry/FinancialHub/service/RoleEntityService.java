package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.dto.role.RoleDtoRequest;
import com.SoyHenry.FinancialHub.dto.role.RoleDtoResponse;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.RoleEntity;
import com.SoyHenry.FinancialHub.entities.RoleEnum;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface RoleEntityService {

    void createRole(RoleDtoRequest roleDtoRequest);
    void updateRole(RoleDtoRequest roleDtoRequest, Long id);
    RoleDtoResponse getRoleById(Long id);
    RoleDtoResponse getRoleByEnum(String roleEnum);
    List<RoleDtoResponse> getAllRoles();
    void deleteRole(Long id);
    RoleDtoResponse findByRoleEnum(String roleEnum);
    Optional<UserEntityDtoResponse> findByUsernameWithRoles(String username);


}
