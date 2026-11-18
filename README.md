# 🏛️ Taller de Arquitectura Hexagonal y DDD - Banco de Preguntas Saber Pro
**(Laboratorio de Ingeniería de Software II - Grupo 4B)**

Implementación de un microservicio para la gestión y creación de preguntas tipo **Saber Pro**, diseñado bajo los principios de **Domain-Driven Design (DDD)** y **Arquitectura Hexagonal (Puertos y Adaptadores)** utilizando Java y Spring Boot.

---

## 📋 Tabla de Contenidos
1. [Descripción del Proyecto](#-descripción-del-proyecto)
2. [Arquitectura del Sistema](#-arquitectura-del-sistema)
3. [Estructura de Directorios](#-estructura-de-directorios)
4. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
5. [Ejecución y Pruebas](#-ejecución-y-pruebas)
6. [Autores](#-autores)

---

## 🎯 Descripción del Proyecto
El objetivo de este taller (`Taller-DDD-y-Hex-Parte-1`) es desacoplar la lógica de negocio principal de los detalles de infraestructura (bases de datos, controladores web y frameworks externos). El sistema permite crear y validar preguntas garantizando el cumplimiento estricto de las reglas de negocio en el núcleo del dominio (validación de enunciados, opciones de respuesta y justificaciones).

---

## 🏗️ Arquitectura del Sistema
El proyecto divide sus responsabilidades en tres capas concéntricas independientes:

- **Dominio (`domain`):** Contiene el núcleo puro del negocio sin dependencias de frameworks externos. Incluye entidades (`Pregunta`, `OpcionRespuesta`), estados (`EstadoPregunta`), objetos de valor (`Enunciado`, `Justificacion`), excepciones de dominio (`ReglaNegocioException`) y los contratos de puertos.
- **Aplicación (`application`):** Orquesta los casos de uso del sistema (`CrearPreguntaUseCase`) mediante servicios de aplicación (`PreguntaApplicationService`), conectando los puertos de entrada y salida.
- **Infraestructura (`infrastructure`):** Implementa los adaptadores técnicos que interactúan con el exterior:
  - **Adaptadores de Entrada (`inbound/rest`):** Exponen la API REST (`PreguntaController`), DTOs y manejo global de excepciones HTTP (`ApiExceptionHandler`).
  - **Adaptadores de Salida (`outbound/persistence`):** Implementan la persistencia de datos tanto en memoria (`InMemoryQuestionAdapter`) como mediante Spring Data JPA (`JpaQuestionAdapter`).

---

## 📂 Estructura de Directorios

```text
Taller-DDD-y-Hex-Parte-1/
├── src/
│   ├── main/
│   │   ├── java/co/edu/unicauca/saberpro/
│   │   │   ├── application/
│   │   │   │   ├── port/
│   │   │   │   │   ├── inbound/          # Contratos de casos de uso
│   │   │   │   │   └── outbound/         # Contratos de persistencia
│   │   │   │   └── service/              # Implementación de casos de uso
│   │   │   ├── domain/
│   │   │   │   ├── exception/            # Excepciones de reglas de negocio
│   │   │   │   ├── model/                # Entidades y enums del dominio
│   │   │   │   ├── ports/                # Definición de puertos del dominio
│   │   │   │   └── valueobject/          # Value Objects (Enunciado, Justificación)
│   │   │   ├── infrastructure/
│   │   │   │   ├── inbound/rest/         # Controladores REST, DTOs y handlers
│   │   │   │   └── outbound/persistence/ # Adaptadores JPA y en memoria
│   │   │   └── SaberProHexagonalApplication.java
│   │   └── resources/
│   │       └── application.yml           # Configuración de Spring Boot
│   └── test/
│       └── java/co/edu/unicauca/saberpro/domain/
│           └── PreguntaTest.java         # Pruebas unitarias del dominio
├── pom.xml                               # Dependencies y configuración Maven
└── lisw2_Taller_12_g8.docx               # Documentación entregable del taller
```

---

## 👨‍💻 Autores

**Grupo 4B**

- Martín Silva
- Juan Manuel García
