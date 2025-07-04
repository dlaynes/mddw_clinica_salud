package com.grupo2.clinicasalud.config.initializers;

import com.grupo2.clinicasalud.model.Consultorio;
import com.grupo2.clinicasalud.repository.ConsultorioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ConsultorioInitializer {
    @Bean
    public CommandLineRunner initConsultorios(ConsultorioRepository consultorioRepository) {
        return args -> {
            if(consultorioRepository.findById(1L).isEmpty()){
                Consultorio consultorio = new Consultorio();
                consultorio.setNombre("Local Principal");
                consultorio.setDescripcion("Local Principal de la Clínica Salud");
                consultorio.setUbicacion("Santa Beatriz, Lima");
                consultorio.setLatitud(BigDecimal.valueOf(-12.0639163));
                consultorio.setLongitud(BigDecimal.valueOf(-77.035823));
                consultorioRepository.save(consultorio);
            }
        };
    }
}
