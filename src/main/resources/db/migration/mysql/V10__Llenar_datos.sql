INSERT INTO roles (rol_id, nombre) VALUES
  (1, "Visitante"),
  (2, "Cliente"),
  (3, "Doctor"),
  (4, "Mantenimiento"),
  (5, "Admin");

INSERT INTO servicios (servicio_id, slug, nombre, descripcion, fecha_creacion) VALUES
  (1, "consulta-medica", "Consulta Médica", "Atención personalizada para diagnosticar y tratar tus necesidades de salud", "2025-05-01 12:00:01"),
  (2, "laboratorio-clinico", "Laboratorio Clínico", "Análisis clínicos precisos para un diagnóstico confiable", "2025-05-01 12:00:01"),
  (3, "radiologia", "Radiología", "Tecnología avanzada para estudios de imagen y diagnóstico", "2025-05-01 12:00:01");

INSERT INTO especialidades (especialidad_id, nombre, descripcion, fecha_creacion, color) VALUES
  (1, "Medicina General", "Atención primaria integral", "2025-05-01 12:00:01", "primary"),
  (2, "Cirugía", "Procedimientos quirúrgicos", "2025-05-01 12:00:01", "danger"),
  (3, "Pediatría", "Atención infantil", "2025-05-01 12:00:01", "success"),
  (4, "Ginecología", "Salud femenina", "2025-05-01 12:00:01", "warning"),
  (5, "Cardiología", "Sistema cardiovascular", "2025-05-01 12:00:01", "info"),
  (6, "Neurología", "Sistema nervioso", "2025-05-01 12:00:01", "secondary"),
  (7, "Odontología", "Salud bucal", "2025-05-01 12:00:01", "dark"),
  (8, "Psiquiatría", "Salud mental", "2025-05-01 12:00:01", "success");
