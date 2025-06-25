ALTER TABLE utp_mddw_citas_medicas.usuarios ADD username varchar(20) NOT NULL;
ALTER TABLE utp_mddw_citas_medicas.usuarios ADD CONSTRAINT usuarios_unique UNIQUE KEY (username);
