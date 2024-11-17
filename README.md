# API del Servidor Central - Simulación de Strava

Este proyecto implementa un servidor central que simula la funcionalidad de una red social deportiva, permitiendo la gestión de usuarios, entrenamientos y retos.

## Tabla de Contenidos

1. [Descripción General](#descripción-general)
2. [Requisitos Previos](#requisitos-previos)
3. [Endpoints del API](#endpoints-del-api)
   - [Control de Usuarios](#control-de-usuarios)
   - [Gestión de Retos](#gestión-de-retos)
   - [Gestión de Entrenamientos](#gestión-de-entrenamientos)


---

## Descripción General

El servidor central ofrece un API RESTful que permite a los usuarios:
- Registrarse, iniciar sesión y cerrar sesión.
- Crear, consultar y aceptar retos deportivos.
- Registrar y consultar entrenamientos.

Este proyecto utiliza **OpenAPI 3.0** para documentar la interfaz del API.

## Requisitos Previos

Antes de empezar, asegúrate de tener lo siguiente instalado:
- **Java 11** o superior
- **Maven** para gestión de dependencias
- Un entorno de desarrollo como **IntelliJ IDEA** o **Eclipse**

## Endpoints del API

### **Control de Usuarios**

| **Método** | **Endpoint**                | **Descripción**                                | **Parámetros Importantes**                                  |
|------------|-----------------------------|------------------------------------------------|------------------------------------------------------------|
| POST       | `/auth/registroUsuario`     | Registra un nuevo usuario.                    | `nombre`, `fechaNacimiento`, `peso`, `altura`, etc.        |
| POST       | `/auth/login`               | Inicia sesión y devuelve un token.            | `email`, `contrasenya`, `tipoLogin`                        |
| POST       | `/auth/logout`              | Cierra la sesión del usuario.                 | `token` (en el cuerpo de la petición)                      |
| GET        | `/auth/usuarios`            | Lista todos los usuarios registrados.         | Ninguno                                                    |

### **Gestión de Retos**

| **Método** | **Endpoint**                        | **Descripción**                                | **Parámetros Importantes**                                  |
|------------|-------------------------------------|------------------------------------------------|------------------------------------------------------------|
| POST       | `/api/reto`                        | Crea un nuevo reto.                           | `nombre`, `deporte`, `objetivoDistancia`, `fechaInicio`, etc. |
| POST       | `/api/retos/{nombreReto}/aceptar`  | Acepta un reto existente.                     | `nombreReto` (path), `token` (en el cuerpo)                |
| GET        | `/api/retos`                       | Lista todos los retos.                        | Ninguno                                                    |
| GET        | `/api/retosActivos`                | Lista los retos activos (no finalizados).     | `fecha`, `deporte` (opcional)                              |
| GET        | `/api/retosAceptados`              | Lista los retos aceptados por el usuario.     | `token`                                                    |

### **Gestión de Entrenamientos**

| **Método** | **Endpoint**                                | **Descripción**                                | **Parámetros Importantes**                                  |
|------------|---------------------------------------------|------------------------------------------------|------------------------------------------------------------|
| POST       | `/api/entrenamiento`                       | Crea un nuevo entrenamiento.                  | `titulo`, `deporte`, `distancia`, `fechaInicio`, `token`, etc. |
| GET        | `/api/entrenamientos/{fechaInicio}/{fechaFin}` | Lista entrenamientos en un rango de fechas.   | `fechaInicio`, `fechaFin`, `token`                         |
