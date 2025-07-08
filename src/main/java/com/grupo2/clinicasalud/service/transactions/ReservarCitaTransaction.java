package com.grupo2.clinicasalud.service.transactions;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.ReservaCitaForm;
import com.grupo2.clinicasalud.repository.*;
import com.grupo2.clinicasalud.security.utils.PasswordGen;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservarCitaTransaction {

    public void guardarCita(
            ReservaCitaForm reservaCitaForm,
            Especialidad especialidad,
            CitaRepository citaRepository,
            PacienteRepository pacienteRepository,
            UsuarioRepository usuarioRepository,
            RolRepository roleRepository,
            ConsultorioRepository consultorioRepository,
            String password,
            PasswordEncoder passwordEncoder
            ){

        Optional<Consultorio> consultorioOptional = consultorioRepository.findById(1L);
        if(consultorioOptional.isEmpty()){
            throw new RuntimeException("Error: No se encontró un consultorio.");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(reservaCitaForm.getEmail());
        String encodedPassword = passwordEncoder.encode(password);

        // TO DO: enviar un correo con la información. Luego pedirle al usuario que cambie su contraseña
        usuario.setPassword(encodedPassword);

        Set<Rol> roles = new HashSet<>();
        Rol clienteRol = roleRepository.findByNombre("Cliente")
                .orElseThrow(() -> new RuntimeException("Error: Rol Cliente no encontrado."));
        roles.add(clienteRol);
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);

        // TO DO: enviar un correo con la información. Luego pedirle al usuario que cambie su contraseña

        Paciente paciente = new Paciente();
        paciente.setNombre(reservaCitaForm.getNombre());
        paciente.setApellido(reservaCitaForm.getApellidos());
        paciente.setEmail(reservaCitaForm.getEmail());
        paciente.setTelefono(reservaCitaForm.getTelefono());
        paciente.setUsuario(usuario);
        paciente.setFechaRegistro(new Date());
        pacienteRepository.save(paciente);

        Cita cita = new Cita();
        cita.setEstadoCita(EstadoCita.registrada);
        cita.setEspecialidad(especialidad);
        cita.setMotivo(reservaCitaForm.getMotivo());
        LocalDate localDate = LocalDate.parse(reservaCitaForm.getFecha());
        LocalTime localTime = LocalTime.parse(reservaCitaForm.getHora());
        Instant instant = localTime.atDate(localDate)
                .atZone(ZoneId.systemDefault()).toInstant();
        cita.setFechaHora(Date.from(instant));
        cita.setPaciente(paciente);
        cita.setConsultorio(consultorioOptional.get());
        cita.setFechaRegistro(new Date());
        citaRepository.save(cita);
    }
}
