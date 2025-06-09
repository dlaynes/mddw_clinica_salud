package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByEmail(String email);

    Optional<Medico> findByEmail(String email);
}
