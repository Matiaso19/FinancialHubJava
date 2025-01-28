package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.entities.UserEntity;
import com.SoyHenry.FinancialHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe"));
        List<SimpleGrantedAuthority> authorityList =new ArrayList<>();
        user.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name()))));
        return new User(user.getUsername(),
                user.getPassword(),
                user.getIsEnabled(),
                user.getAccountNoExpired(),
                user.getCredentialsNoExpired(),
                user.getAccountNoLocked(),
                authorityList);
    }
}
