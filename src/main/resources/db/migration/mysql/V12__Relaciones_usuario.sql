ALTER TABLE pacientes ADD COLUMN usuario_id BIGINT NULL;
ALTER TABLE medicos ADD COLUMN usuario_id BIGINT NULL;

ALTER TABLE pacientes ADD FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id);
ALTER TABLE medicos ADD FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id);


