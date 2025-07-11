package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.*;
import com.grupo2.clinicasalud.model.form.ReservaCitaForm;
import com.grupo2.clinicasalud.repository.CitaRepository;
import com.grupo2.clinicasalud.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public long countCitasDeDoctorBetween(long medicoId, LocalDateTime start, LocalDateTime end){
        return repository.countByMedicoIdAndFechaHoraBetween(medicoId, start, end);
    }

    public List<Cita> findCitasBetween(LocalDateTime start, LocalDateTime end) {
        return repository.findByFechaHoraBetweenOrderByFechaHoraAsc(start, end);
    }

    public long countCitasByEstado(EstadoCita estadoCita){
        return repository.countByEstadoCita(estadoCita);
    }

    public long countCitasDeDoctorByEstado(long medicoId, EstadoCita estadoCita){
        return repository.countByMedicoIdAndEstadoCita(medicoId, estadoCita);
    }

    public long contarPacientesDeMedico(long medicoId){
        return repository.contarPacientesDeMedico(medicoId);
    }

    public Cita guardarCitaDesdeForm(ReservaCitaForm reserva, Paciente paciente, Especialidad especialidad, Consultorio consultorio){
        Cita cita = new Cita();
        cita.setEstadoCita(EstadoCita.registrada);
        cita.setEspecialidad(especialidad);
        cita.setMotivo(reserva.getMotivo());
        LocalDate localDate = LocalDate.parse(reserva.getFecha());
        LocalTime localTime = LocalTime.parse(reserva.getHora());
        cita.setFechaHora(LocalDateTime.of(localDate, localTime));
        cita.setPaciente(paciente);
        cita.setConsultorio(consultorio);
        cita.setFechaRegistro(LocalDateTime.now());
        repository.save(cita);
        return cita;
    }

}
