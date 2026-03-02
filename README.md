# Proyecto POO - Sistema de Gestión de Taxis

## Explicación del gráfico UML


![WhatsApp Image 2026-03-02 at 16 55 19 (1)](https://github.com/user-attachments/assets/4103d1f1-ae29-44c2-a57e-446a40f5e7e3)

Diagrama UML: Sistema de Gestión de Flota de Taxis

Este proyecto modela un sistema de gestión de taxis aplicando Programación Orientada a Objetos. El diagrama UML se diseñó para separar claramente el modelo (clases principales del dominio), la lógica de negocio (servicios) y la persistencia (guardar/cargar datos), asegurando un diseño entendible, escalable y alineado a los pilares de POO.

1) Modelo de Usuarios

La clase User es abstracta porque representa el concepto general de usuario dentro del sistema. Contiene atributos comunes como id, name, email y ubicación (latitude, longitude). Además, define el método authenticate(String password) para que cada tipo de usuario implemente su propia validación, demostrando abstracción y polimorfismo.

A partir de User se derivan:

AdminUser: representa al administrador del sistema. Agrega role y puede generar reportes o acceder a funciones administrativas.

ClientUser: representa al cliente. Agrega phoneNumber y contiene acciones como solicitar taxi.

2) Modelo de Vehículos

La clase Vehicle es abstracta porque define lo común a cualquier vehículo: id, licensePlate, brand, model y year. Incluye métodos abstractos como displayInfo() y getVehicleType() para que las clases concretas los implementen según su tipo.

De Vehicle hereda:

Taxi: vehículo específico del sistema. Incluye driverName, available y ubicación del taxi. Implementa los métodos abstractos de Vehicle y añade lógica como cálculo de distancia.

3) Geolocalización con Interfaz

La interfaz IGeolocalizable se usa para estandarizar objetos que tienen ubicación. Define métodos como getLatitude(), getLongitude() y setLocation(lat, lon).
En el sistema, la implementan User y Taxi, permitiendo que ambos manejen coordenadas de forma consistente.

4) Trayectorias (Viajes)

La clase Trajectory representa un viaje/ruta. Contiene:

Coordenadas de origen y destino

Valores calculados como distancia y duración estimada

Asociación con un Taxi y un User

Relaciones principales:

Un User puede tener muchas trayectorias (historial de viajes).

Un Taxi puede participar en muchas trayectorias.

Cada Trajectory está asociada a 1 taxi y 1 usuario, porque un viaje específico siempre tiene un solicitante y un vehículo asignado.

5) Servicios (Lógica de Negocio)

Para evitar mezclar lógica con el modelo, se usan servicios que gestionan operaciones y colecciones:

UserService: registra, busca y autentica usuarios. Usa estructuras como Map, Set y List para eficiencia (ej: evitar emails duplicados y buscar por id rápidamente).

TaxiService: registra taxis, valida duplicados (id/placa), maneja disponibilidad y permite encontrar taxis disponibles/cercanos.

TrajectoryService: crea y gestiona trayectorias, permitiendo consultas por usuario/taxi y cálculos como distancia total.

6) Persistencia (Guardar/Cargar Estado)

Se incorpora un PersistenceService para permitir guardar y cargar el estado del sistema. Esto hace que el sistema no sea “estático”, ya que puede persistir usuarios, taxis y trayectorias entre ejecuciones (por ejemplo, mediante serialización).

## Estructura del Proyecto

