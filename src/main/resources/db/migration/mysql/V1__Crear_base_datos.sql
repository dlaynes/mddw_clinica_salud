CREATE TABLE pacientes (
    paciente_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero ENUM('M', 'F', ''),
    direccion VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE servicios (
    servicio_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE especialidades (
    especialidad_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    color VARCHAR(10) NOT NULL,
    descripcion TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE medicos (
    medico_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE especialidades_medicos (
   especialidad_id BIGINT NOT NULL,
   medico_id BIGINT NOT NULL,
   FOREIGN KEY (especialidad_id) REFERENCES Especialidades(especialidad_id),
   FOREIGN KEY (medico_id) REFERENCES Medicos(medico_id)
);


CREATE TABLE consultorios (
    consultorio_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE citas (
    cita_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    especialidad_id BIGINT NOT NULL,
    consultorio_id BIGINT,
    fecha_hora DATETIME NOT NULL,
    estado ENUM('Programada', 'Completada', 'Cancelada', 'En espera') DEFAULT 'Programada',
    motivo TEXT,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(paciente_id),
    FOREIGN KEY (medico_id) REFERENCES medicos(medico_id),
    FOREIGN KEY (especialidad_id) REFERENCES especialidades(especialidad_id),
    FOREIGN KEY (consultorio_id) REFERENCES consultorios(consultorio_id)
);

CREATE TABLE historial_medico (
    historial_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    especialidad_id BIGINT NOT NULL,
    fecha_consulta DATETIME DEFAULT CURRENT_TIMESTAMP,
    diagnostico TEXT NOT NULL,
    tratamiento TEXT,
    notas TEXT,
    FOREIGN KEY (paciente_id) REFERENCES Pacientes(paciente_id),
    FOREIGN KEY (especialidad_id) REFERENCES Especialidades(especialidad_id),
    FOREIGN KEY (medico_id) REFERENCES Medicos(medico_id)
);

CREATE TABLE recetas (
    receta_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    historial_id BIGINT NOT NULL,
    medicamento VARCHAR(100) NOT NULL,
    dosis VARCHAR(100),
    duracion VARCHAR(100),
    instrucciones TEXT,
    FOREIGN KEY (historial_id) REFERENCES historial_medico(historial_id)
);