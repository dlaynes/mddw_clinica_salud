package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    Optional<Receta> findOneByIdAndHistorialMedicoId(Long id, Long historialMedicoId);

    List<Receta> findByHistorialMedicoId(Long historialMedicoId);
}
