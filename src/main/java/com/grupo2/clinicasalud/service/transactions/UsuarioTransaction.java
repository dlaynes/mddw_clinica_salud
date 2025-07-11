package com.grupo2.clinicasalud.service.transactions;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioTransaction {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void guardarUsuario(UsuarioForm usuarioForm,
                               Usuario usuario,
                               PasswordEncoder passwordEncoder,
                               UsuarioRepository usuarioRepository,
                               PacienteRepository pacienteRepository,
                               MedicoRepository medicoRepository){

        Long usuarioFormId = usuarioForm.getId();
        if(usuarioFormId != null && usuarioFormId != 0){
            usuario.setId(usuarioFormId);
        }
        usuario.setEmail(usuarioForm.getEmail());
        String password = usuarioForm.getPassword();

        if(password != null && !password.isBlank()){
            String encodedPassword = passwordEncoder.encode(usuarioForm.getPassword());
            usuario.setPassword(encodedPassword);
        }
        usuario.setRoles(usuarioForm.getRoles());

        em.getTransaction().begin();
        Paciente paciente = null;
        Medico medico = null;
        if(usuario.getId() == 0){
            usuarioRepository.save(usuario);

            Set<Rol> roles = usuario.getRoles();

            if(roles.stream().anyMatch(rol -> rol.getNombre().equals("Cliente"))){
                paciente = crearPaciente(usuarioForm);
            }
            if(roles.stream().anyMatch(rol -> rol.getNombre().equals("Doctor"))){
                medico = crearMedico(usuarioForm);
            }
        }

        if(paciente != null){
            paciente.setUsuarioId(usuario.getId());
            pacienteRepository.save(paciente);

            usuario.setPacienteId(paciente.getId());
        }
        if(medico != null){
            medico.setUsuario(usuario);
            medicoRepository.save(medico);

            usuario.setMedicoId(medico.getId());
        }
        if(paciente != null || medico != null){
            usuarioRepository.save(usuario);
        }

        em.getTransaction().commit();
    }

    private Paciente crearPaciente(UsuarioForm usuarioForm){
        Paciente paciente = new Paciente();
        paciente.setEmail(usuarioForm.getEmail());
        paciente.setNombre(usuarioForm.getNombre());
        paciente.setApellido(usuarioForm.getApellido());
        paciente.setTelefono(usuarioForm.getTelefono());
        paciente.setFechaRegistro(LocalDateTime.now());
        return paciente;
    }

    private Medico crearMedico(UsuarioForm usuarioForm){
        Medico medico = new Medico();
        medico.setEmail(usuarioForm.getEmail());
        medico.setNombre(usuarioForm.getNombre());
        medico.setApellido(usuarioForm.getApellido());
        medico.setTelefono(usuarioForm.getTelefono());
        medico.setFechaCreacion(LocalDateTime.now());
        return medico;
    }

}
