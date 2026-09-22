INSERT INTO "Roles" (nombre) VALUES
  ('Cliente'),
  ('Proveedor de Servicios'),
  ('Administrador')
ON CONFLICT (nombre) DO NOTHING;

INSERT INTO "Estados_Reserva" (id, nombre) VALUES
  (1, 'ACTIVA'),
  (2, 'CANCELADA'),
  (3, 'COMPLETADA')
ON CONFLICT (id) DO NOTHING;
