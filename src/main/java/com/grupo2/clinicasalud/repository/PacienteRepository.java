package com.grupo2.clinicasalud.repository;

import com.grupo2.clinicasalud.model.Paciente;
import com.grupo2.clinicasalud.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByEmail(String email);

    Optional<Paciente> findByEmail(String email);

    Optional<Paciente> findByUsuarioId(Long usuarioId);

    @Query("SELECT p FROM Paciente p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.apellido) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "p.numeroDocumento LIKE CONCAT('%', :searchTerm, '%')")
    List<Paciente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrNumeroDocumentoContaining(
            @Param("searchTerm") String searchTerm1,
            @Param("searchTerm") String searchTerm2,
            @Param("searchTerm") String searchTerm3);

    long count();

}
