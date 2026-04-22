-- ==========================================================
-- Base de datos: cine
-- Proyecto: Reto Transversal 2º DAW
-- Grupo: Manuel, Santi, Felix
-- Descripcion: Aplicacion de reserva de entradas de cine
-- ==========================================================

CREATE DATABASE IF NOT EXISTS cine;
USE cine;

-- ----------------------------------------------------------
-- TABLA: perfiles
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS perfiles (
    id_perfil   INT          PRIMARY KEY,
    nombre      VARCHAR(45)  NOT NULL UNIQUE
);

-- ----------------------------------------------------------
-- TABLA: usuarios
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    username        VARCHAR(45)  PRIMARY KEY,
    password        VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    nombre          VARCHAR(30)  NOT NULL,
    apellidos       VARCHAR(45)  NOT NULL,
    direccion       VARCHAR(100),
    enabled         INT          NOT NULL,
    fecha_registro  DATE         NOT NULL
);

-- ----------------------------------------------------------
-- TABLA: usuario_perfiles
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario_perfiles (
    id_usuario_perfiles  INT  AUTO_INCREMENT PRIMARY KEY,
    username             VARCHAR(45) NOT NULL,
    id_perfil            INT         NOT NULL,
    FOREIGN KEY (username)  REFERENCES usuarios(username),
    FOREIGN KEY (id_perfil) REFERENCES perfiles(id_perfil)
);

-- ----------------------------------------------------------
-- TABLA: tipos
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS tipos (
    id_tipo     INT          AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(45)  NOT NULL,
    descripcion VARCHAR(200)
);

-- ----------------------------------------------------------
-- TABLA: eventos
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS eventos (
    id_evento        INT           AUTO_INCREMENT PRIMARY KEY,
    id_tipo          INT           NOT NULL,
    nombre           VARCHAR(50)   NOT NULL,
    descripcion      VARCHAR(200),
    fecha_inicio     DATE          NOT NULL,
    duracion         INT           NOT NULL,
    direccion        VARCHAR(100)  NOT NULL,
    estado           ENUM('CANCELADO','TERMINADO','ACTIVO') NOT NULL,
    destacado        ENUM('S','N') NOT NULL,
    aforo_maximo     INT           NOT NULL,
    minimo_asistencia INT          NOT NULL,
    precio           DOUBLE        NOT NULL,
    FOREIGN KEY (id_tipo) REFERENCES tipos(id_tipo)
);

-- ----------------------------------------------------------
-- TABLA: reservas
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS reservas (
    id_reserva   INT          AUTO_INCREMENT PRIMARY KEY,
    id_evento    INT          NOT NULL,
    username     VARCHAR(45)  NOT NULL,
    precio_venta DOUBLE       NOT NULL,
    observaciones VARCHAR(200),
    cantidad     INT          NOT NULL,
    FOREIGN KEY (id_evento)  REFERENCES eventos(id_evento),
    FOREIGN KEY (username)   REFERENCES usuarios(username)
);


-- ==========================================================
-- INSERCION DE DATOS
-- ==========================================================

-- ----------------------------------------------------------
-- Perfiles
-- (id_perfil no es AUTO_INCREMENT, hay que indicarlo)
-- ----------------------------------------------------------
INSERT INTO perfiles (id_perfil, nombre) VALUES
(1, 'ROLE_ADMON'),
(2, 'ROLE_CLIENTE');

