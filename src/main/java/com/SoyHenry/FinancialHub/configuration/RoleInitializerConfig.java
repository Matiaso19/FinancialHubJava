    package com.SoyHenry.FinancialHub.configuration;

    import com.SoyHenry.FinancialHub.entities.RoleEntity;
    import com.SoyHenry.FinancialHub.entities.RoleEnum;
    import com.SoyHenry.FinancialHub.entities.UserEntity;
    import com.SoyHenry.FinancialHub.repository.RoleRepository;
    import com.SoyHenry.FinancialHub.repository.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    import java.time.LocalDate;
    import java.util.Set;

    @Configuration
    @RequiredArgsConstructor
    public class RoleInitializerConfig {

        private final RoleRepository roleRepository;
        private final UserRepository userRepository;
        //private final BCryptPasswordEncoder passwordEncoder;

        @Bean
        public CommandLineRunner initRoles(){
            return args -> {
                if(roleRepository.count() == 0){
                    RoleEntity adminRole = new RoleEntity(null, RoleEnum.ADMIN, null);
                    RoleEntity userRole = new RoleEntity(null, RoleEnum.USER, null);
                    RoleEntity managerRole = new RoleEntity(null, RoleEnum.MANAGER, null);

                    roleRepository.save(adminRole);
                    roleRepository.save(userRole);
                    roleRepository.save(managerRole);
                    System.out.println("Roles iniciados correctamente");
                } else {
                    System.out.println("Los roles ya existen en la Base de Datos");
                }

//                RoleEntity adminRole = roleRepository.findByRole(RoleEnum.ADMIN)
//                        .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
//
//                UserEntity userAdmin = UserEntity.builder()
//                        .roles(Set.of(adminRole))
//                        .email("admin@example.com")
//                        .accountNoLocked(true)
//                        .accountNoExpired(true)
//                        .dateOfBirth(LocalDate.now())
//                        .name("Admin")
//                        .documentId("12345678")
//                        .account(null)
//                        .credentialsNoExpired(true)
//                        .lastName("AdminLastName")
//                        .isEnabled(true)
//                        .password(passwordEncoder.encode("adminPassword"))
//                        .username("adminUser")
//                        .build();
//
//                userRepository.save(userAdmin);
//                System.out.println("Usario admin creado");

            };
        }

    }
