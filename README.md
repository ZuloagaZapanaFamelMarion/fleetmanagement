# Proyecto POO - Sistema de Gestión de Taxis

## Explicación del gráfico UML

![WhatsApp Image 2026-02-04 at 21 44 56](https://github.com/user-attachments/assets/24e80c1b-8f79-4012-99d3-c24250d89b66)


Diagrama UML: Sistema de Gestión de Flota de Taxis

1) Visión general
Este diagrama UML representa la arquitectura del Sistema de Gestión de Flota de Taxis. Muestra las clases principales, sus relaciones (herencia e implementación), y cómo se modela el viaje mediante una clase específica.

2) Elementos del diagrama
Clases abstractas: Vehicle, User
Clases concretas: Taxi, AdminUser, ClientUser
Interfaz: IGeolocalizable
Clase de viaje/ruta: Trajectory

3) Relaciones UML clave
Herencia (extends):
Taxi extiende Vehicle
AdminUser y ClientUser extienden User
Realización (implements):
Taxi implementa IGeolocalizable
User implementa IGeolocalizable (y sus subclases heredan esta capacidad)
Asociación:
Trajectory se asocia con Taxi y con User, porque un viaje requiere un taxi asignado y un usuario solicitante.

4) Responsabilidad de cada clase
Vehicle (abstracta): define atributos comunes del vehículo y obliga a implementar displayInfo() y getVehicleType().
Taxi: especializa Vehicle, maneja disponibilidad y ubicación (latitud/longitud) y permite calcular distancias.
IGeolocalizable: contrato de geolocalización con getLatitude(), getLongitude() y setLocation().
User (abstracta): centraliza datos del usuario, ubicación y define el método abstracto authenticate().
AdminUser: usuario administrador, con autenticación más estricta y métodos de administración (por ejemplo, generar reportes).
ClientUser: usuario cliente, con autenticación propia y métodos de uso (por ejemplo, solicitar taxi).
Trajectory: representa el viaje, almacenando origen, destino, distancia y duración, conectando al User con el Taxi asignado.

5) Flujo típico del sistema
1. El ClientUser solicita un taxi.
2. El sistema compara ubicaciones porque User y Taxi cumplen IGeolocalizable.
3. Se selecciona el taxi disponible más cercano.
4. Se crea un objeto Trajectory con el usuario, el taxi y los datos de origen/destino; el sistema calcula distancia y duración.

6) Principios de POO reflejados
Abstracción: Vehicle y User evitan instanciar tipos genéricos y definen contratos.
Herencia: reutiliza atributos y comportamientos comunes en taxis y usuarios.
Encapsulamiento: atributos privados/protected y acceso mediante getters/setters.
Polimorfismo: authenticate() se comporta distinto en AdminUser y ClientUser.

7) Nota de consistencia
Si en el código los constructores de AdminUser y ClientUser reciben id, el diagrama UML debe reflejarlo para mantener consistencia entre diseño y implementación.

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






## 🚀 Pasos para Implementar el Proyecto

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

## 📝 Notas Importantes

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

## 📚 Próximos Pasos

1. Implementar las clases base con sus atributos y métodos básicos
2. Agregar relaciones entre clases (asociaciones, herencia)
3. Implementar la lógica de negocio en el paquete service
4. Crear casos de prueba en Main.java
5. Generar diagramas UML del sistema
6. Documentar con JavaDoc
