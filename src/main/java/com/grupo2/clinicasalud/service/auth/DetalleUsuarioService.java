package com.grupo2.clinicasalud.service.auth;

import com.grupo2.clinicasalud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DetalleUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Preferimos el uso de correo para no obligar al visitante a pensar en un nombre de cuenta,
    // pero en una aplicación más avanzada los datos deberán estar encriptados para que nadie acceda al correo
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
    }
}
