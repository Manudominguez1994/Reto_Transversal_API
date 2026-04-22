USE cine;

INSERT INTO eventos (id_tipo, nombre, descripcion, fecha_inicio, duracion, direccion, estado, destacado, aforo_maximo, minimo_asistencia, precio) VALUES
(5, 'Oppenheimer',
    'La historia del fisico que lidero el Proyecto Manhattan y cambio el mundo para siempre con la bomba atomica.',
    '2026-05-05', 180, 'Cine Gran Via, Sala 5, Madrid', 'ACTIVO', 'S', 120, 15, 10.50),

(4, 'Avatar: El Camino del Agua',
    'Jake Sully y Neytiri forman una familia y hacen todo lo posible por permanecer juntos. Pero deben abandonar su hogar.',
    '2026-05-12', 192, 'Cine Gran Via, Sala 5, Madrid', 'ACTIVO', 'S', 200, 20, 11.00),

(2, 'El Caballero Oscuro',
    'Batman se enfrenta al Joker, un criminal que siembra el caos en Ciudad Gotica en una batalla por el alma de la ciudad.',
    '2026-05-19', 152, 'Cine Gran Via, Sala 3, Madrid', 'ACTIVO', 'S', 150, 15, 9.00),

(3, 'Los Vengadores: Endgame',
    'Los Vengadores restantes deben encontrar la manera de recuperar a sus aliados para un enfrentamiento final con Thanos.',
    '2026-05-26', 181, 'Cine Gran Via, Sala 1, Madrid', 'ACTIVO', 'S', 250, 25, 10.00),

(5, 'El Padrino',
    'El patriarca de una familia mafiosa transfiere el control de su imperio clandestino a su renuente hijo.',
    '2026-06-02', 175, 'Cine Gran Via, Sala 2, Madrid', 'ACTIVO', 'N', 100, 10, 8.00),

(4, 'Blade Runner 2049',
    'Un nuevo cazador de replicantes descubre un secreto enterrado durante mucho tiempo que podria sumir a la sociedad en el caos.',
    '2026-06-09', 164, 'Cine Gran Via, Sala 4, Madrid', 'ACTIVO', 'N', 80, 10, 8.50),

(2, 'Top Gun: Maverick',
    'Maverick regresa para entrenar a una nueva generacion de pilotos en la mision mas peligrosa de su carrera.',
    '2026-03-10', 131, 'Cine Gran Via, Sala 3, Madrid', 'TERMINADO', 'N', 150, 15, 8.00),

(3, 'Jurassic Park',
    'Un parque tematico con dinosaurios reales se convierte en una pesadilla cuando los animales escapan de sus recintos.',
    '2026-03-17', 127, 'Cine Gran Via, Sala 2, Madrid', 'TERMINADO', 'N', 180, 20, 7.00),

(2, 'Fast and Furious X',
    'Dom Toretto y su familia son el objetivo del vengativo hijo de Dante Reyes.',
    '2026-04-01', 141, 'Cine Gran Via, Sala 3, Madrid', 'CANCELADO', 'N', 200, 20, 7.50),

(1, 'El Hobbit: Version Extendida (Ciclo Completo)',
    'Sesion especial de las tres peliculas de El Hobbit en version extendida. Cancelada por obras en la sala.',
    '2026-04-15', 540, 'Cine Gran Via, Sala 1, Madrid', 'CANCELADO', 'N', 100, 10, 25.00);


INSERT INTO usuarios (username, password, email, nombre, apellidos, direccion, enabled, fecha_registro) VALUES
('pedro_r', '$2a$10$EPzX9Kd3ObmYChnivTThturQH.u7eBiVQm.DRlzgUdwVgqshKt9GK', 'pedro@email.com', 'Pedro', 'Rodriguez Alonso', 'Calle Luna 15, Madrid', 1, '2025-03-01');

INSERT INTO usuario_perfiles (username, id_perfil) VALUES
('pedro_r', 2);


INSERT INTO reservas (id_evento, username, precio_venta, observaciones, cantidad) VALUES
(11, 'ana_g',    10.50, 'Butacas preferentes',           2),
(12, 'ana_g',    11.00, NULL,                             3),
(13, 'ana_g',     9.00, 'Con mi pareja',                  2),
(14, 'ana_g',    10.00, NULL,                             4),
(17, 'ana_g',     8.00, NULL,                             2),

(11, 'carlos_l', 10.50, NULL,                             1),
(13, 'carlos_l',  9.00, 'Zona central',                   2),
(14, 'carlos_l', 10.00, NULL,                             5),
(15, 'carlos_l',  8.00, NULL,                             2),
(18, 'carlos_l',  7.00, NULL,                             3),

(12, 'lucia_m',  11.00, 'Asientos en el centro',          2),
(13, 'lucia_m',   9.00, NULL,                             1),
(16, 'lucia_m',   8.50, 'Primera fila si es posible',     2),
(17, 'lucia_m',   8.00, NULL,                             2),

(11, 'pedro_r',  10.50, NULL,                             2),
(12, 'pedro_r',  11.00, 'Para el estreno',                3),
(14, 'pedro_r',  10.00, NULL,                             2),
(16, 'pedro_r',   8.50, NULL,                             1);
