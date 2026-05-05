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
<img width="600" height="350" alt="Captura de pantalla 2026-05-04 184301" src="https://github.com/user-attachments/assets/143e0214-f6c3-4150-bdbd-eec95c5d97c4" />
<img width="600" height="350" alt="Captura de pantalla 2026-05-04 184331" src="https://github.com/user-attachments/assets/574a1cd2-d4e1-4ee0-bf73-601c07513d1d" />

---

## Capturas de Pantalla

### 📋 Pantalla de Reservas
Vista principal que muestra el listado de todas las reservas realizadas, con información resumida como fecha, huésped y estado de cada reserva.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/af081faf-a9aa-42c5-81ec-864e509fccd3" />
---

### 👤 Pantalla de Huéspedes
Listado completo de los huéspedes registrados en el sistema, mostrando nombre, cédula, correo electrónico y teléfono de contacto.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/bada0d95-bfb5-46d3-a591-f645b642b476" />

---

### 🛏️ Pantalla de Habitaciones
Vista que presenta todas las habitaciones del hotel con su número, tipo, precio por noche y estado de disponibilidad actual.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/8592ca71-96f0-425d-93ad-f00402fbfc66" />

---

### ➕ Crear Habitación
Formulario para registrar una nueva habitación en el sistema, permitiendo ingresar número de habitación, tipo, precio y estado inicial.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/baccf4e1-d159-416f-972f-2c3c9c22cbbf" />

---

### ➕ Crear Huésped
Formulario de registro de un nuevo huésped con campos para cédula, nombre, apellido, correo electrónico, teléfono, dirección y nacionalidad.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/5d15e37b-23a6-4649-9c46-66c03446e49e" />
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/781932ac-b78c-4fea-a94b-34c60a6592d0" />

---

### ➕ Crear Reserva
Formulario para generar una nueva reserva seleccionando el huésped, las habitaciones disponibles, fechas de entrada y salida, y observaciones adicionales.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/ae8c1ae5-ac93-4ec4-a002-c5605139d809" />
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/bfa70109-3f37-4df6-94db-75fe8c275e3f" />

---

### 🔍 Ver Detalles de la Reserva
Vista detallada de una reserva específica mostrando la información completa del huésped, las habitaciones asignadas, fechas y el monto total de la estadía.

<!-- Imagen aquí -->
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/429c1b19-5ab1-424b-955d-12f3f55f8f8d" />
<img width="250" height="500" alt="image" src="https://github.com/user-attachments/assets/3f64c3d4-c9c4-48d3-95fa-398e2110edd0" />

