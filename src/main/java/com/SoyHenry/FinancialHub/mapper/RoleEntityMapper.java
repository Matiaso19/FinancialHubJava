package com.SoyHenry.FinancialHub.mapper;

import com.SoyHenry.FinancialHub.dto.role.RoleDtoRequest;
import com.SoyHenry.FinancialHub.dto.role.RoleDtoResponse;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoRequest;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.RoleEntity;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

    RoleDtoRequest mapToDtoRequest(RoleEntity roleEntity);
    RoleDtoResponse mapToDtoResponse(RoleEntity roleEntity);
    RoleEntity mapToUserEntity(RoleDtoRequest roleDtoRequest);

}
