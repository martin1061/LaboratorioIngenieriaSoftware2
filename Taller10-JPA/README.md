# Banco de Preguntas Saber Pro — Taller JPA

Base de trabajo para el Taller 10. Java 17, Spring Boot 3.4.5, Spring Data JPA, Maven y H2.

## Arquitectura
- `api`: endpoints REST.
- `application`: casos de uso.
- `domain`: modelo y contratos sin anotaciones JPA.
- `infrastructure.jpa`: registros JPA, repositorios Spring Data y adaptación al contrato.

## Ejecutar
Requisitos: JDK 17+ y Maven 3.8+.
```bash
mvn clean test
mvn spring-boot:run
```
H2 console: http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:mem:questionbank`; usuario `sa`; contraseña vacía.

## Datos de referencia requeridos
Antes de crear preguntas deben existir un usuario autor y un tema asociado a una competencia. Esta base incluye los registros y repositorios JPA, pero no incluye todavía endpoints de administración de usuarios/competencias/temas. Para probar el POST de preguntas se deben crear datos de referencia mediante seed data o completar esos endpoints.

## Endpoint disponible
`POST /api/questions`
```json
{
  "context": "Contexto de la pregunta",
  "statement": "¿Cuál opción es correcta?",
  "justification": "Justificación de la respuesta",
  "bibliography": "Referencia consultada",
  "difficulty": "MEDIUM",
  "authorId": 1,
  "topicId": 1
}
```
`GET /api/questions` y `GET /api/questions/{id}`.

## Importante
Este es un esqueleto inicial de implementación y no debe entregarse como aplicación final sin completar: seed data/endpoints de administración, persistencia de opciones, transiciones de estado persistidas, revisiones, cuestionarios, validaciones estructurales completas y pruebas de integración. No se afirma que compile o pase pruebas hasta ejecutar `mvn clean test` en un entorno con Maven.
