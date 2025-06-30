package com.grupo2.clinicasalud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", columnDefinition = "BIGINT")
    private long id;

    @Column(length = 20, nullable = false, unique = true)
    private String nombre;

    public Rol(){

    }

    public Rol(String rol){
        this.nombre = rol;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