-- ----------------------------------------------------------
-- Usuarios
-- Contraseña de todos los usuarios: 1234 (cifrada con BCrypt strength=10)
-- ----------------------------------------------------------
INSERT INTO usuarios (username, password, email, nombre, apellidos, direccion, enabled, fecha_registro) VALUES
('manuel',   '$2a$10$EPzX9Kd3ObmYChnivTThturQH.u7eBiVQm.DRlzgUdwVgqshKt9GK', 'manuel@cine.com',   'Manuel', 'Garcia Lopez',    'Calle Mayor 1, Madrid',      1, '2024-09-01'),
('santi',    '$2a$10$6qQ.01ZWiOI2NVNQX4C2h.yfaIALBkbNjERVQg.edYlFDF8PB/SGG', 'santi@cine.com',    'Santi',  'Martinez Ruiz',   'Avenida Central 5, Madrid',  1, '2024-09-01'),
('felix',    '$2a$10$wPr59m/mhdmw4vi0NVPPtOOWuA11Ndub0WPaOhDuuN7O0oNzzk.Hi', 'felix@cine.com',    'Felix',  'Sanchez Torres',  'Plaza Espana 3, Madrid',     1, '2024-09-01'),
('ana_g',    '$2a$10$2sGUFwL.p.zypr3.BxyYf.RIRXYCkxUC6tqy5lwwFxUIqTa3EAydW', 'ana@email.com',     'Ana',    'Gomez Perez',     'Calle Nueva 10, Madrid',     1, '2024-10-15'),
('carlos_l', '$2a$10$vcST2e/QnnT6d5ryO.qXEOgP8ySH4M46HVaUnPN7a1Q4j58DLPRFG', 'carlos@email.com',  'Carlos', 'Lopez Fernandez', 'Calle Vieja 22, Madrid',     1, '2024-11-20'),
('lucia_m',  '$2a$10$YcxpGFehpZ08M7UHEDy90O5gBbeyMT5FEsYqhAAUy6MRX31qOQdO.', 'lucia@email.com',   'Lucia',  'Moreno Diaz',     'Avenida del Sol 7, Madrid',  1, '2025-01-10');

-- ----------------------------------------------------------
-- Asignacion de perfiles a usuarios
-- ----------------------------------------------------------
INSERT INTO usuario_perfiles (username, id_perfil) VALUES
('manuel',   1),
('santi',    1),
('felix',    1),
('ana_g',    2),
('carlos_l', 2),
('lucia_m',  2);

-- ----------------------------------------------------------
-- Tipos de evento (generos de pelicula)
-- ----------------------------------------------------------
INSERT INTO tipos (nombre, descripcion) VALUES
('Fantasia epica',  'Peliculas de fantasia con mundos imaginarios y aventuras epicas'),
('Accion',          'Peliculas con escenas de accion, combates y persecuciones'),
('Aventura',        'Peliculas de exploracion y aventura en mundos desconocidos'),
('Ciencia ficcion', 'Peliculas ambientadas en el futuro o con tecnologia avanzada'),
('Drama',           'Peliculas con historias emotivas y personajes complejos');

-- ----------------------------------------------------------
-- Eventos (peliculas)
-- Saga El Senor de los Anillos y El Hobbit de Peter Jackson
-- ----------------------------------------------------------
INSERT INTO eventos (id_tipo, nombre, descripcion, fecha_inicio, duracion, direccion, estado, destacado, aforo_maximo, minimo_asistencia, precio) VALUES

-- El Senor de los Anillos
(1, 'El Senor de los Anillos: La Comunidad del Anillo',
    'Frodo hereda el Anillo Unico y debe unirse a una compania para destruirlo en las llamas del Monte del Destino.',
    '2025-06-02', 178, 'Cine Gran Via, Sala 1, Madrid', 'ACTIVO', 'S', 200, 20, 8.50),

(1, 'El Senor de los Anillos: Las Dos Torres',
    'La compania se divide. Frodo y Sam se acercan a Mordor guiados por Gollum mientras la guerra arrecia en Rohan.',
    '2025-06-09', 179, 'Cine Gran Via, Sala 1, Madrid', 'ACTIVO', 'S', 200, 20, 8.50),

(1, 'El Senor de los Anillos: El Retorno del Rey',
    'La batalla final por la Tierra Media. Aragorn reclama su trono mientras Frodo llega al borde del abismo.',
    '2025-06-16', 201, 'Cine Gran Via, Sala 1, Madrid', 'ACTIVO', 'S', 200, 20, 9.00),

-- El Hobbit
(1, 'El Hobbit: Un Viaje Inesperado',
    'Bilbo Bolson acepta unirse a Gandalf y trece enanos para recuperar Erebor, la Montana Solitaria, del dragon Smaug.',
    '2025-06-23', 169, 'Cine Gran Via, Sala 2, Madrid', 'ACTIVO', 'N', 150, 15, 7.50),

