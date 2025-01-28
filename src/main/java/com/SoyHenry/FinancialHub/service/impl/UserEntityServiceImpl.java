package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.role.RoleDtoResponse;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoRequest;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.*;
import com.SoyHenry.FinancialHub.mapper.UserEntityMapper;
import com.SoyHenry.FinancialHub.repository.RoleRepository;
import com.SoyHenry.FinancialHub.repository.TransactionRepository;
import com.SoyHenry.FinancialHub.repository.UserRepository;
import com.SoyHenry.FinancialHub.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final TransactionRepository transactionRepository;


    @Override
    public void createUser(UserEntityDtoRequest userEntityDtoRequest) {
        UserEntity newUser = userEntityMapper.mapToUserEntity(userEntityDtoRequest);

        //super importante este paso
        newUser.setPassword(passwordEncoder.encode(userEntityDtoRequest.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();

        if(userEntityDtoRequest.getRoles() == null || userEntityDtoRequest.getRoles().isEmpty()){
            RoleEntity defaultRole = roleRepository.findByRole(RoleEnum.USER)
                    .orElseThrow(()-> new RuntimeException("Rol USER no encontrado"));

            roles.add(defaultRole);
        } else {

            for(RoleEnum roleEnum : userEntityDtoRequest.getRoles()){
                RoleEntity roleEntity = roleRepository.findByRole(roleEnum)
                        .orElseThrow(()->new RuntimeException("Rol no encontrado: " + roleEnum));
                roles.add(roleEntity);
            }

        }



        newUser.setRoles(roles);

        userRepository.save(newUser);
        }



    @Override
    public void updateUser(UserEntityDtoRequest userEntityDtoRequest, Long id) {

        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            user.setDocumentId(userEntityDtoRequest.getDocumentId());
            user.setUsername(userEntityDtoRequest.getUsername());
            user.setName(userEntityDtoRequest.getName());
            user.setLastName(userEntityDtoRequest.getLastName());
            user.setEmail(userEntityDtoRequest.getEmail());
            user.setDateOfBirth(userEntityDtoRequest.getDateOfBirth());

            // Actualizar la contraseña si está presente
            if (userEntityDtoRequest.getPassword() != null && !userEntityDtoRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userEntityDtoRequest.getPassword()));
            }

            user.setIsEnabled(userEntityDtoRequest.getIsEnabled());
            user.setAccountNoExpired(userEntityDtoRequest.getAccountNoExpired());
            user.setAccountNoLocked(userEntityDtoRequest.getAccountNoLocked());
            user.setCredentialsNoExpired(userEntityDtoRequest.getCredentialsNoExpired());

            if (userEntityDtoRequest.getRoles() != null && !userEntityDtoRequest.getRoles().isEmpty()) {
                // Buscar roles existentes
                Set<RoleEntity> roles = userEntityDtoRequest.getRoles().stream()
                        .map(roleEnum -> {
                            RoleEntity roleEntity = roleRepository.findByRole(roleEnum)
                                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleEnum));
                            return roleEntity;
                        })
                        .collect(Collectors.toSet());

                user.setRoles(roles);
            }

            userRepository.save(user);
        }
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);


    }

    @Override
    public List<UserEntityDtoResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return mapToDtoList(users);
    }

    @Override
    public UserEntityDtoResponse getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        return user.map(userEntityMapper::mapToDtoResponse).orElse(null);
    }

    @Override
    public Optional<UserEntityDtoResponse> findByUsername(String username) {
       Optional<UserEntity> user = userRepository.findByUsername(username);
       return user.map(userEntityMapper::mapToDtoResponse);


    }

    @Override
    public Optional<UserEntityDtoResponse> findByEmail(String email) {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(userEntityMapper::mapToDtoResponse);
    }

    @Override
    public Optional<UserEntityDtoResponse> findByDocumentId(String documentId) {

        Optional<UserEntity> user = userRepository.findByDocumentId(documentId);
        return user.map(userEntityMapper::mapToDtoResponse);
    }

    @Override
    public Boolean isUser(Long userId, UserDetails userDetails) {
        String username = userDetails.getUsername();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));

        return user.getId().equals(userId);
    }

    public Boolean isUserAccount(Long accountId, UserDetails userDetails){
        String username = userDetails.getUsername();

        UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));

        return user.getAccount().getId().equals(accountId);

    }

    public Boolean isUserTransaction(Long transactionId, UserDetails userDetails){
        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()-> new RuntimeException("Transaccion no encontrada"));

        return user.getAccount().getId().equals(transaction.getAccount().getId());
    }

    private List<UserEntityDtoResponse> mapToDtoList(List<UserEntity> users){
        return users.stream()
                .map(userEntityMapper::mapToDtoResponse)
                .collect(Collectors.toList());
    }
}
