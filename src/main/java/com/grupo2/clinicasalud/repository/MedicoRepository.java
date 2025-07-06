package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByEmail(String email);

    Optional<Medico> findByEmail(String email);

    Optional<Medico> findByUsuarioId(Long id);

    @Query(value = "SELECT m.* FROM medicos m " +
        "JOIN especialidades_medicos em ON m.medico_id=em.medico_id " +
            "WHERE em.especialidad_id=:especialidadId", nativeQuery = true)
    List<Medico> dameMedicosPorEspecialidad(@Param("especialidadId") Long especialidadId);
}
