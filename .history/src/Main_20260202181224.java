import model.Taxi;
import model.Vehicle;
import model.User;
import model.ClientUser;
import model.AdminUser;
import model.Trajectory;

/**
 * Clase principal del sistema de gestión de taxis
 * 
 * @author marionzuloagazapana
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestión de Taxis ===\n");

        // Creación de algunos  taxis usando referencias de tipo Vehicle (POLIMORFISMO)
        Vehicle vehicle1 = new Taxi("T001", "ABC-123", "Toyota", "Corolla", 2020, "Juan Pérez");
        ((Taxi) vehicle1).setLocation(-12.0464, -77.0428); // Lima, Perú

        Vehicle vehicle2 = new Taxi("T002", "XYZ-456", "Nissan", "Sentra", 2021, "María García");
        ((Taxi) vehicle2).setLocation(-12.0564, -77.0528);

        System.out.println("Taxis registrados:");
        System.out.println(vehicle1);
        System.out.println(vehicle2);
        System.out.println();

        // Crear usuarios y un administrador
        User user1 = new ClientUser(1L, "Carlos López", "carlos@email.com", "987654321");
        user1.setLocation(-12.0364, -77.0328);
        
        User user2 = new ClientUser(2L, "Ana Martínez", "ana@email.com", "987654322");
        user2.setLocation(-12.0664, -77.0628);
        
        User admin1 = new AdminUser(3L, "Pedro Admin", "admin@taxi.com", "Super Admin");
        admin1.setLocation(-12.0500, -77.0500);

        System.out.println("Usuarios registrados:");
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(admin1);
        System.out.println();

        // DEMOSTRACIÓN DE POLIMORFISMO CON VEHICLES
        System.out.println("=== Demostración de Polimorfismo con Vehicles ===");
        System.out.println("Tipo de vehículo 1: " + vehicle1.getVehicleType());
        System.out.println("Tipo de vehículo 2: " + vehicle2.getVehicleType());
        System.out.println("\nInformación de vehículos (método polimórfico):");
        vehicle1.displayInfo();
        vehicle2.displayInfo();
        System.out.println();

        // DEMOSTRACIÓN DE POLIMORFISMO CON USERS
        System.out.println("=== Demostración de Polimorfismo con Users ===");
        System.out.println("Autenticación de usuarios (método polimórfico):");
        System.out.println("Cliente 1 - Contraseña '12345': " + user1.authenticate("12345")); // false (menos de 6)
        System.out.println("Cliente 1 - Contraseña '123456': " + user1.authenticate("123456")); // true (6+)
        System.out.println("Cliente 2 - Contraseña 'abc123': " + user2.authenticate("abc123")); // true (6+)
        System.out.println("Admin 1 - Contraseña '1234567': " + admin1.authenticate("1234567")); // false (menos de 8)
        System.out.println("Admin 1 - Contraseña '12345678': " + admin1.authenticate("12345678")); // true (8+)
        System.out.println();

        // Demostrar métodos específicos de cada tipo
        System.out.println("=== Métodos Específicos por Tipo ===");
        if (user1 instanceof ClientUser) {
            ((ClientUser) user1).requestTaxi();
        }
        if (admin1 instanceof AdminUser) {
            System.out.println("\n" + ((AdminUser) admin1).generateReport());
        }
        System.out.println();

        // Crear trayectorias
        Trajectory trajectory1 = new Trajectory(
                "TR001",
                user1.getLatitude(), user1.getLongitude(),
                -12.0564, -77.0628,
                (Taxi) vehicle1,
                user1);

        System.out.println("Trayectoria creada:");
        System.out.println(trajectory1);
        System.out.println();

        // Simular búsqueda de taxi cercano
        System.out.println("Buscando taxi cercano para usuario " + user1.getName() + ":");
        Taxi t1 = (Taxi) vehicle1;  // Cast para acceder a métodos específicos de Taxi
        Taxi t2 = (Taxi) vehicle2;
        double distance1 = t1.calculateDistance(user1.getLatitude(), user1.getLongitude());
        double distance2 = t2.calculateDistance(user1.getLatitude(), user1.getLongitude());

        System.out.println("Distancia desde " + t1.getDriverName() + ": " +
                String.format("%.2f", distance1) + " km");
        System.out.println("Distancia desde " + t2.getDriverName() + ": " +
                String.format("%.2f", distance2) + " km");

        if (distance1 < distance2) {
            System.out.println("Taxi asignado: " + t1.getDriverName());
        } else {
            System.out.println("Taxi asignado: " + t2.getDriverName());
        }
        System.out.println();

        // DEMOSTRACIÓN DE POLIMORFISMO CON ARRAYS/COLECCIONES
        System.out.println("=== Polimorfismo con Arrays ===");
        Vehicle[] vehicles = {vehicle1, vehicle2};
        User[] users = {user1, user2, admin1};

        System.out.println("\nListando todos los vehículos:");
        for (Vehicle v : vehicles) {
            System.out.println("- " + v.getVehicleType() + " (ID: " + v.getId() + ", Placa: " + v.getLicensePlate() + ")");
        }

        System.out.println("\nListando todos los usuarios:");
        for (User u : users) {
            System.out.println("- " + u.getName() + " (" + u.getClass().getSimpleName() + ")");
            System.out.println("  Email: " + u.getEmail());
            System.out.println("  Autenticación con 'password123': " + u.authenticate("password123"));
        }
    }
}
