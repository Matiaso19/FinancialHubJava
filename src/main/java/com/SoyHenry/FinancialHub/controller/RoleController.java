package com.SoyHenry.FinancialHub.controller;


import com.SoyHenry.FinancialHub.dto.role.RoleDtoRequest;
import com.SoyHenry.FinancialHub.dto.role.RoleDtoResponse;
import com.SoyHenry.FinancialHub.service.RoleEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleEntityService roleEntityService;

    @GetMapping
    public ResponseEntity<List<RoleDtoResponse>> getAllRoles(){
        try {
            List<RoleDtoResponse> roleDtoResponseList = roleEntityService.getAllRoles();
            return new ResponseEntity<>(roleDtoResponseList,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PostMapping("/create")
    public ResponseEntity<String> createRole(@RequestBody @Valid RoleDtoRequest roleDtoRequest){
        try{
            roleEntityService.createRole(roleDtoRequest);
            return new ResponseEntity<>("Rol creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Hubo un problema al crear el rol: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
