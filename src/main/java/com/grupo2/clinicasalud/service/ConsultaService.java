package com.grupo2.clinicasalud.service;

import com.grupo2.clinicasalud.model.Consulta;

import com.grupo2.clinicasalud.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

        private final ConsultaRepository repository;

        @Autowired
        public ConsultaService(ConsultaRepository repository){
            this.repository = repository;
        }

        public Consulta dameConsultaPorId(long id){
            return repository.findById(id).orElse(null);
        }

        public void guardarConsulta(Consulta e){
            repository.save(e);
        }

        public List<Consulta> dameConsultas(){
            return repository.findAll();
        }

        public void eliminarConsulta(Long id){
            repository.deleteById(id);
        }
}
