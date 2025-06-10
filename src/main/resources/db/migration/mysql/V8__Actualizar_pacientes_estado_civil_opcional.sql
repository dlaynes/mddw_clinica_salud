ALTER TABLE medicos
    MODIFY COLUMN tipo_documento ENUM('D','P','E','') DEFAULT '',
    MODIFY COLUMN genero ENUM('M','F','') DEFAULT '',
    MODIFY COLUMN estado_civil ENUM('S','C','V','D','') DEFAULT '';

ALTER TABLE pacientes
    MODIFY COLUMN tipo_documento ENUM('D','P','E','') DEFAULT '',
    MODIFY COLUMN genero ENUM('M','F','') DEFAULT '',
    MODIFY COLUMN estado_civil ENUM('S','C','V','D','') DEFAULT '';