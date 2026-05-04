# StayHub - Accommodation 

## 📍 Estado del proyecto

✅ **Finalizado**

Este repositorio contiene el microservicio central de **Alojamientos**. Este servicio se encarga de proteger la lógica de dominio, orquestar las búsquedas complejas de disponibilidad y aplicar de forma estricta reglas de negocio según roles de usuario.

---

## 🎯 Hitos del microservicio

### Motor de búsquedas avanzado y dinámico
Se ha desarrollado un pipeline de búsqueda de dos fases altamente optimizado:
* **Búsqueda general de alojamientos:** El cliente filtra por ciudad, capacidad, número de habitaciones y rango de fechas. El sistema calcula en tiempo real si el alojamiento tiene disponibilidad suficiente y retorna las opciones con el cálculo de *Precio mínimo* y *Precio máximo* según las habitaciones aptas.
* **Búsqueda específica de habitaciones:** Al seleccionar un alojamiento, se retorna el detalle de las habitaciones disponibles para ese rango de fechas, calculando el **Precio total** de la estancia.
* **Historial Persistente:** Se ha implementado `LastSearchEntity` para almacenar automáticamente la última búsqueda de cada usuario, mejorando la experiencia de usuario y la analítica de negocio.

### Gestión dinámica del ciclo de vida y trazabilidad
Implementación de lógica de negocio estricta para la consistencia de los datos:
* **Estados complejos:** Habitaciones y alojamientos manejan ciclos de vida independientes. El estado de un alojamiento se calcula dinámicamente basándose en sus habitaciones (ej: Si todas las habitaciones cambian a mantenimiento, el alojamiento pasa directamente a no estar disponible).
* **Borrado seguro:** Un alojamiento nunca puede ser eliminado si contiene habitaciones asociadas. En producción, en lugar de borrar entidades, el sistema permite crear "Bloqueos" (mantenimiento, temporada, personal, otros) para preservar la trazabilidad de las reservas pasadas.

### Creación en fases
Para simular un "Onboarding" de plataformas reales, un alojamiento se crea de inicio en estado `{Draft}`. Permanecerá inactivo y oculto de las búsquedas hasta que el propietario complete obligatoriamente:
* Información básica y validación geográfica.
* Establecimiento de restricciones de noches mínimas y máximas.
* Meses de disponibilidad en el calendario.
* Mínimo una imagen por habitación y alojamiento.

### Seguridad
Se ha implementado `Spring Security` y un filtro JWT personalizado (`JwtFilter`, `JwtUtil`) para un control de acceso estricto:
* **Endpoints de búsqueda:** Accesibles para cualquier usuario registrado (`User`, `Owner`).
* **Endpoints de creación:** Exclusivos de `Owner`.
* **Validación de Tenancy:** Un propietario solo puede modificar alojamientos y habitaciones que él mismo ha creado, interceptando las peticiones de otro propietario que no sea dicho creador a nivel de UUID.

### Orquestación distribuida (gRPC)
Este microservicio permite orquestar operaciones de negocio consultando a otros nodos mediante gRPC:
* **Con StayHub-Country:** Durante la creación de un alojamiento, valida en tiempo real que el país y la ciudad enviados existen en la base de datos disponibles.
* **Con StayHub-Reservation:** Consulta en tiempo real si existen bloqueos o reservas confirmadas que se solapen antes de devolver resultados en las búsquedas, asegurando la "verdad absoluta" de la ocupación.
  
---

## 🛠️ Tecnologías utilizadas
* **Lenguaje y Framework:** Java 17, Spring Boot
* **Seguridad y Acceso:** Spring Security, JWT (JSON Web Tokens)
* **Persistencia y ORM:** JPA, Hibernate, PostgreSQL (Producción), H2 (Desarrollo y Testing)
* **Arquitectura y Comunicación:** Patrón Microservicios, gRPC, API REST, JSON
* **Manejo de Errores:** Interceptores y Excepciones personalizadas centralizadas en `@ControllerAdvice`.
* **DevOps y Despliegue:** Docker, Docker Compose (gestión de puertos para gRPC), Git, GitHub.

---

## 🚀 Próximos pasos en StayHub

StayHub se basa en una arquitectura diseñada en la separación de responsabilidades, lo que facilita futuras integraciones como:

* Implementación de sistema de pagos.
* Sistema de reseñas por alojamiento.
* Caché con Redis.
