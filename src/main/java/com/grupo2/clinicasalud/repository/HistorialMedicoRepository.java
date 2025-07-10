package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {

    public List<HistorialMedico> findByMedicoIdOrderByFechaHoraDesc(Long medicoId);

    public List<HistorialMedico> findByPacienteIdIdOrderByFechaHoraDesc(Long pacienteId);

    public Optional<HistorialMedico> findOneByIdAndMedicoId(Long id, Long medicoId);

    public Optional<HistorialMedico> findOneByIdAndPacienteId(Long id, Long pacienteId);
}
