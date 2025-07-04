package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Cita;
import com.grupo2.clinicasalud.model.EstadoCita;
import com.grupo2.clinicasalud.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedicoIdOrderByFechaHoraDesc(Long medicoId);

    List<Cita> findByPacienteIdOrderByFechaHoraDesc(Long pacienteId);

    long countByFechaHoraBetween(LocalDateTime start, LocalDateTime end);

    List<Cita> findByFechaHoraBetweenOrderByFechaHoraAsc(LocalDateTime start, LocalDateTime end);

    long countByEstadoCita(EstadoCita estado);

    List<Cita> findByEstadoCitaOrderByFechaHoraDesc(EstadoCita estado);

    long countByPacienteId(Long pacienteId);

    long countByMedicoId(Long medicoId);


    /*
    @Query("SELECT c FROM Cita c WHERE " +
            "LOWER(c.paciente.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.paciente.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.medico.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.medico.apellidos) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.medico.especialidad.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Cita> buscarCitas(@Param("searchTerm") String searchTerm);
    */

}
