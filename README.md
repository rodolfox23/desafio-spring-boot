# bci-desafio-java

# API de Gestión de Tareas - Banco BCI

API REST para la gestión de usuarios y tareas, desarrollada con Spring Boot.

## Características Principales

### Autenticación y Usuarios
- Registro de nuevos usuarios (Sign-up)
- Inicio de sesión (Login) con JWT
- Gestión de perfiles de usuario
- Validación de email y contraseña

### Gestión de Tareas
- Creación de tareas
- Actualización de tareas
- Eliminación de tareas
- Consulta de tareas
- Asignación de tareas a usuarios

### Estados de Tareas
- Estados predefinidos: Nueva, Pendiente, En Progreso, Completada
- Posibilidad de crear nuevos estados
- Gestión de estados de tareas

## Tecnologías Utilizadas
- Spring Boot 3.2.5
- Spring Security
- JWT (JSON Web Tokens)
- H2 Database
- Gradle
- Java 17
- Spring Data JPA
- Springdoc OpenAPI (Swagger)

## Requisitos Previos
- Java 17
- Gradle
- Maven

## Configuración

### Variables de Entorno
La aplicación utiliza las siguientes configuraciones por defecto:
- Puerto: 8080
- Base de datos: H2 (en memoria)
- Regex de contraseña: `^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$`

### Script de Base de Datos
El script de creación de la base de datos se encuentra en `src/main/resources/schema.sql`. Este script contiene las definiciones de todas las tablas (usuario, phone, token, estado_tarea, tarea). Con H2 en memoria, el script se ejecuta automáticamente al iniciar la aplicación. Si necesitas usar otra base de datos, puedes ejecutar este script manualmente.

### Usuarios Iniciales
La aplicación crea automáticamente dos usuarios de prueba:
1. Juan Perez (juan.perez@example.com)
2. Maria Perez (maria.perez@example.com)

## Documentación de la API

### Autenticación
Todos los endpoints (excepto login y registro) requieren autenticación mediante JWT.
El token debe incluirse en el header de la siguiente forma:
```
Authorization: Bearer <token>
```

### Endpoints Principales

#### Autenticación
- **Registro (Sign-up)**
  ```
  POST /auth/sign-up
  ```
  ```json
  {
    "nombre": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "Password123",
    "phones": [
      {
        "number": "1234567",
        "citycode": "1",
        "contrycode": "57"
      }
    ]
  }
  ```

- **Login**
  ```
  POST /auth/login
  ```
  ```json
  {
    "email": "usuario@example.com",
    "password": "Password123"
  }
  ```

#### Usuarios
- **Obtener Usuarios**
  ```
  GET /api/usuarios
  GET /api/usuarios/{id}
  ```

- **Actualizar Usuario**
  ```
  PUT /api/usuarios/{id}
  PATCH /api/usuarios/{id}
  ```

- **Eliminar Usuario**
  ```
  DELETE /api/usuarios/{id}
  ```

#### Tareas
- **Crear Tarea**
  ```
  POST /api/tareas
  ```
  ```json
  {
    "titulo": "Título de la tarea",
    "descripcion": "Descripción de la tarea",
    "userId": 1
  }
  ```

- **Obtener Tareas**
  ```
  GET /api/tareas
  GET /api/tareas/{id}
  ```

- **Actualizar Tarea**
  ```
  PUT /api/tareas/{id}
  ```

- **Eliminar Tarea**
  ```
  DELETE /api/tareas/{id}
  ```

#### Estados
- **Obtener Estados**
  ```
  GET /api/estados
  GET /api/estados/{id}
  ```

- **Crear Estado**
  ```
  POST /api/estados
  ```
  ```json
  {
    "estado": "Nuevo Estado"
  }
  ```

- **Actualizar Estado**
  ```
  PUT /api/estados/{id}
  ```

- **Eliminar Estado**
  ```
  DELETE /api/estados/{id}
  ```

## Documentación Swagger
La documentación completa de la API está disponible en:
```
http://localhost:8080/swagger-ui/index.html
```

## Ejecución
1. Clonar el repositorio
2. Ejecutar `./gradlew bootRun`
3. La aplicación estará disponible en `http://localhost:8080`

## Cómo Probar

### Usando los Usuarios Iniciales
La aplicación crea automáticamente dos usuarios que puedes usar para probar:
- Email: `juan.perez@example.com` / Password: `Password123`
- Email: `maria.perez@example.com` / Password: `Password123`

### Ejemplo de Flujo
1. **Login con usuario existente:**
   ```
   POST http://localhost:8080/auth/login
   ```
   ```json
   {
     "email": "juan.perez@example.com",
     "password": "Password123"
   }
   ```
   La respuesta incluirá un `token` que debes usar en los siguientes requests.

2. **Obtener usuarios (requiere token):**
   ```
   GET http://localhost:8080/api/usuarios
   Headers: Authorization: Bearer <token>
   ```

3. **Crear un nuevo usuario:**
   ```
   POST http://localhost:8080/auth/sign-up
   ```
   ```json
   {
     "nombre": "Test User",
     "email": "test@example.com",
     "password": "Password123",
     "phones": [
       {
         "number": "1234567",
         "citycode": "1",
         "contrycode": "57"
       }
     ]
   }
   ```

### Otras Formas de Probar
- **Swagger UI:** Accede a `http://localhost:8080/swagger-ui/index.html` para probar los endpoints desde el navegador
- **Postman:** Importa la colección `desafio_bci.postman_collection.json` incluida en el proyecto