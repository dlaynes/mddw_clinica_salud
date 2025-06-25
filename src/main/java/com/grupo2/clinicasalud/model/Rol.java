package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", columnDefinition = "BIGINT")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private TipoRol nombre;

    public Rol(TipoRol rol){
        this.nombre = rol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoRol getNombre() {
        return nombre;
    }

    public void setNombre(TipoRol nombre) {
        this.nombre = nombre;
    }
}
