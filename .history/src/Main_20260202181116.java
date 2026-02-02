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
        System.out.println();

        // Crear trayectorias
        Trajectory trajectory1 = new Trajectory(
                "TR001",
                user1.getLatitude(), user1.getLongitude(),
                -12.0564, -77.0628,
                taxi1,
                user1);

        System.out.println("Trayectoria creada:");
        System.out.println(trajectory1);
        System.out.println();

        // Simular búsqueda de taxi cercano
        System.out.println("Buscando taxi cercano para usuario " + user1.getName() + ":");
        double distance1 = taxi1.calculateDistance(user1.getLatitude(), user1.getLongitude());
        double distance2 = taxi2.calculateDistance(user1.getLatitude(), user1.getLongitude());

        System.out.println("Distancia desde " + taxi1.getDriverName() + ": " +
                String.format("%.2f", distance1) + " km");
        System.out.println("Distancia desde " + taxi2.getDriverName() + ": " +
                String.format("%.2f", distance2) + " km");

        if (distance1 < distance2) {
            System.out.println("Taxi asignado: " + taxi1.getDriverName());
        } else {
            System.out.println("Taxi asignado: " + taxi2.getDriverName());
        }
    }
}
