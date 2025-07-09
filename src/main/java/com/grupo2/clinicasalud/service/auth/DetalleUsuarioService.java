package com.grupo2.clinicasalud.service.auth;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DetalleUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Preferimos el uso de correo para no obligar al visitante a pensar en un nombre de cuenta,
    // En una aplicación de nivel empresarial los datos deberán estar encriptados para que nadie acceda al correo
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
    }

    public Paciente getPacienteActual(){
        Usuario user = getUser();
        if(user == null) return null;
        return user.getPaciente();
    }

    public Medico getMedicoActual(){
        Usuario user = getUser();
        if(user == null) return null;
        return user.getMedico();
    }

    public Usuario getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> user = usuarioRepository.findByEmail(auth.getName());
        return user.orElse(null);
    }

}
