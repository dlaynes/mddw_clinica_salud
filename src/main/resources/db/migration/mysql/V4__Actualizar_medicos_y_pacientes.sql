ALTER TABLE medicos DROP COLUMN dni,
    ADD COLUMN tipo_documento ENUM('D','P','E',''),
    ADD COLUMN numero_documento VARCHAR(18),
    ADD COLUMN genero ENUM('M','F',''),
    ADD COLUMN estado_civil ENUM('S','C','V','D','');

ALTER TABLE pacientes DROP COLUMN dni,
    ADD COLUMN tipo_documento ENUM('D','P','E',''),
    ADD COLUMN numero_documento VARCHAR(18),
    ADD COLUMN estado_civil ENUM('S','C','V','D','');