CREATE TABLE consultas (
    consulta_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    comentario TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);