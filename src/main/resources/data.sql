INSERT INTO "Roles" (nombre) VALUES
  ('Cliente'),
  ('Proveedor de Servicios'),
  ('Administrador')
ON CONFLICT (nombre) DO NOTHING;
