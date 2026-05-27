# 🏢 Sistema de Gestión Condominio Vista Verde

<div align="center">

![Java](https://img.shields.io/badge/Java-JDK%2017+-orange?style=for-the-badge&logo=java)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven)
![Architecture](https://img.shields.io/badge/Architecture-MVC-success?style=for-the-badge)
![FlatLaf](https://img.shields.io/badge/Design-FlatLaf-darkgreen?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Acad%C3%A9mico-important?style=for-the-badge)

</div>

---

# 📘 Descripción General

El **Sistema de Gestión Condominio Vista Verde** es una aplicación de escritorio desarrollada en **Java** para la administración integral de un condominio privado compuesto por **30 viviendas**.

El sistema permite automatizar procesos administrativos relacionados con:

- Control de propietarios y viviendas.
- Gestión de cuotas de mantenimiento.
- Registro y validación de pagos mensuales.
- Auditoría de morosidad.
- Generación de reportes administrativos.
- Envío automatizado de comprobantes de pago por correo electrónico.

El proyecto fue desarrollado bajo una arquitectura **MVC (Model-View-Controller)** estricta, aplicando buenas prácticas de ingeniería de software y principios de modularidad para garantizar mantenibilidad, escalabilidad y claridad estructural.

---

# ✨ Características Principales

## 🔐 Autenticación de Usuarios

- Inicio de sesión seguro mediante credenciales.
- Límite máximo de **3 intentos fallidos**.
- Bloqueo de acceso al exceder el límite permitido.
- Validación de usuarios antes del acceso al sistema.

---

## 🏠 Gestión de Casas y Propietarios

- Administración de exactamente **30 casas** del condominio.
- Registro y actualización de información de propietarios.
- Asociación de propietarios con viviendas específicas.
- Control individual por número de casa.

---

## 💰 Configuración de Cuota Mensual

- Configuración global de cuota de mantenimiento.
- Aplicación automática de cuota mensual a todas las viviendas.
- Centralización administrativa de pagos.

---

## 📅 Registro y Validación de Pagos

- Registro de pagos mensuales por vivienda.
- Validación automática de pagos pendientes.
- Restricción lógica:
  - ❌ No se puede pagar un mes si el anterior está pendiente.
- Historial de pagos almacenado localmente.

---

## 📊 Módulo de Reportes

### 📈 Reporte General de Ingresos

- Visualización consolidada de ingresos.
- Reportes administrativos financieros.
- Control de pagos recibidos.

### 🚨 Auditoría de Casas Morosas

- Identificación automática de morosidad.
- Consulta de cuotas pendientes.
- Generación de listados de auditoría.

---

## 📧 Envío Automático de Comprobantes

- Integración con **JavaMail API**.
- Envío automático de comprobantes vía correo electrónico.
- Confirmación inmediata después del pago.

---

## 💾 Persistencia de Datos

- Persistencia mediante archivos locales `.dat`.
- Uso de serialización de objetos Java.
- Almacenamiento ligero sin dependencia de bases de datos externas.

---

# 🏗️ Arquitectura y Tecnologías

| Tecnología | Descripción |
|---|---|
| ☕ Java | Lenguaje principal del sistema |
| 🧩 JDK 17+ | Plataforma requerida |
| 🖥️ Java Swing | Desarrollo de interfaces gráficas |
| 🎨 FlatLaf | Modernización visual del sistema |
| 📦 Maven | Gestión de dependencias |
| 🧠 MVC | Arquitectura de separación de responsabilidades |

---

# 🧱 Arquitectura del Proyecto

El proyecto implementa una arquitectura **MVC (Model-View-Controller)** estricta para garantizar organización, escalabilidad y mantenibilidad del sistema.

```plaintext
SistemaGestionCondominio/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/
│   │   │   │   ├── Casa.java
│   │   │   │   ├── Propietario.java
│   │   │   │   ├── Pago.java
│   │   │   │   └── Usuario.java
│   │   │   │
│   │   │   ├── logic/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── PagoService.java
│   │   │   │   ├── ReporteService.java
│   │   │   │   └── PersistenciaService.java
│   │   │   │
│   │   │   ├── ui/
│   │   │   │   ├── LoginFrame.java
│   │   │   │   ├── DashboardFrame.java
│   │   │   │   ├── CasasPanel.java
│   │   │   │   └── PagoDialog.java
│   │   │   │
│   │   │   └── Main.java
│   │   │
│   │   └── resources/
│   │
│   └── test/
│
├── docs/
│   ├── diagramas/
│   ├── manual_usuario/
│   ├── evidencias/
│   └── mockups/
│
├── data/
│   └── *.dat
│
├── pom.xml
└── README.md
📂 Explicación de Paquetes
📦 model

Contiene las entidades principales del sistema:

Casas
Propietarios
Pagos
Usuarios
Configuraciones

Representa la estructura de datos del dominio.

⚙️ logic

Implementa toda la lógica de negocio:

Validaciones
Autenticación
Gestión de pagos
Reportería
Persistencia
Serialización de datos
🖼️ ui

Contiene las interfaces gráficas desarrolladas con Java Swing:

Ventanas principales
Formularios
Paneles administrativos
Diálogos de interacción
📚 docs

Carpeta destinada a documentación técnica y académica:

Diagramas UML
Evidencias del sistema
Manuales de usuario
Capturas de funcionamiento
🛠️ Requisitos Previos

Antes de ejecutar el sistema, asegúrese de contar con:

☑️ Java JDK 17 o superior
☑️ Apache Maven
☑️ IDE recomendado:
IntelliJ IDEA
Apache NetBeans
Eclipse IDE
🚀 Instalación y Ejecución
1️⃣ Clonar el repositorio
git clone https://github.com/usuario/sistema-condominio-vista-verde.git
2️⃣ Ingresar al directorio del proyecto
cd sistema-condominio-vista-verde
3️⃣ Descargar dependencias y compilar
mvn clean install
4️⃣ Ejecutar el proyecto
Desde Maven
mvn exec:java
O ejecutar manualmente

Ejecutar la clase:

Main.java
📧 Configuración de JavaMail

Para habilitar el envío automático de comprobantes:

Configurar credenciales SMTP.
Definir correo emisor.
Habilitar autenticación segura.
Configurar contraseña de aplicación si se utiliza Gmail.

Ejemplo de tecnologías utilizadas:

JavaMail API
SMTP Gmail
🧪 Validaciones Implementadas

✔️ Control de acceso por intentos fallidos.
✔️ Restricción de pagos fuera de secuencia.
✔️ Validación de campos obligatorios.
✔️ Persistencia segura mediante serialización.
✔️ Gestión controlada de cuotas y estados de pago.
✔️ Validaciones administrativas de morosidad.

📈 Objetivos Académicos del Proyecto

Este proyecto tiene como finalidad aplicar conocimientos relacionados con:

Programación Orientada a Objetos.
Arquitectura de Software.
Desarrollo de interfaces gráficas.
Persistencia de datos.
Implementación del patrón MVC.
Gestión de proyectos ágiles.
Buenas prácticas de ingeniería de software.
👨‍💻 Equipo de Desarrollo (SCRUM)

Proyecto desarrollado bajo metodologías ágiles utilizando Jira Software para la planificación, organización y seguimiento de tareas.

👥 Integrantes
🔹 Integrante 1
🔹 Integrante 2
🔹 Integrante 3
🔹 Integrante 4
🔹 Integrante 5
🎓 Información Académica

Universidad Mariano Gálvez de Guatemala
Facultad de Ingeniería en Sistemas
Proyecto Académico Universitario

📄 Licencia

Proyecto desarrollado exclusivamente con fines académicos y educativos.

<div align="center">
🏡 Sistema de Gestión Condominio Vista Verde
Universidad Mariano Gálvez de Guatemala
</div> ```