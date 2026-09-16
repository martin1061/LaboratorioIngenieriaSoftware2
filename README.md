# Banco de Preguntas - Ingeniería de Software II

## Laboratorio: Patrón en Capas y Micro Patrón MVC

Aplicación de escritorio desarrollada en Java SE utilizando Swing para la gestión y visualización de un banco de preguntas orientado a la preparación de estudiantes para las pruebas Saber Pro.

El proyecto implementa una arquitectura monolítica en capas, el micro patrón MVC y el patrón de comportamiento Observer.

---

## 👥 Autores

| Nombre |
|---|
| **BRITNEY BENÍTEZ** |
| **JUAN MANUEL GARCÍA** |
| **YINEIDY MAMIÁN JIMÉNEZ** |
| **MARTÍN SILVA CABRERA** |

**Universidad del Cauca**  
**Programa de Ingeniería de Sistemas**  
**Ingeniería de Software II**  
**2026.2**

---

## 🎯 Objetivo

Implementar una aplicación de escritorio que permita visualizar y administrar el estado de las preguntas de un banco de preguntas para apoyar la preparación de los estudiantes para las pruebas Saber Pro.

La aplicación permite:

- Visualizar las preguntas almacenadas en el banco.
- Seleccionar preguntas mediante un `ComboBox`.
- Consultar la información de cada pregunta.
- Visualizar sus opciones y respuesta correcta.
- Cambiar el estado de una pregunta.
- Visualizar estadísticas de las preguntas según su estado.
- Representar mediante una gráfica de pastel el porcentaje de preguntas por estado.
- Notificar automáticamente a las vistas cuando cambia el estado de una pregunta.

---

## 🏗️ Arquitectura

El proyecto utiliza una arquitectura monolítica organizada en capas:

```text
Banco de Preguntas
│
├── presentation
│   ├── GUIQuestions.java
│   ├── GUIObserver1.java
│   ├── GUIObserver2.java
│   └── Main.java
│
├── domain
│   ├── Question.java
│   ├── QuestionDistractors.java
│   ├── QuestionService.java
│   └── QuestionRepository.java
│
├── access
│   └── QuestionImplRepository.java
│
└── infra
    ├── Observer.java
    └── Subject.java
## 📥 Clonar el proyecto

Para obtener directamente la versión correspondiente al laboratorio de MVC y Observer, ejecutar:

```bash
git clone -b laboratorio-mvc-observer https://github.com/martin1061/LaboratorioIngenieriaSoftware2.git
