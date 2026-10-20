# Taller 09: Comunicación síncrona y asíncrona de microservicios

**Banco de Preguntas Saber PRO** · Spring Boot, REST y RabbitMQ
Universidad del Cauca · Laboratorio de Ingeniería de Software II · Octubre 2026.2

## Integrantes

- Britney Benítez
- Juan Manuel García
- Yineidy Mamián Jiménez
- Martín Silva Cabrera

## Descripción

Este proyecto pasa el sistema monolítico del Banco de Preguntas Saber PRO a una arquitectura de **microservicios**. Combina dos tipos de comunicación:

- **Síncrona (REST/HTTP):** login, validación de preguntas y consultas entre servicios.
- **Asíncrona (RabbitMQ/AMQP):** envío de preguntas a revisión y notificaciones a revisores, sin bloquear al cliente.

El **Lifecycle Service** es la única autoridad sobre el estado de las preguntas. Los demás servicios solicitan transiciones o reaccionan a eventos, pero nunca cambian el estado por su cuenta.

## Clonar únicamente esta rama

Este repositorio tiene otras ramas con laboratorios distintos. Para descargar solo la rama del Taller 09, sin traer las demás:

```bash
git clone --branch taller09-microservicios --single-branch https://github.com/martin1061/LaboratorioIngenieriaSoftware2.git lisw2_taller09_microservicios
cd lisw2_taller09_microservicios
```

- `--branch taller09-microservicios` selecciona la rama del taller.
- `--single-branch` evita descargar el historial de las otras ramas.
- El último parámetro es el nombre de la carpeta donde queda el proyecto.

Para comprobar que estás en la rama correcta:

```bash
git branch
```

Debe mostrar `* taller09-microservicios`.

**Si ya clonaste el repositorio completo**, cambia a la rama así:

```bash
git fetch origin taller09-microservicios
git checkout taller09-microservicios
```

## Microservicios

| Servicio | Puerto | Responsabilidad | Comunicación |
|---|---|---|---|
| `user-service` | 8081 | Autenticar usuarios y verificar roles | REST |
| `validation-service` | 8082 | Validar enunciado, 4 opciones y respuesta correcta | REST |
| `question-service` | 8083 | Crear preguntas y coordinar el envío a revisión | REST + RabbitMQ (publica comando) |
| `lifecycle-service` | 8084 | Estados y transiciones de la pregunta | RabbitMQ (consume) + REST |
| `review-service` | 8085 | Asignar revisores y registrar resultados | REST + RabbitMQ (publica evento) |
| `notification-service` | 8086 | Enviar notificaciones al revisor | RabbitMQ (consume) |

## Estructura del repositorio

```
├── docker-compose.yml      # RabbitMQ (AMQP 5672 + consola 15672)
├── user-service/
├── validation-service/
├── question-service/
├── lifecycle-service/
├── review-service/
└── notification-service/
```

Cada servicio es un proyecto Spring Boot independiente con su propia base de datos H2 en memoria, siguiendo el patrón *Database per Service*.

## Flujo implementado

1. **Login:** `POST /auth/login` en User Service. Devuelve 401 si las credenciales son incorrectas.
2. **Crear pregunta:** `POST /questions` en Question Service, que llama por REST a Validation Service.
   - Si es válida, la guarda y responde `201 Created` en estado `DRAFT`.
   - Si no, responde `400 Bad Request` con los errores.
3. **Enviar a revisión:** `POST /questions/{id}/submit` publica el comando `SubmitQuestionForReview` en RabbitMQ y responde `202 Accepted` con un `operationId`.
4. **Procesamiento:** Lifecycle Service consume el comando, valida `DRAFT → PENDING_REVIEW` y registra el resultado (`COMPLETED` o `FAILED`).
5. **Consultar resultado:** `GET /operations/{operationId}` en Question Service.
6. **Asignar revisor:** `POST /reviews/assign` en Review Service.
   - Verifica que quien asigna sea ADMIN y que el revisor tenga rol REVISOR.
   - Pide a Lifecycle la transición `PENDING_REVIEW → IN_REVIEW`.
   - Si falla, la asignación queda en `FAILED` para no generar inconsistencias.
7. **Notificación:** Review Service publica el evento `ReviewerAssigned` y Notification Service lo consume sin bloquear la asignación.
8. **Resultado de revisión:** `POST /reviews/{questionId}/result` con `APPROVED` o `REJECTED`.

