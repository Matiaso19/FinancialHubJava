package com.SoyHenry.FinancialHub.mapper;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoRequest;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.RoleEntity;
import com.SoyHenry.FinancialHub.entities.RoleEnum;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntityDtoRequest mapToDtoRequest(UserEntity userEntity);

    @Mapping(target = "roles", expression = "java(mapRolesToEnums(userEntity.getRoles()))")
    UserEntityDtoResponse mapToDtoResponse(UserEntity userEntity);

    UserEntity mapToUserEntity(UserEntityDtoRequest userEntityDtoRequest);

    default Set<RoleEnum> mapRolesToEnums(Set<RoleEntity> roles){
        return roles.stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toSet());
    }


}
