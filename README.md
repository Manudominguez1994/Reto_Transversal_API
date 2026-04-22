# API REST - Cine (Reto Transversal 2º DAW)

API del reto transversal hecha con Spring Boot. Gestiona eventos de cine, reservas y usuarios conectando con MySQL.

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL 8
- Lombok
- JWT (jjwt 0.12.3)
- BCrypt (spring-security-crypto)
- Swagger (SpringDoc)

## Arrancar el proyecto

1. Tener MySQL corriendo en local
2. Ejecutar el script de base de datos: `src/main/resources/bbdd_cine.sql`
3. Arrancar:

```bash
./mvnw spring-boot:run
```

O ejecutando `RetoApplication.java` desde el IDE.

La API queda en `http://localhost:8095/api`

## Base de datos

El script crea la base de datos `cine` con tablas y datos de prueba. Credenciales por defecto `root / 1234`, si son distintas cambiarlas en `application.properties`.

## Swagger

Con el proyecto arrancado:

```
http://localhost:8095/api/swagger-ui/index.html
```

## Colección Postman

Importar `src/main/resources/postman_collection.json`. Hacer primero el login, copiar el token y pegarlo en el header `Authorization: Bearer <token>`.

## Usuarios de prueba

| Username | Password | Rol |
|----------|----------|-----|
| manuel | 1234 | ROLE_ADMON |
| santi | 1234 | ROLE_ADMON |
| ana_g | 1234 | ROLE_CLIENTE |
| carlos_l | 1234 | ROLE_CLIENTE |

## Login y JWT

```
POST /api/auth/login
Body: { "username": "manuel", "password": "1234" }
```

Devuelve un token que va en el header de las siguientes peticiones:

```
Authorization: Bearer <token>
```

Los GET de eventos, tipos y perfiles son públicos. Las operaciones de escritura necesitan ser admin (403 si no). Para reservar basta con estar logado.

## Endpoints principales

**Auth**
- `POST /api/auth/login`

**Eventos**
- `GET /api/eventos/por-estado-activo`
- `GET /api/eventos/destacados`
- `GET /api/eventos/cancelados`
- `GET /api/eventos/terminados`
- `POST /api/eventos/alta-activo` (admin)
- `PUT /api/eventos/editar/{id}` (admin)
- `PUT /api/eventos/cancelar/{id}` (admin)

**Usuarios**
- `POST /api/usuarios/registro`
- `GET /api/usuarios` (admin)

**Reservas**
- `POST /api/reservas/reservar/{idEvento}` (logado)
- `DELETE /api/reservas/cancelar/{idReserva}` (logado)
- `GET /api/reservas/por-usuario/{username}`

## Perfil de producción

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Variables de entorno necesarias: `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Estructura

```
src/main/java/apirest/reto/
├── config/           CORS, JWT y BCrypt
├── model/
│   ├── entity/       Entidades JPA
│   └── dto/          Objetos de respuesta
├── repository/       Repositorios JPA
├── service/          Lógica de negocio
└── restcontroller/   Endpoints REST
```
