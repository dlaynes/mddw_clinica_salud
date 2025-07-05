package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Consultorio;
import com.grupo2.clinicasalud.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {
    ConsultorioRepository repository;

    @Autowired
    public ConsultorioService(ConsultorioRepository repository){
        this.repository = repository;
    }

    public boolean existeConsultorioPorId(long id) { return repository.existsById(id); }

    public Consultorio dameConsultorioPorId(long id){
        return repository.findById(id).orElse(null);
    }

    public void guardarConsultorio(Consultorio c){
        repository.save(c);
    }

    public List<Consultorio> dameConsultorios(){
        return repository.findAll();
    }

    public void eliminarConsultorio(Consultorio c){
        repository.deleteById(c.getId());
    }

    public long countConsultorios() {
        return repository.count();
    }

}
