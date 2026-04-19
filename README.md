# API REST - Cine (Reto Transversal 2º DAW)

Esta es la API del reto transversal. Está hecha con Spring Boot y conecta con una base de datos MySQL.
Gestiona eventos de cine, reservas de entradas y usuarios.

---

## Tecnologías usadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL 8
- Lombok
- JWT (sin Spring Security)
- Swagger (SpringDoc)

---

## Cómo arrancar el proyecto

1. Tener MySQL corriendo en local
2. Ejecutar el script de la base de datos:

```
src/main/resources/bbdd_cine.sql
```

3. Arrancar con Maven:

```bash
./mvnw spring-boot:run
```

O directamente desde Eclipse/IntelliJ ejecutando `RetoApplication.java`.

La API queda en: `http://localhost:8095/api`

---

## Base de datos

El script crea la base de datos `cine` con todas las tablas y datos de prueba ya metidos.
Las credenciales por defecto son `root / 1234`. Si las tuyas son distintas, cambiar en `application.properties`.

---

## Swagger

Con el proyecto arrancado, entrar aquí para ver todos los endpoints:

```
http://localhost:8095/api/swagger-ui/index.html
```

---

## Colección Postman

Importar el archivo que está en:

```
src/main/resources/postman_collection.json
```

Hay que hacer primero el login, copiar el token que devuelve, y pegarlo como variable `token` en Postman (o en el header de cada petición que lo necesite).

---

## Usuarios de prueba

| Username | Email | Password | Rol |
|----------|-------|----------|-----|
| manuel | manuel@cine.com | 1234 | ROLE_ADMON |
| ana_g | ana@cine.com | 1234 | ROLE_CLIENTE |
| carlos_l | carlos@cine.com | 1234 | ROLE_CLIENTE |

---

## Login y JWT

Para acceder a los endpoints protegidos hay que hacer primero login:

```
POST /api/auth/login
Body: { "email": "manuel@cine.com", "password": "1234" }
```

Devuelve un token que hay que mandar en el header de las siguientes peticiones:

```
Authorization: Bearer <token>
```

Los GET de eventos, tipos y perfiles son públicos (no necesitan token).
Las operaciones de escritura necesitan ser administrador, si no devuelve 403.
Para reservar o cancelar basta con estar logado (cualquier rol).

---

## Endpoints principales

**Auth**
- `POST /api/auth/login` — hace login y devuelve el token

**Eventos**
- `GET /api/eventos` — todos los eventos
- `GET /api/eventos/por-estado-activo` — eventos activos
- `GET /api/eventos/destacados` — eventos destacados
- `GET /api/eventos/cancelados` — eventos cancelados
- `GET /api/eventos/terminados` — eventos terminados
- `POST /api/eventos/alta-activo` — crear evento (admin)
- `PUT /api/eventos/cancelar/{id}` — cancelar evento (admin)

**Usuarios**
- `POST /api/usuarios/registro` — registro público (sin token)
- `GET /api/usuarios` — listar usuarios (admin)

**Reservas**
- `POST /api/reservas/reservar/{idEvento}` — hacer reserva (logado)
- `DELETE /api/reservas/cancelar/{idReserva}` — cancelar reserva (logado)
- `GET /api/reservas/por-usuario/{username}` — reservas de un usuario

---

## Tests

Se han hecho tests unitarios con JUnit y Mockito para probar la lógica de negocio sin necesitar base de datos.

```bash
./mvnw test
```

Los tests están en `src/test/java/apirest/reto/`:
- `ReservaServiceTest` — prueba las validaciones de hacer una reserva
- `EventoServiceTest` — prueba cancelar, dar de alta y la tarea programada

---

## Perfil de producción

Para desplegar en un servidor usar el perfil `prod`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Y definir las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD`.

---

## Estructura

```
src/main/java/apirest/reto/
├── config/           CORS y JWT
├── model/
│   ├── entity/       Entidades de la base de datos
│   └── dto/          Lo que devuelve la API
├── repository/       Acceso a base de datos
├── service/          Lógica de negocio
└── restcontroller/   Endpoints
```
