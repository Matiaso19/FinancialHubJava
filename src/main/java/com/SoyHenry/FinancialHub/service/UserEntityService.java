package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoRequest;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserEntityService {

    void createUser(UserEntityDtoRequest userEntityDtoRequest);
    void updateUser(UserEntityDtoRequest userEntityDtoRequest, Long id);
    void deleteUser(Long id);
    List<UserEntityDtoResponse> getAllUsers();
    UserEntityDtoResponse getUserById(Long id);
    Optional<UserEntityDtoResponse> findByUsername(String username);
    Optional<UserEntityDtoResponse> findByEmail(String email);
    Optional<UserEntityDtoResponse> findByDocumentId(String documentId);

}
