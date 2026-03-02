import model.Taxi;
import model.Vehicle;
import model.User;
import model.ClientUser;
import model.AdminUser;
import model.Trajectory;
import service.TaxiService;
import service.UserService;
import service.TrajectoryService;
import java.util.List;

/**
 * Clase principal del sistema de gestión de taxis
 * Demuestra la integración completa del sistema al 60%
 * 
 * @author marionzuloagazapana
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestión de Taxis - Avance 60% ===\n");

        // Inicializar servicios (uso de colecciones: List, Map, Set)
        TaxiService taxiService = new TaxiService();
        UserService userService = new UserService();
        TrajectoryService trajectoryService = new TrajectoryService();

        // ========== SECCIÓN 1: REGISTRO DE TAXIS ==========
        System.out.println("=== 1. REGISTRO DE TAXIS ===");
        
        // Creación de taxis usando polimorfismo (referencias de tipo Vehicle)
        Vehicle vehicle1 = new Taxi("T001", "ABC-123", "Toyota", "Corolla", 2020, "Juan Pérez");
        ((Taxi) vehicle1).setLocation(-12.0464, -77.0428); // Lima, Perú

        Vehicle vehicle2 = new Taxi("T002", "XYZ-456", "Nissan", "Sentra", 2021, "María García");
        ((Taxi) vehicle2).setLocation(-12.0564, -77.0528);

        Vehicle vehicle3 = new Taxi("T003", "DEF-789", "Hyundai", "Elantra", 2022, "Carlos Rodríguez");
        ((Taxi) vehicle3).setLocation(-12.0664, -77.0628);

        // Registrar taxis usando el servicio (usa Map y Set internamente)
        taxiService.registerTaxi((Taxi) vehicle1);
        taxiService.registerTaxi((Taxi) vehicle2);
        taxiService.registerTaxi((Taxi) vehicle3);

        System.out.println("Taxis registrados: " + taxiService.getAllTaxis().size());
        System.out.println("Taxis disponibles: " + taxiService.getAvailableTaxis().size());
        System.out.println();

        // Demostración de búsqueda por ID y placa (uso de Map)
        Taxi foundTaxi = taxiService.findTaxiById("T001");
        System.out.println("Taxi encontrado por ID 'T001': " + foundTaxi.getDriverName());
        Taxi foundByPlate = taxiService.findTaxiByLicensePlate("XYZ-456");
        System.out.println("Taxi encontrado por placa 'XYZ-456': " + foundByPlate.getDriverName());
        System.out.println();

        // ========== SECCIÓN 2: REGISTRO DE USUARIOS ==========
        System.out.println("=== 2. REGISTRO DE USUARIOS ===");
        
        // Crear usuarios usando polimorfismo (referencias de tipo User)
        User user1 = new ClientUser(1L, "Carlos López", "carlos@email.com", "987654321");
        user1.setLocation(-12.0364, -77.0328);
        
        User user2 = new ClientUser(2L, "Ana Martínez", "ana@email.com", "987654322");
        user2.setLocation(-12.0664, -77.0628);
        
        User admin1 = new AdminUser(3L, "Pedro Admin", "admin@taxi.com", "Super Admin");
        admin1.setLocation(-12.0500, -77.0500);

        // Registrar usuarios usando el servicio (usa Map y Set para emails únicos)
        userService.registerUser(user1);
        userService.registerUser(user2);
        userService.registerUser(admin1);

        System.out.println("Usuarios registrados: " + userService.getUserCount());
        System.out.println("Clientes: " + userService.getClientUsers().size());
        System.out.println("Administradores: " + userService.getAdminUsers().size());
        System.out.println();

        // Demostración de búsqueda por ID (uso de Map)
        User foundUser = userService.findUserById(1L);
        System.out.println("Usuario encontrado por ID '1': " + foundUser.getName());
        User foundByEmail = userService.findUserByEmail("ana@email.com");
        System.out.println("Usuario encontrado por email 'ana@email.com': " + foundByEmail.getName());
        System.out.println();

        // ========== SECCIÓN 3: DEMOSTRACIÓN DE POLIMORFISMO ==========
        System.out.println("=== 3. DEMOSTRACIÓN DE POLIMORFISMO ===");
        
        // Polimorfismo con Vehicles
        System.out.println("\n--- Polimorfismo con Vehicles ---");
        Vehicle[] vehicles = {(Vehicle) vehicle1, (Vehicle) vehicle2, (Vehicle) vehicle3};
        for (Vehicle v : vehicles) {
            System.out.println("- Tipo: " + v.getVehicleType() + 
                             " | ID: " + v.getId() + 
                             " | Placa: " + v.getLicensePlate());
            v.displayInfo(); // Método polimórfico
        }

        // Polimorfismo con Users
        System.out.println("\n--- Polimorfismo con Users ---");
        List<User> allUsers = userService.getAllUsers();
        for (User u : allUsers) {
            System.out.println("- " + u.getName() + " (" + u.getClass().getSimpleName() + ")");
            System.out.println("  Autenticación 'password123': " + u.authenticate("password123"));
        }
        System.out.println();

        // ========== SECCIÓN 4: FLUJO PRINCIPAL - ASIGNACIÓN DE TAXIS ==========
        System.out.println("=== 4. FLUJO PRINCIPAL: ASIGNACIÓN DE TAXIS ===");
        
        // Cliente solicita taxi
        if (user1 instanceof ClientUser) {
            ((ClientUser) user1).requestTaxi();
        }

        // Buscar taxi más cercano usando el servicio
        Taxi nearestTaxi = taxiService.findNearestAvailableTaxi(
            user1.getLatitude(), 
            user1.getLongitude()
        );

        if (nearestTaxi != null) {
            System.out.println("Taxi más cercano encontrado: " + nearestTaxi.getDriverName());
            System.out.println("Distancia: " + 
                String.format("%.2f", nearestTaxi.calculateDistance(user1.getLatitude(), user1.getLongitude())) + 
                " km");
            
            // Asignar taxi al usuario
            boolean assigned = taxiService.assignTaxi(nearestTaxi, user1);
            if (assigned) {
                System.out.println("✓ Taxi asignado exitosamente");
            }
        }
        System.out.println();

        // ========== SECCIÓN 5: CREACIÓN DE TRAYECTORIAS ==========
        System.out.println("=== 5. GESTIÓN DE TRAYECTORIAS ===");
        
        // Crear trayectoria usando el servicio
        Trajectory trajectory1 = new Trajectory(
            "TR001",
            user1.getLatitude(), user1.getLongitude(),
            -12.0564, -77.0628,
            nearestTaxi,
            user1
        );

        trajectoryService.createTrajectory(trajectory1);
        System.out.println("Trayectoria creada: " + trajectory1);
        System.out.println("Distancia: " + String.format("%.2f", trajectory1.getDistance()) + " km");
        System.out.println("Duración estimada: " + trajectory1.getDuration() + " minutos");
        System.out.println();

        // Crear segunda trayectoria
        Taxi taxi2 = taxiService.findTaxiById("T002");
        if (taxi2 != null && taxi2.isAvailable()) {
            Trajectory trajectory2 = new Trajectory(
                "TR002",
                user2.getLatitude(), user2.getLongitude(),
                -12.0464, -77.0428,
                taxi2,
                user2
            );
            trajectoryService.createTrajectory(trajectory2);
            taxiService.assignTaxi(taxi2, user2);
            System.out.println("Segunda trayectoria creada: " + trajectory2);
        }
        System.out.println();

        // ========== SECCIÓN 6: CONSULTAS Y REPORTES ==========
        System.out.println("=== 6. CONSULTAS Y REPORTES ===");
        
        // Consultar trayectorias por usuario (uso de Map para agrupación)
        List<Trajectory> userTrajectories = trajectoryService.getTrajectoriesByUser(1L);
        System.out.println("Trayectorias del usuario " + user1.getName() + ": " + userTrajectories.size());
        double totalDistance = trajectoryService.getTotalDistanceByUser(1L);
        System.out.println("Distancia total recorrida: " + String.format("%.2f", totalDistance) + " km");
        System.out.println();

        // Consultar trayectorias por taxi
        List<Trajectory> taxiTrajectories = trajectoryService.getTrajectoriesByTaxi("T001");
        System.out.println("Trayectorias del taxi T001: " + taxiTrajectories.size());
        System.out.println();

        // Reporte de administrador
        if (admin1 instanceof AdminUser) {
            System.out.println("=== Reporte del Administrador ===");
            System.out.println(((AdminUser) admin1).generateReport());
            System.out.println("Total de taxis: " + taxiService.getAllTaxis().size());
            System.out.println("Total de usuarios: " + userService.getUserCount());
            System.out.println("Total de trayectorias: " + trajectoryService.getTrajectoryCount());
        }
        System.out.println();

        // ========== SECCIÓN 7: DEMOSTRACIÓN DE COLECCIONES ==========
        System.out.println("=== 7. USO DE COLECCIONES (List, Map, Set) ===");
        System.out.println("✓ List: Usado en todos los servicios para mantener orden");
        System.out.println("✓ Map: Usado para búsqueda rápida por ID, email, placa");
        System.out.println("✓ Set: Usado para mantener valores únicos (emails, placas, IDs)");
        System.out.println("✓ Genéricos: Todas las colecciones usan genéricos <Tipo>");
        System.out.println();

        System.out.println("=== Sistema funcionando correctamente al 60% ===");
    }
}
