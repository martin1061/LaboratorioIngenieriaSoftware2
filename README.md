# 📐 Taller de Polimorfismo - Figuras Geométricas (Laboratorio de Ingeniería de Software 2)(seccion de trabajos de martin silva cabrera)

Este proyecto en Java demuestra la aplicación del **Polimorfismo** y la **Herencia** utilizando clases abstractas para el cálculo de áreas y perímetros de distintas figuras geométricas.

---

## 🚀 Descripción del Proyecto

El programa define una clase abstracta base `Figure` que establece el contrato para calcular el área y el perímetro. Diversas figuras geométricas heredan de esta clase y proporcionan sus propias implementaciones concretas.

### 🔷 Figuras Soportadas

- **Circulo (`Circle`):** Calcula área ($\pi \times r^2$) y perímetro ($2 \times \pi \times r$).
- **Cuadrado (`Square`):** Calcula área ($l^2$) y perímetro ($4 \times l$).
- **Triángulo Rectángulo (`Triangle`):** Calcula área ($\frac{base \times altura}{2}$) y perímetro sumando base, altura e hipotenusa.

---

## 🛠️ Conceptos de Programación Orientados a Objetos

- **Clases Abstractas:** Uso de la clase abstracta `Figure` con métodos abstractos `calculateArea()` y `getPerimeter()`.
- **Polimorfismo:** Almacenamiento de diferentes subtipos de figuras dentro de una colección unificada (`List<Figure>`) para procesarlas de manera genérica.
- **Sobrescritura de Métodos (`@Override`):** Implementación de la lógica específica de cálculo en cada subclase.

---

## 💻 Ejecución del Proyecto

### 1. Compilar el archivo Java

```bash
javac Main.java
