package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Servicio;
import com.grupo2.clinicasalud.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Servicios provistos por la clínica

@Service
public class ServicioService {
    private final ServicioRepository repository;

    @Autowired
    public ServicioService(ServicioRepository repository){
        this.repository = repository;
    }

    public Servicio dameServicioPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarServicio(Servicio e){
        repository.save(e);
    }

    public List<Servicio> dameServicios(){
        return repository.findAll();
    }

    public void eliminarServicio(Long id){
        repository.deleteById(id);
    }
}
