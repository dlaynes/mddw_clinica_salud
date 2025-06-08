ALTER TABLE citas MODIFY COLUMN medico_id BIGINT;
ALTER TABLE citas MODIFY COLUMN estado ENUM('Registrada', 'Rechazada', 'Programada', 'Completada', 'Cancelada', 'En espera') DEFAULT 'Registrada';