```
/proyecto-poo-taxi
│
├── /src
│   ├── /model
│   │   ├── Vehicle.java (clase abstracta o base)
│   │   ├── Taxi.java (hereda de Vehicle)
│   │   ├── Trajectory.java
│   │   ├── User.java
│   │   └── IGeolocalizable.java (interfaz)
│   │
│   ├── /service (opcional, si quieres mostrar más estructura)
│   └── Main.java
│
├── /diagrams
│   └── (diagramas UML, casos de uso, etc.)
│
├── README.md
└── build.xml (proyecto NetBeans con Ant)






## Pasos para Implementar el Proyecto

### Paso 1: Crear la Interfaz IGeolocalizable
- **Ubicación**: `src/model/IGeolocalizable.java`
- **Propósito**: Interfaz para objetos que pueden ser geolocalizados
- **Métodos sugeridos**: 
  - `getLatitude()`: double
  - `getLongitude()`: double
  - `setLocation(double lat, double lon)`: void

### Paso 2: Crear la Clase Base Vehicle
- **Ubicación**: `src/model/Vehicle.java`
- **Tipo**: Clase abstracta
- **Propósito**: Representa un vehículo genérico
- **Atributos sugeridos**:
  - `id`: String o int
  - `licensePlate`: String
  - `brand`: String
  - `model`: String
  - `year`: int
- **Métodos**: Constructor, getters, setters, métodos abstractos si es necesario

### Paso 3: Crear la Clase Taxi
- **Ubicación**: `src/model/Taxi.java`
- **Herencia**: Extiende de `Vehicle`
- **Implementa**: `IGeolocalizable` (opcional)
- **Atributos adicionales**:
  - `driverName`: String
  - `available`: boolean
  - `currentLocation`: (lat, lon)
- **Métodos**: Constructor, métodos específicos de taxi

### Paso 4: Crear la Clase Trajectory
- **Ubicación**: `src/model/Trajectory.java`
- **Propósito**: Representa una trayectoria o ruta
- **Atributos sugeridos**:
  - `origin`: (lat, lon)
  - `destination`: (lat, lon)
  - `distance`: double
  - `duration`: int (en minutos)
  - `taxi`: Taxi (relación con Taxi)

### Paso 5: Crear la Clase User
- **Ubicación**: `src/model/User.java`
- **Propósito**: Representa un usuario del sistema
- **Atributos sugeridos**:
  - `id`: String o int
  - `name`: String
  - `email`: String
  - `phone`: String
  - `location`: (lat, lon) - implementa IGeolocalizable

### Paso 6: Crear el Paquete Service (Opcional)
- **Ubicación**: `src/service/`
- **Clases sugeridas**:
  - `TaxiService.java`: Lógica de negocio para taxis
  - `UserService.java`: Lógica de negocio para usuarios
  - `TrajectoryService.java`: Gestión de trayectorias

### Paso 7: Crear la Clase Main
- **Ubicación**: `src/Main.java`
- **Propósito**: Punto de entrada de la aplicación
- **Contenido**: Método `main()` con ejemplos de uso de las clases

### Paso 8: Actualizar Configuración del Proyecto
- Actualizar `nbproject/project.properties` si cambias el paquete principal
- El `main.class` debería apuntar a `Main` (o el que elijas)

### Paso 9: Crear Diagramas
- **Ubicación**: `diagrams/`
- **Diagramas sugeridos**:
  - Diagrama de clases UML
  - Diagrama de casos de uso
  - Diagrama de secuencia (opcional)

##  Notas Importantes

1. **Paquetes Java**: Asegúrate de que todas las clases tengan la declaración de paquete correcta:
   - `package model;` para clases en `/src/model/`
   - `package service;` para clases en `/src/service/`

2. **NetBeans**: Este proyecto usa Ant (build.xml), no Maven/Gradle. Si quieres migrar a Maven:
   - Crea `pom.xml`
   - Configura las dependencias necesarias
   - Actualiza la estructura según convenciones de Maven

3. **Java Version**: El proyecto está configurado para Java 21

## 🔧 Comandos Útiles

- **Compilar**: Desde NetBeans: Build → Build Project
- **Ejecutar**: Desde NetBeans: Run → Run Project
- **Limpiar**: Desde NetBeans: Build → Clean and Build Project

## Próximos Pasos

1. Implementar las clases base con sus atributos y métodos básicos
2. Agregar relaciones entre clases (asociaciones, herencia)
3. Implementar la lógica de negocio en el paquete service
4. Crear casos de prueba en Main.java
5. Generar diagramas UML del sistema
6. Documentar con JavaDoc
