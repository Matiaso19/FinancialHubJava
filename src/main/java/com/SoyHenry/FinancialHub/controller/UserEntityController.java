package com.SoyHenry.FinancialHub.controller;

import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoRequest;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.service.UserEntityService;
import com.SoyHenry.FinancialHub.service.impl.UserEntityServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserEntityController {

    private final UserEntityService userEntityService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntityDtoResponse>> getAllUsers(){
        try{
            List<UserEntityDtoResponse> users = userEntityService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserEntityDtoRequest userEntityDtoRequest){

        try{

            userEntityService.createUser(userEntityDtoRequest);
            return new ResponseEntity<>("Usuario creado exitosamente",HttpStatus.CREATED);

        } catch (Exception e){

            return new ResponseEntity<>("HUbo un error al crear el usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userEntityServiceImpl.isUser(#id, principal)")
    public ResponseEntity<UserEntityDtoResponse> getUserById(@PathVariable Long id){
        try{
            UserEntityDtoResponse user = userEntityService.getUserById(id);
            System.out.println(user.toString() + "este es el user");
            if (user != null){
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            UserEntityDtoResponse eliminateUser = userEntityService.getUserById(id);
            if(eliminateUser != null){
                userEntityService.deleteUser(id);
                return new ResponseEntity<>("Usuario con id: " + id + "eliminado correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontro el usuario con id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el user con id: " + id + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') or @userEntityServiceImpl.isUser(#id, principal)")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody @Valid UserEntityDtoRequest userEntityDtoRequest){
        try {
            UserEntityDtoResponse updateUser = userEntityService.getUserById(id);
            if(updateUser != null){
                userEntityService.updateUser(userEntityDtoRequest, id);
                return new ResponseEntity<>("Usuario modificado correctamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontro el usuario a modificar", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Hubo un error al modificar el usuario " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/find/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntityDtoResponse> findUserByUserName(@PathVariable String username) {
        try {
            Optional<UserEntityDtoResponse> user = userEntityService.findByUsername(username);
                return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


    }

    @GetMapping("/find/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntityDtoResponse> getUserByEmail(@PathVariable @Email String email){
        try {
            Optional<UserEntityDtoResponse> user = userEntityService.findByEmail(email);
            return user.map(value-> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/document/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntityDtoResponse> getUserByDocument(@PathVariable String document){
        try {
            Optional<UserEntityDtoResponse> user = userEntityService.findByDocumentId(document);
            return user.map(value-> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
