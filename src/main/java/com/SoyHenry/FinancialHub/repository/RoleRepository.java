package com.SoyHenry.FinancialHub.repository;

import com.SoyHenry.FinancialHub.entities.RoleEntity;
import com.SoyHenry.FinancialHub.entities.RoleEnum;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {


    Optional<RoleEntity> findByRole(RoleEnum roleEnum);

    //load user with his roles
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<UserEntity> findByUsernameWithRoles(@Param("username") String username);

}
