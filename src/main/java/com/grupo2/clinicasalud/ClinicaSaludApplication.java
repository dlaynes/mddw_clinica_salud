package com.grupo2.clinicasalud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClinicaSaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaSaludApplication.class, args);
	}

}
