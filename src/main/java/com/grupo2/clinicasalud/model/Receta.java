package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "recetas")

public class Receta {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="receta_id", columnDefinition = "BIGINT")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "historial_id")
    HistorialMedico historialMedico;

    @NotBlank(message = "El medicamento es obligatorio")
    @Size(min = 2, max = 150, message = "El medicamento debe tener entre 2 y 150 caracteres")
    @Column(name = "medicamento", length = 150)
    private String medicamento;

    @NotBlank(message = "La dosis es obligatoria")
    @Size(min = 2, max = 150, message = "La dosis debe tener entre 2 y 150 caracteres")
    @Column(name = "dosis", length = 150)
    private String dosis;

    @NotBlank(message = "La duración es obligatoria")
    @Size(min = 2, max = 100, message = "La duración debe tener entre 2 y 100 caracteres")
    @Column(name = "duracion", length = 100)
    private String duracion;

    @Size(min = 5, max = 2000, message = "Las instrucciones deben tener entre 2 y 2000 caracteres")
    @Column(name = "instrucciones", columnDefinition = "TEXT")
    private String instrucciones;

    public Receta(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }
}
