package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
}
