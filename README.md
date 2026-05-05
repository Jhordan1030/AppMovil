# AppHotel 🏨

Aplicación móvil Android para gestión hotelera desarrollada con Kotlin y Material Design 3.

## Funcionalidades
- **Reservas**: CRUD completo con detalle maestro-detalle
- **Huéspedes**: Gestión de información de clientes
- **Habitaciones**: Control de disponibilidad y estados

## Tecnologías
- Kotlin + Android SDK
- Material Design 3
- Retrofit + OkHttp
- Navigation Component
- MVVM Architecture

## URL de la API

La aplicación consume datos de la siguiente API REST desplegada en Vercel:

🔗 **URL Base:** [https://hotel-api-silk.vercel.app/](https://hotel-api-silk.vercel.app/)

### Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/huespedes` | Obtener todos los huéspedes |
| GET | `/api/habitaciones` | Obtener todas las habitaciones |
| GET | `/api/habitaciones/disponibles` | Obtener habitaciones disponibles |
| GET | `/api/reservas` | Obtener todas las reservas |
| GET | `/api/reservas/:id` | Obtener detalle de una reserva |

<!-- Imagen de la API -->

---

## Capturas de Pantalla

### 📋 Pantalla de Reservas
Vista principal que muestra el listado de todas las reservas realizadas, con información resumida como fecha, huésped y estado de cada reserva.

<!-- Imagen aquí -->

---

### 👤 Pantalla de Huéspedes
Listado completo de los huéspedes registrados en el sistema, mostrando nombre, cédula, correo electrónico y teléfono de contacto.

<!-- Imagen aquí -->

---

### 🛏️ Pantalla de Habitaciones
Vista que presenta todas las habitaciones del hotel con su número, tipo, precio por noche y estado de disponibilidad actual.

<!-- Imagen aquí -->

---

### ➕ Crear Habitación
Formulario para registrar una nueva habitación en el sistema, permitiendo ingresar número de habitación, tipo, precio y estado inicial.

<!-- Imagen aquí -->

---

### ➕ Crear Huésped
Formulario de registro de un nuevo huésped con campos para cédula, nombre, apellido, correo electrónico, teléfono, dirección y nacionalidad.

<!-- Imagen aquí -->

---

### ➕ Crear Reserva
Formulario para generar una nueva reserva seleccionando el huésped, las habitaciones disponibles, fechas de entrada y salida, y observaciones adicionales.

<!-- Imagen aquí -->

---

### 🔍 Ver Detalles de la Reserva
Vista detallada de una reserva específica mostrando la información completa del huésped, las habitaciones asignadas, fechas y el monto total de la estadía.

<!-- Imagen aquí -->
