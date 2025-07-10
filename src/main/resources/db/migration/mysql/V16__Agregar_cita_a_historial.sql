ALTER TABLE historial_medico ADD COLUMN cita_id BIGINT NULL;
ALTER TABLE historial_medico ADD FOREIGN KEY (cita_id) REFERENCES citas(cita_id);

ALTER TABLE citas ADD COLUMN historial_id BIGINT NULL;
ALTER TABLE citas ADD FOREIGN KEY (historial_id) REFERENCES historial_medico(historial_id);