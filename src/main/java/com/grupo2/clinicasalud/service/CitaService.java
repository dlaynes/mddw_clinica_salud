package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.EstadoCita;
import com.grupo2.clinicasalud.model.Medico;
import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository repository;

    @Autowired
    public CitaService(CitaRepository repository){
        this.repository = repository;
    }

    public Cita dameCitaPorId(long id){
        return repository.findById(id).orElse(null);
    }

    /**
     * Guarda o actualiza una cita
     * @param cita Entidad Cita a guardar
     */
    public void guardarCita(Cita cita){
        repository.save(cita);
    }

    /**
     * Obtiene todas las citas registradas
     * @return Lista de citas
     */
    public List<Cita> dameCitas(){
        return repository.findAll();
    }

    public void eliminarCita(Cita cita){
        repository.deleteById(cita.getId());
    }


    public List<Cita> dameCitasDePaciente(Paciente paciente){
        return repository.findByPacienteIdOrderByFechaHoraDesc(paciente.getId());
    }

    public List<Cita> dameCitasDeMedico(Medico medico){
        return repository.findByMedicoIdOrderByFechaHoraDesc(medico.getId());
    }

    public long countCitasBetween(LocalDateTime start, LocalDateTime end) {
        return repository.countByFechaHoraBetween(start, end);
    }

    public List<Cita> findCitasBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByFechaHoraBetweenOrderByFechaHoraAsc(start, end);
    }

    public long countCitasByEstado(EstadoCita estadoCita){
        return repository.countByEstadoCita(estadoCita);
    }


}