(1, 'El Hobbit: La Desolacion de Smaug',
    'La compania llega finalmente a la Montana Solitaria y Bilbo se enfrenta al temible dragon Smaug.',
    '2025-06-30', 161, 'Cine Gran Via, Sala 2, Madrid', 'ACTIVO', 'N', 150, 15, 7.50),

(1, 'El Hobbit: La Batalla de los Cinco Ejercitos',
    'La batalla final por la Montana Solitaria enfrenta a enanos, elfos, hombres, orcos y aguilas en un epico conflicto.',
    '2025-07-07', 144, 'Cine Gran Via, Sala 2, Madrid', 'ACTIVO', 'N', 150, 15, 7.50),

-- Otras peliculas
(2, 'Mad Max: Fury Road',
    'En un mundo postapocaliptico, Max se une a Furiosa para huir a traves del desierto de un tirano implacable.',
    '2025-05-20', 120, 'Cine Gran Via, Sala 3, Madrid', 'TERMINADO', 'N', 100, 10, 7.00),

(3, 'Indiana Jones y el Arca Perdida',
    'El arqueologo mas famoso del cine se enfrenta a los nazis para recuperar el Arca de la Alianza antes que ellos.',
    '2025-05-26', 115, 'Cine Gran Via, Sala 3, Madrid', 'TERMINADO', 'N', 100, 10, 6.50),

(4, 'Interstellar',
    'Un grupo de astronautas viaja a traves de un agujero de gusano en busca de un nuevo hogar para la humanidad.',
    '2025-07-14', 169, 'Cine Gran Via, Sala 4, Madrid', 'ACTIVO', 'S', 80, 10, 9.50),

(4, 'Dune: Parte Dos',
    'Paul Atreides se une a los Fremen para vengar a su familia y cumplir su destino en el planeta Arrakis.',
    '2025-07-21', 166, 'Cine Gran Via, Sala 4, Madrid', 'ACTIVO', 'S', 80, 10, 9.50);

-- ----------------------------------------------------------
-- Reservas de ejemplo
-- ----------------------------------------------------------
INSERT INTO reservas (id_evento, username, precio_venta, observaciones, cantidad) VALUES
(1, 'ana_g',    8.50, NULL,                        2),
(2, 'ana_g',    8.50, 'Butacas en el centro',      3),
(3, 'carlos_l', 9.00, NULL,                        1),
(1, 'carlos_l', 8.50, NULL,                        4),
(4, 'lucia_m',  7.50, NULL,                        2),
(9, 'ana_g',    9.50, 'Prefiero asientos delante', 2),
(1, 'lucia_m',  8.50, NULL,                        1);


-- ==========================================================
-- MIGRACION DE CONTRASENAS A BCRYPT
-- Ejecutar este bloque si la base de datos ya existe con
-- contraseñas en texto plano. Contraseña original: 1234
-- ==========================================================
-- UPDATE usuarios SET password = '$2a$10$EPzX9Kd3ObmYChnivTThturQH.u7eBiVQm.DRlzgUdwVgqshKt9GK' WHERE username = 'manuel';
-- UPDATE usuarios SET password = '$2a$10$6qQ.01ZWiOI2NVNQX4C2h.yfaIALBkbNjERVQg.edYlFDF8PB/SGG' WHERE username = 'santi';
-- UPDATE usuarios SET password = '$2a$10$wPr59m/mhdmw4vi0NVPPtOOWuA11Ndub0WPaOhDuuN7O0oNzzk.Hi' WHERE username = 'felix';
-- UPDATE usuarios SET password = '$2a$10$2sGUFwL.p.zypr3.BxyYf.RIRXYCkxUC6tqy5lwwFxUIqTa3EAydW' WHERE username = 'ana_g';
-- UPDATE usuarios SET password = '$2a$10$vcST2e/QnnT6d5ryO.qXEOgP8ySH4M46HVaUnPN7a1Q4j58DLPRFG' WHERE username = 'carlos_l';
-- UPDATE usuarios SET password = '$2a$10$YcxpGFehpZ08M7UHEDy90O5gBbeyMT5FEsYqhAAUy6MRX31qOQdO.' WHERE username = 'lucia_m';
