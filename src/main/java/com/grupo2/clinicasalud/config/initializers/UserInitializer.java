package com.grupo2.clinicasalud.config.initializers;

import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.repository.RolRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class UserInitializer {

    @Bean
    public CommandLineRunner initUsers(RolRepository rolRepository, UsuarioRepository userRepository){
        return args -> {
            if(userRepository.findByEmail("admin@test.com").isEmpty()){

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                Optional<Rol> rol = rolRepository.findByNombre("Admin");
                if(rol.isEmpty()){
                    throw new Exception("No se encontró el rol Admin");
                }
                Usuario adm = new Usuario();
                adm.setEmail("admin@test.com");
                adm.setPassword(passwordEncoder.encode("Cl1n1c4$alUd"));
                Set<Rol> roles = new HashSet<>();
                roles.add(rol.get());
                adm.setRoles(roles);
                userRepository.save(adm);
            }
        };
    }

}
