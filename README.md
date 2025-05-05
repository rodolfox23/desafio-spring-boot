# API de Gestión de Tareas - Previred

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
  POST /api/auth/signup
  ```
  ```json
  {
    "nombre": "Nombre Usuario",
    "email": "usuario@example.com",
    "password": "Password123"
  }
  ```

- **Login**
  ```
  POST /api/auth/login
  ```
  ```json
  {
    "email": "usuario@example.com",
    "password": "Password123"
  }
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

## Documentación Swagger
La documentación completa de la API está disponible en:
```
http://localhost:8080/swagger-ui/index.html
```

## Ejecución
1. Clonar el repositorio
2. Ejecutar `./gradlew bootRun`
3. La aplicación estará disponible en `http://localhost:8080`

## Contribución
1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request