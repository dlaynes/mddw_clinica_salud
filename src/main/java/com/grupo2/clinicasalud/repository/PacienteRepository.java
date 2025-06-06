package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
