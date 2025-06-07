ALTER TABLE historial_medico ADD COLUMN servicio_id BIGINT NULL;
ALTER TABLE historial_medico ADD FOREIGN KEY (servicio_id) REFERENCES servicios(servicio_id);