### Transiciones de estado permitidas

```
DRAFT → PENDING_REVIEW → IN_REVIEW → APPROVED → PUBLISHED → ARCHIVED
                                   ↘ REJECTED → DRAFT / PENDING_REVIEW
```

Una transición no permitida (por ejemplo, aprobar desde `DRAFT`) responde `409 Conflict`.

## Mensajería (RabbitMQ)

- **Exchange:** `saberpro.direct.exchange` (direct)

| Cola | Routing key | Mensaje |
|---|---|---|
| `lifecycle.commands` | `question.submit` | Comando `SubmitQuestionForReview` |
| `question.operation-result` | `question.operation-result` | Resultado de la operación |
| `notification.reviewer-assigned` | `reviewer.assigned` | Evento `ReviewerAssigned` |

Los mensajes se serializan en JSON con `Jackson2JsonMessageConverter`.

## Requisitos

- Java 17
- Maven 3.9+
- Docker Desktop

## Cómo ejecutar

**1. Levantar RabbitMQ** (en la raíz del proyecto):

```bash
docker compose up -d
```

Consola de administración: http://localhost:15672 (usuario `guest`, contraseña `guest`).

**2. Iniciar cada microservicio** en una terminal distinta:

```bash
cd user-service
mvn spring-boot:run
```

Repite con `validation-service`, `lifecycle-service`, `question-service`, `review-service` y `notification-service`. Cada servicio está listo cuando muestra `Started ...Application`.

**Equipos con poca memoria:** limita el heap de cada servicio.

```bash
mvn spring-boot:run "-Dspring-boot.run.jvmArguments=-Xms32m -Xmx192m -XX:+UseSerialGC"
```

## Usuarios de prueba

| Usuario | Contraseña | Rol | ID |
|---|---|---|---|
| `docente` | `docente123` | DOCENTE | 1 |
| `admin` | `admin123` | ADMIN | 2 |
| `revisor1` | `revisor123` | REVISOR | 3 |
| `revisor2` | `revisor123` | REVISOR | 4 |

## Endpoints principales

| Método | URL | Descripción |
|---|---|---|
| POST | `localhost:8081/auth/login` | Autenticación |
| GET | `localhost:8081/users/{id}` | Datos y roles de un usuario |
| POST | `localhost:8082/validate` | Validar estructura de una pregunta |
| POST | `localhost:8083/questions` | Crear pregunta (queda en DRAFT) |
| GET | `localhost:8083/questions/{id}` | Consultar pregunta |
| POST | `localhost:8083/questions/{id}/submit` | Enviar a revisión (202) |
| GET | `localhost:8083/operations/{operationId}` | Resultado de la operación asíncrona |
| GET | `localhost:8084/lifecycle/{id}` | Estado actual de la pregunta |
| POST | `localhost:8084/lifecycle/{id}/transition` | Solicitar transición de estado |
| POST | `localhost:8085/reviews/assign` | Asignar revisor |
| POST | `localhost:8085/reviews/{questionId}/result` | Aprobar o rechazar |
| GET | `localhost:8086/notifications` | Notificaciones enviadas |

## Ejemplo rápido (PowerShell)

```powershell
$h = @{ "Content-Type" = "application/json" }

# Crear pregunta
$q = '{"text":"¿Qué desacopla servicios?","options":[{"id":"A","text":"Monolito"},{"id":"B","text":"Mensajería"},{"id":"C","text":"Singleton"},{"id":"D","text":"Factory"}],"correctOptionId":"B","authorId":1}'
Invoke-RestMethod -Method Post -Uri http://localhost:8083/questions -Headers $h -Body $q

# Enviar a revisión (asíncrono)
$r = Invoke-RestMethod -Method Post -Uri "http://localhost:8083/questions/1/submit?requestedBy=1"
Invoke-RestMethod http://localhost:8083/operations/$($r.operationId)

# Asignar revisor
Invoke-RestMethod -Method Post -Uri http://localhost:8085/reviews/assign -Headers $h -Body '{"questionId":1,"reviewerId":3,"assignedBy":2}'
```

## Tecnologías

Spring Boot 3.2.5 · Spring Web · Spring Data JPA · H2 · Spring AMQP · RabbitMQ · Docker Compose · Maven
