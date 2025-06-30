package com.grupo2.clinicasalud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo2.clinicasalud.model.TipoRol;
import com.grupo2.clinicasalud.model.Rol;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String rol);
}
