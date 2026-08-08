# SmartStore

Sistema de gestión de tienda e inventario desarrollado en Java utilizando
Programación Orientada a Objetos, Java Swing, estructuras de datos,
algoritmos, pruebas unitarias y Maven.

SmartStore permite gestionar productos, clientes, pedidos, facturación e
inventario mediante una aplicación de escritorio con interfaz gráfica.

---

## 📋 Descripción

SmartStore es una aplicación de escritorio desarrollada como proyecto
integrador académico.

El sistema busca facilitar la administración de una tienda mediante la
gestión de:

- Productos.
- Categorías.
- Proveedores.
- Clientes.
- Pedidos.
- Detalles de pedidos.
- Facturas.
- Inventario.
- Reportes.

El proyecto integra conceptos de Programación Orientada a Objetos,
estructuras de datos, algoritmos, manejo de excepciones, pruebas unitarias
y desarrollo de interfaces gráficas con Java Swing.

---

## 🎯 Objetivo general

Diseñar e implementar una aplicación de escritorio en Java que permita
gestionar los procesos principales de una tienda, aplicando principios de
Programación Orientada a Objetos, estructuras de datos, algoritmos,
validaciones, manejo de excepciones y pruebas automatizadas.

---

## 🎯 Objetivos específicos

- Gestionar productos y su información dentro del inventario.
- Gestionar clientes y sus datos.
- Administrar categorías y proveedores.
- Crear y administrar pedidos.
- Calcular totales de pedidos.
- Generar facturas a partir de pedidos.
- Aplicar validaciones y manejo de excepciones.
- Implementar estructuras de datos para apoyar la gestión de información.
- Implementar algoritmos de búsqueda, ordenamiento y procesamiento.
- Analizar la complejidad de operaciones mediante Big O.
- Desarrollar una interfaz gráfica utilizando Java Swing.
- Realizar pruebas unitarias utilizando JUnit 5.
- Medir la cobertura del código utilizando JaCoCo.
- Gestionar el proyecto mediante Git y GitHub.

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal |
| Java Swing | Interfaz gráfica |
| Maven | Gestión y construcción del proyecto |
| JUnit 5 | Pruebas unitarias |
| JaCoCo | Cobertura de código |
| Git | Control de versiones |
| GitHub | Repositorio y colaboración |

---

## 💻 Requisitos

Antes de ejecutar el proyecto se necesita tener instalado:

- Java JDK 17 o superior.
- Apache Maven 3.9 o superior.
- Git.
- Un IDE compatible con Java, como IntelliJ IDEA, Eclipse o Visual Studio
  Code con soporte para Java.

Para comprobar las versiones:

```bash
java -version

---

SmartStore/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── smartstore/
│   │               ├── Main.java
│   │               │
│   │               ├── model/
│   │               │   ├── Categoria.java
│   │               │   ├── Cliente.java
│   │               │   ├── DetallePedido.java
│   │               │   ├── Factura.java
│   │               │   ├── Pedido.java
│   │               │   ├── Producto.java
│   │               │   └── Proveedor.java
│   │               │
│   │               ├── service/
│   │               │   ├── ClienteService.java
│   │               │   ├── InventarioService.java
│   │               │   └── PedidoService.java
│   │               │
│   │               ├── structures/
│   │               │
│   │               ├── algorithms/
│   │               │
│   │               ├── exceptions/
│   │               │
│   │               └── ui/
│   │                   ├── MainFrame.java
│   │                   ├── PanelInicio.java
│   │                   ├── PanelClientes.java
│   │                   ├── PanelProductos.java
│   │                   ├── PanelPedidos.java
│   │                   ├── PanelFacturas.java
│   │                   └── PanelReportes.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── smartstore/
│                   ├── model/
│                   ├── service/
│                   ├── structures/
│                   └── algorithms/
│
├── doc/
│   ├── uml/
│   ├── capturas/
│   ├── documento-tecnico.md
│   └── manual-usuario.md
│
├── pom.xml
├── README.md
├── LICENSE
└── .gitignore
