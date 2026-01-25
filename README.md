# StayHub - Accommodation 

## 📍 Estado del proyecto

🚧 Proyecto en desarrollo

La arquitectura y los componentes evolucionan de forma progresiva conforme se incorporan nuevas funcionalidades y tecnologías.
Para consultar el código del proyecto se debe acceder al branch "testing"

## 📌 Descripción
StayHub - Accommodation es el microservicio encargado de gestionar alojamientos y habitaciones dentro del proyecto StayHub

Este servicio forma parte de la arquitectura basada en microservicios orientada al aprendizaje y el diseño de sistemas backend actuales, simulando el funcionamiento real de una plataforma de gestión de alojamientos y reservas.

El objetivo principal de este microservicio se basa en centralizar la lógica relacionada con: 

- Creación y modificación de alojamientos
- Creación y modificación de habitaciones
- Creación escalonada de alojamientos (draft)
- Búsqueda de alojamientos y habitaciones
- Validaciones estructurales y de dominio

## 🎯 Responsabilidad del microservicio

Este microservicio cumple con las siguientes responsabilidades:

- Crear, modificar y consultar alojamientos
- Crear, modificar y consultar habitaciones
- Gestionar el sistema de creación escalonada de alojamientos (draft)
- Endpoints de búsqueda avanzada de alojamientos y habitaciones
- Validar país y ciudad mediante comunicación con el microservicio StayHub-Country
- Solicitar comprobaciones de disponibiliada mediante comunicación con el microservicio de reservas

Este microservicio no es responsable de: 

- Crear o confirmar reservas
- Gestionar ocupación real de habitaciones
- Almacenar reservas o bloqueos
- Determinar disponibilidad final

## 🧩 Modelo de dominio

Alojamiento

Un alojamiento representa una propiedad (hotel, apartamento, hostel, etc) que puede contener una o varias habitaciones
- Solo se podrá borrar un alojamiento por parte de un administrador
- Para poder eliminar un alojamiento, todas las habitaciones asociadas deben haber sido eliminadas previamente

Aunque la lógica de eliminación está implementada, no se aplica en producción, ya que en entornos reales, se suele mantener los alojamientos por trazabilidad histórica al menos 3 años desde la última reserva.

Habitaciones
Las habitaciones dependen completamente del alojamiento
- No existen habitaciones sin alojamiento
- Solo se podrá borrar una habitación por parte de un administrador
- Las habitaciones pueden ser bloquedas por parte del propietario del alojamiento.

En lugar de eliminar los alojamientos o habitaciones el sistema utiliza bloqueos de habitaciones como solución principal para retirarlos de las búsquedas
Un bloqueo impide la creación de nuevas reservas en un rango de fechas, siempre que dicho rango no coincida con reservas ya existentes

Permitiendo asi: 

- Respetar reservas previamente confirmadas
- Evitar pérdida de información histórica
- Desactivar de manera progresiva habitaciones o alojamientos sin eliminarlos

Un alojamiento solo aparece en los resultados de búsqueda cuando al menos una de sus habitaciones está disponible dentro del rango de fechas solicitadas por un cliente

Por lo tanto, cuando todas las habitaciones de un alojamiento se encuentran bloqueadas, el alojamiento desaparece de todas las búsquedas posibles, pero sus datos siguen existiendo en el sistema

Este diseño permite simular el cierre de un alojamiento de forma realista, manteniendo su trazabilidad histórica

## 🧱 Sistema de creación escalonada (Draft)

El sistema drafct permite crear alojamiento de forma progresiva

Esta práctica permite simular escenarios reales donde: 
- El propietario crea inicialmente el alojamiento
- Copmleta la información por partes
- El alojamiento no se publica hasta cunmplir los requisitos establecidos

## 🔍 Sistema de búsquedas

Para poder realizar una búsqueda se debe proporcionar la siguiente información:

