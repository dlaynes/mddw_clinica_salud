package com.grupo2.clinicasalud.service.auth;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import com.grupo2.clinicasalud.repository.RolRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DetalleUsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private RolRepository rolRepository;

    // Preferimos el uso de correo para no obligar al visitante a pensar en un nombre de cuenta,
    // En una aplicación de nivel empresarial los datos deberán estar encriptados para que nadie acceda al correo
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
    }

    public Paciente getPacienteActual(){
        Usuario user = getUser();
        if(user == null) return null;
        return pacienteRepository.findByUsuarioId(user.getId()).orElse(null);
    }

    public Medico getMedicoActual(){
        Usuario user = getUser();
        if(user == null) return null;
        return medicoRepository.findByUsuarioId(user.getId()).orElse(null);
    }

    public Usuario getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Usuario> user = usuarioRepository.findByEmail(auth.getName());
        return user.orElse(null);
    }

    public Usuario guardarCliente(String email, String password, PasswordEncoder passwordEncoder){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        String encodedPassword = passwordEncoder.encode(password);
        usuario.setPassword(encodedPassword);

        Set<Rol> roles = new HashSet<>();
        Rol clienteRol = rolRepository.findByNombre("Cliente")
                .orElseThrow(() -> new RuntimeException("Error: Rol Cliente no encontrado."));
        roles.add(clienteRol);
        usuario.setRoles(roles);

        usuarioRepository.saveAndFlush(usuario);
        return usuario;
    }

}
