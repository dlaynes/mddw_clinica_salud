package com.grupo2.clinicasalud.service.transactions;

import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Rol;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.model.form.admin.UsuarioForm;
import com.grupo2.clinicasalud.repository.MedicoRepository;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import com.grupo2.clinicasalud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioTransaction {

    public void guardarUsuario(UsuarioForm usuarioForm,
                               PasswordEncoder passwordEncoder,
                               UsuarioRepository usuarioRepository,
                               PacienteRepository pacienteRepository,
                               MedicoRepository medicoRepository){

        Long usuarioFormId = usuarioForm.getId();
        Usuario usuario = new Usuario();
        if(usuarioFormId != null){
            usuario.setId(usuarioFormId);
        }
        usuario.setEmail(usuarioForm.getEmail());
        String password = usuarioForm.getPassword();

        if(password != null && !password.isBlank()){
            String encodedPassword = passwordEncoder.encode(usuarioForm.getPassword());
            usuario.setPassword(encodedPassword);
        }
        usuario.setRoles(usuarioForm.getRoles());

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
        } else {
            usuarioRepository.save(usuario);
            Set<Rol> roles = usuario.getRoles();

            // Creamos las cuentas si no existen. De otra manera, las dejamos en el sistema
            if(roles.stream().anyMatch(rol -> rol.getNombre().equals("Doctor"))){
                Optional<Medico> medicoOpt = medicoRepository.findByUsuarioId(usuario.getId());
                if(medicoOpt.isEmpty()){
                    medico = crearMedico(usuarioForm);
                }
            }
            if(roles.stream().anyMatch(rol -> rol.getNombre().equals("Cliente"))){
                Optional<Paciente> pacienteOpt = pacienteRepository.findByUsuarioId(usuario.getId());
                if(pacienteOpt.isEmpty()){
                    paciente = crearPaciente(usuarioForm);
                }
            }
        }
        if(paciente != null){
            paciente.setUsuario(usuario);
            pacienteRepository.save(paciente);

            usuario.setPaciente(paciente);
        }
        if(medico != null){
            medico.setUsuario(usuario);
            medicoRepository.save(medico);

            usuario.setMedico(medico);
        }
        if(paciente != null || medico != null){
            usuarioRepository.save(usuario);
        }
    }

    private Paciente crearPaciente(UsuarioForm usuarioForm){
        Paciente paciente = new Paciente();
        paciente.setEmail(usuarioForm.getEmail());
        paciente.setNombre(usuarioForm.getNombre());
        paciente.setApellido(usuarioForm.getApellido());
        paciente.setTelefono(usuarioForm.getTelefono());
        paciente.setFechaRegistro(new Date());
        return paciente;
    }

    private Medico crearMedico(UsuarioForm usuarioForm){
        Medico medico = new Medico();
        medico.setEmail(usuarioForm.getEmail());
        medico.setNombre(usuarioForm.getNombre());
        medico.setApellido(usuarioForm.getApellido());
        medico.setTelefono(usuarioForm.getTelefono());
        medico.setFechaCreacion(new Date());
        return medico;
    }

}
