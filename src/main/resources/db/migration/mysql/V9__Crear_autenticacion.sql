CREATE TABLE IF NOT EXISTS usuarios (
    usuario_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    rol_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario_roles (
   usuario_id BIGINT NOT NULL,
   rol_id BIGINT NOT NULL,
   FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id),
   FOREIGN KEY (rol_id) REFERENCES roles(rol_id)
);

