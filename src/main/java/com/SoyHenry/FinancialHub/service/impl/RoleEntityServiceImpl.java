package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.role.RoleDtoRequest;
import com.SoyHenry.FinancialHub.dto.role.RoleDtoResponse;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.RoleEntity;
import com.SoyHenry.FinancialHub.entities.RoleEnum;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import com.SoyHenry.FinancialHub.mapper.RoleEntityMapper;
import com.SoyHenry.FinancialHub.repository.RoleRepository;
import com.SoyHenry.FinancialHub.service.RoleEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleEntityServiceImpl implements RoleEntityService {

    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;


    @Override
    public void createRole(RoleDtoRequest roleDtoRequest) {

        Optional<RoleEntity> existingRole = roleRepository.findByRole(roleDtoRequest.getRoleEnum());
        if(existingRole.isEmpty()){
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRole(roleDtoRequest.getRoleEnum());
            roleRepository.save(roleEntity);
        } else {
            throw new RuntimeException("El Rol " + roleDtoRequest.getRoleEnum() + " ya existe y no se puede duplicar");
        }


    }

    @Override
    public void updateRole(RoleDtoRequest roleDtoRequest, Long id) {
        Optional<RoleEntity> optionalRole = roleRepository.findById(id);

        if(optionalRole.isPresent()){
            RoleEntity role = optionalRole.get();

            role.setRole(roleDtoRequest.getRoleEnum());

            roleRepository.save(role);
        }
    }

    @Override
    public RoleDtoResponse getRoleById(Long id) {
        Optional<RoleEntity> role = roleRepository.findById(id);
        return role.map(roleEntityMapper::mapToDtoResponse).orElse(null);
    }

    @Override
    public RoleDtoResponse getRoleByEnum(String roleEnum) {

        Optional<RoleEntity> role = roleRepository.findByRole(RoleEnum.valueOf(roleEnum));
        return role.map(roleEntityMapper::mapToDtoResponse).orElse(null);
    }

    @Override
    public List<RoleDtoResponse> getAllRoles() {

        List<RoleEntity> roles = roleRepository.findAll();

        return roles.stream()
                .map(role -> new RoleDtoResponse(
                        role.getId(),
                        role.getRole()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDtoResponse findByRoleEnum(String roleEnum) {
        Optional<RoleEntity> role = roleRepository.findByRole(RoleEnum.valueOf(roleEnum));
        return role.map(roleEntityMapper::mapToDtoResponse).orElse(null);
    }

    @Override
    public Optional<UserEntityDtoResponse> findByUsernameWithRoles(String username) {

        Optional<UserEntity> userOptional = roleRepository.findByUsernameWithRoles(username);
        return userOptional.map(user ->{
            UserEntityDtoResponse response = new UserEntityDtoResponse();
            response.setUsername(user.getUsername());
            response.setRoles(user.getRoles().stream()
                    .map(RoleEntity::getRole)
                    .collect(Collectors.toSet()));
            return response;
                }

                );
    }


}