- Ciudad
- Capacidad de personas
- Número de habitaciones
- Fecha de entrada
- Fecha de salida

### Funcionamiento general (Búsquedas)

**1️⃣ Autenticación del usuario**

Para poder realizar la búsqueda, el usuario debe estar registrado y autenticado en el sistema. Esto permite asociar la búsqueda a un usuario en concreto 

**2️⃣ Párametros obligatorios para búsqueda**

Toda búsqueda debe incluir obligatoriamente los siguientes parámetros:

- Ciudad
- Fecha de entrada
- Fecha de salida
- Número de personas
- Número de habitaciones

Si falta alguno de estos parámetros la búsqueda no podrá ser realizada

**3️⃣ Validación de fechas**

Antes de iniciar la búsqueda se comprueba que las fechas proporcionadas por el cliente sean validas
- La fecha de entrada no puede ser anterior a la fecha actual
- La fecha de salida debe ser posterior a la fecha de entrada
- El rango de fechas no puede exceder el límite máximo permitido (en este caso 1 año desde la fecha actual)

Estas validaciones garantizan búsquedas coherentes

**4️⃣ Registro de búsqueda**

La última búsqueda de cada cliente es guardada, incluyendo todos los datos de la búsqueda

**5️⃣ Filtrado inicial de habitaciones**

En este primer filtrado se buscan habitaciones que cumplan con los requisitos demanadados por el cliente
- Coincidencia en ciudad y número de habitaciones
- Capacidad suficiente

**6️⃣ Verificación de disponibilidad real**

Las habitaciones filtradas, pasan a ser validadas por el microservicio de reservas, comprobando que no existen reservas ni bloqueos que se solapen con el rango de fechas solicitado por el cliente

Solo las habitaciones que pasan este filtro continuan el proceso

**7️⃣ Agrupación y filtrado de alojamientos**

Dado que el objetivo final de este endpoint es mostrar los alojamientos que contienen habitaciones disponibles, las habitaciones disponibles se agrupan por alojamiento

Para cada alojamiento se verifica además: 

- El alojamiento debe estar publicado (Draft cumplimentado)
- Cumpla con los requisitos de estancia minima y maxima
- El período solicitado se encuentre dentro de los meses de disponibilidad del alojamiento

**8️⃣ Resultado de búsqueda**

El resultado final de la búsqueda es una lista de alojamientos disponibles. Cada uno con al menos una habitación reservable

### Consultas de habitaciones de un alojamiento

Una vez seleccionado el alojamiento, el sistema permite consultar las habitaciones disponibles asociadas a dicho alojamiento.

Este proceso reutiliza las validaciones de fechas y disponibilidad, devolviendo únicamente las habitaciones, junto con información detallada, actualizada en el momento de la búsqueda

## 🔗 Comunicación entre microservicios

Este servicio se comunica mediante gRPC con los siguientes microservicios: 

StayHub-Reservation

Utilizado para: 
- Comprobar disponibilidad
- Validar bloqueos
- Validar reservas existentes
- Impedir operaciones incompatibles con futuras reservas

Reservation es el microservicio con la verdad final sobre ocupación

StayHub-Country

Utilizado para: 
- Validar país
- Validar ciudad
- Garantizar coherencia de datos geográficos

## 🔐 Seguridad y control de acceso
- Los propietarios solo pueden modificar sus propios alojamientos
- Existen endpoint de uso exclusivo de prueba o admin
- Los endpoint están protegidos y solo podrán ser usados por usuarios o propietarios según correspondencia
  
## 🛠️ Tecnologías utilizadas
- Java
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- gRPC
- REST API
- JSON
- H2 (para desarrollo)
- Git

## 📘 Contexto del proyecto

Este microservicio forma parte del proyecto StayHub, una proyecto backend diseñado con fines de aprendizaje y de arquitectura, orientada a simular escenarios reales utilizados en sistemas de gestión de alojamientos.
