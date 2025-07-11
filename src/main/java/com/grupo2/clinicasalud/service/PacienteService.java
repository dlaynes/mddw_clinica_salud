package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Usuario;
import com.grupo2.clinicasalud.model.form.ReservaCitaForm;
import com.grupo2.clinicasalud.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository repository;

    @Autowired
    public PacienteService(PacienteRepository repository){
        this.repository = repository;
    }

    public List<Paciente> damePacientes() {
        return repository.findAll();
    }
    public Paciente obtenerPacientePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id));
    }

    public Paciente actualizarPaciente(Long id, Paciente pacienteActualizado){
        Paciente paciente = obtenerPacientePorId(id);
        paciente.setTipoDocumento(pacienteActualizado.getTipoDocumento());
        paciente.setNumeroDocumento(pacienteActualizado.getNumeroDocumento());
        paciente.setNombre(pacienteActualizado.getNombre());
        paciente.setApellido(pacienteActualizado.getApellido());
        paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
        paciente.setTelefono(pacienteActualizado.getTelefono());
        paciente.setEmail(pacienteActualizado.getEmail());
        paciente.setEstadoCivil(pacienteActualizado.getEstadoCivil());
        paciente.setGenero(pacienteActualizado.getGenero());
        return repository.save(paciente);
    }

    public List<Paciente> buscarPacientes(String searchTerm) {
        return repository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrNumeroDocumentoContaining(
                searchTerm, searchTerm, searchTerm);
    }

    public Paciente damePacientePorId(long id){
        return repository.findById(id).orElse(null);
    }

    public Paciente damePacientePorEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }

    public long countPacientes() {
        return repository.count();
    }

    public Paciente guardarPaciente(String nombre, String apellidos, String email, String telefono, Long usuarioId){
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellidos);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setUsuarioId(usuarioId);
        paciente.setFechaRegistro(LocalDateTime.now());
        repository.save(paciente);

        return paciente;
    }

}
