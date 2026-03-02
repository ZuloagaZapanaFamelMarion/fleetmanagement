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
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Map;
import java.util.HashMap;
import service.PersistenceService;
import service.PersistenceService.LoadedState;
import java.io.File;
import java.io.IOException;

/**
 * Clase principal del sistema de gestiÃ³n de taxis
 * Demuestra la integraciÃ³n completa del sistema al 60%
 *
 * @author marionzuloagazapana
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // =========================================================
    // AUTH TEMPORAL (SIN BD): email -> password (en memoria)
    // =========================================================
    // Nota: Esto es solo para la demo en consola. En un proyecto real
    // se guarda hash en BD y se maneja sesión/token.
    private static final Map<String, String> PASSWORDS_BY_EMAIL = new HashMap<>();

    public static void main(String[] args) {
        //System.out.println("=== Sistema de GestiÃ³n de Taxis - Avance 60% ===\n");

        // Inicializar servicios (uso de colecciones: List, Map, Set)
        TaxiService taxiService = new TaxiService();
        UserService userService = new UserService();
        TrajectoryService trajectoryService = new TrajectoryService();

        seedData(taxiService, userService);

        /*
        System.out.println("=== Sistema de Gestión de Taxis (Consola) ===");

        // ========== SECCIÃ“N 1: REGISTRO DE TAXIS ==========
        System.out.println("=== 1. REGISTRO DE TAXIS ===");

        // CreaciÃ³n de taxis usando polimorfismo (referencias de tipo Vehicle)
        Vehicle vehicle1 = new Taxi("T001", "ABC-123", "Toyota", "Corolla", 2020, "Juan PÃ©rez");
        ((Taxi) vehicle1).setLocation(-12.0464, -77.0428); // Lima, PerÃº

        Vehicle vehicle2 = new Taxi("T002", "XYZ-456", "Nissan", "Sentra", 2021, "MarÃ­a GarcÃ­a");
        ((Taxi) vehicle2).setLocation(-12.0564, -77.0528);

        Vehicle vehicle3 = new Taxi("T003", "DEF-789", "Hyundai", "Elantra", 2022, "Carlos RodrÃ­guez");
        ((Taxi) vehicle3).setLocation(-12.0664, -77.0628);

        // Registrar taxis usando el servicio (usa Map y Set internamente)
        taxiService.registerTaxi((Taxi) vehicle1);
        taxiService.registerTaxi((Taxi) vehicle2);
        taxiService.registerTaxi((Taxi) vehicle3);

        System.out.println("Taxis registrados: " + taxiService.getAllTaxis().size());
        System.out.println("Taxis disponibles: " + taxiService.getAvailableTaxis().size());
        System.out.println();

        // DemostraciÃ³n de bÃºsqueda por ID y placa (uso de Map)
        Taxi foundTaxi = taxiService.findTaxiById("T001");
        System.out.println("Taxi encontrado por ID 'T001': " + foundTaxi.getDriverName());
        Taxi foundByPlate = taxiService.findTaxiByLicensePlate("XYZ-456");
        System.out.println("Taxi encontrado por placa 'XYZ-456': " + foundByPlate.getDriverName());
        System.out.println();

        // ========== SECCIÃ“N 2: REGISTRO DE USUARIOS ==========
        System.out.println("=== 2. REGISTRO DE USUARIOS ===");

        // Crear usuarios usando polimorfismo (referencias de tipo User)
        User user1 = new ClientUser(1L, "Carlos LÃ³pez", "carlos@email.com", "987654321");
        user1.setLocation(-12.0364, -77.0328);

        User user2 = new ClientUser(2L, "Ana MartÃ­nez", "ana@email.com", "987654322");
        user2.setLocation(-12.0664, -77.0628);

        User admin1 = new AdminUser(3L, "Pedro Admin", "admin@taxi.com", "Super Admin");
        admin1.setLocation(-12.0500, -77.0500);

        // Registrar usuarios usando el servicio (usa Map y Set para emails Ãºnicos)
        userService.registerUser(user1);
        userService.registerUser(user2);
        userService.registerUser(admin1);

        System.out.println("Usuarios registrados: " + userService.getUserCount());
        System.out.println("Clientes: " + userService.getClientUsers().size());
        System.out.println("Administradores: " + userService.getAdminUsers().size());
        System.out.println();

        // DemostraciÃ³n de bÃºsqueda por ID (uso de Map)
        User foundUser = userService.findUserById(1L);
        System.out.println("Usuario encontrado por ID '1': " + foundUser.getName());
        User foundByEmail = userService.findUserByEmail("ana@email.com");
        System.out.println("Usuario encontrado por email 'ana@email.com': " + foundByEmail.getName());
        System.out.println();

        // ========== SECCIÃ“N 3: DEMOSTRACIÃ“N DE POLIMORFISMO ==========
        System.out.println("=== 3. DEMOSTRACIÃ“N DE POLIMORFISMO ===");

        // Polimorfismo con Vehicles
        System.out.println("\n--- Polimorfismo con Vehicles ---");
        Vehicle[] vehicles = {(Vehicle) vehicle1, (Vehicle) vehicle2, (Vehicle) vehicle3};
        for (Vehicle v : vehicles) {
            System.out.println("- Tipo: " + v.getVehicleType() +
                             " | ID: " + v.getId() +
                             " | Placa: " + v.getLicensePlate());
            v.displayInfo(); // MÃ©todo polimÃ³rfico
        }

        // Polimorfismo con Users
        System.out.println("\n--- Polimorfismo con Users ---");
        List<User> allUsers = userService.getAllUsers();
        for (User u : allUsers) {
            System.out.println("- " + u.getName() + " (" + u.getClass().getSimpleName() + ")");
            System.out.println("  AutenticaciÃ³n 'password123': " + u.authenticate("password123"));
        }
        System.out.println();

        // ========== SECCIÃ“N 4: FLUJO PRINCIPAL - ASIGNACIÃ“N DE TAXIS ==========
        System.out.println("=== 4. FLUJO PRINCIPAL: ASIGNACIÃ“N DE TAXIS ===");

        // Cliente solicita taxi
        if (user1 instanceof ClientUser) {
            ((ClientUser) user1).requestTaxi();
        }

        // Buscar taxi mÃ¡s cercano usando el servicio
        Taxi nearestTaxi = taxiService.findNearestAvailableTaxi(
            user1.getLatitude(),
            user1.getLongitude()
        );

        if (nearestTaxi != null) {
            System.out.println("Taxi mÃ¡s cercano encontrado: " + nearestTaxi.getDriverName());
            System.out.println("Distancia: " +
                String.format("%.2f", nearestTaxi.calculateDistance(user1.getLatitude(), user1.getLongitude())) +
                " km");

            // Asignar taxi al usuario
            boolean assigned = taxiService.assignTaxi(nearestTaxi, user1);
            if (assigned) {
                System.out.println("âœ“ Taxi asignado exitosamente");
            }
        }
        System.out.println();

        // ========== SECCIÃ“N 5: CREACIÃ“N DE TRAYECTORIAS ==========
        System.out.println("=== 5. GESTIÃ“N DE TRAYECTORIAS ===");

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
        System.out.println("DuraciÃ³n estimada: " + trajectory1.getDuration() + " minutos");
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

        // ========== SECCIÃ“N 6: CONSULTAS Y REPORTES ==========
        System.out.println("=== 6. CONSULTAS Y REPORTES ===");

        // Consultar trayectorias por usuario (uso de Map para agrupaciÃ³n)
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

        // ========== SECCIÃ“N 7: DEMOSTRACIÃ“N DE COLECCIONES ==========
        System.out.println("=== 7. USO DE COLECCIONES (List, Map, Set) ===");
        System.out.println("âœ“ List: Usado en todos los servicios para mantener orden");
        System.out.println("âœ“ Map: Usado para bÃºsqueda rÃ¡pida por ID, email, placa");
        System.out.println("âœ“ Set: Usado para mantener valores Ãºnicos (emails, placas, IDs)");
        System.out.println("âœ“ GenÃ©ricos: Todas las colecciones usan genÃ©ricos <Tipo>");
        System.out.println();

        System.out.println("=== Sistema funcionando correctamente al 60% ===");
        */

        // =========================================================
        // MENÚ PRINCIPAL (dinámico): login / registro / uso del sistema
        // =========================================================
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Registrarme como Cliente");
            System.out.println("0) Salir");
            int opt = readInt("Opción: ");

            switch (opt) {
                case 1 -> loginFlow(userService, taxiService, trajectoryService);
                case 2 -> registerClientFlow(userService, taxiService);
                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ---------------- FLUJOS PRINCIPALES ----------------

    /**
     * Login real (demo):
     * 1) valida que el password coincida con lo registrado en el Map (AUTH temporal)
     * 2) valida reglas del rol (User.authenticate), ej: cliente >=6, admin >=8
     */
    private static void loginFlow(UserService userService, TaxiService taxiService, TrajectoryService trajectoryService) {
        System.out.println("\n--- LOGIN ---");
        String email = readString("Email: ");
        String password = readString("Password: ");

        // 1) Buscar usuario por email
        User candidate = userService.findUserByEmail(email);
        if (candidate == null) {
            System.out.println("Credenciales inválidas (email no existe).");
            return;
        }

        // 2) Comparar password con lo guardado al registrarse
        String storedPass = PASSWORDS_BY_EMAIL.get(email);
        if (storedPass == null || !storedPass.equals(password)) {
            System.out.println("Credenciales inválidas (password incorrecto).");
            return;
        }

        // 3) Reglas por rol (método polimórfico)
        if (!candidate.authenticate(password)) {
            System.out.println("Credenciales inválidas (reglas de seguridad no cumplidas).");
            return;
        }

        User logged = candidate;
        System.out.println("Bienvenido/a: " + logged.getName() + " (" + logged.getClass().getSimpleName() + ")");

        if (logged instanceof AdminUser admin) {
            adminMenu(admin, userService, taxiService, trajectoryService);
        } else if (logged instanceof ClientUser client) {
            clientMenu(client, userService, taxiService, trajectoryService);
        } else {
            System.out.println("Rol no reconocido.");
        }
    }

    /**
     * Registro de cliente:
     * - ID se autogenera (correlativo)
     * - se pide password y se guarda temporalmente en Map (AUTH)
     * - ubicación se asigna random cerca de taxis (para no pedir lat/lon manual)
     */
    private static void registerClientFlow(UserService userService, TaxiService taxiService) {
        System.out.println("\n--- REGISTRO CLIENTE ---");

        // ID autogenerado correlativo
        Long id = generateNextUserId(userService);

        String name = readString("Nombre: ");
        String email = readString("Email: ");
        String phone = readString("Teléfono: ");

        // Password para poder loguearse
        String pass1 = readString("Password: ");
        String pass2 = readString("Confirmar Password: ");
        if (!pass1.equals(pass2)) {
            System.out.println("No coincide el password. Registro cancelado.");
            return;
        }

        ClientUser client = new ClientUser(id, name, email, phone);

        //  Ubicación RANDOM cerca de taxis registrados
        double[] loc = randomNearAnyTaxi(taxiService, -12.0464, -77.0428);
        client.setLocation(loc[0], loc[1]);
        System.out.printf("Ubicación asignada cerca de taxis: %.6f, %.6f%n", loc[0], loc[1]);

        // Registrar usuario (valida email único con Set en UserService)
        boolean ok = userService.registerUser(client);
        if (ok) {
            // Guardar password en AUTH temporal
            PASSWORDS_BY_EMAIL.put(email, pass1);

            System.out.println("Registro exitoso. Tu ID generado es: " + id);
            System.out.println("Ya puedes iniciar sesión.");
        } else {
            System.out.println("No se pudo registrar (email duplicado o datos inválidos).");
        }
    }

    // ---------------- MENÚS POR ROL ----------------

    private static void clientMenu(ClientUser client, UserService userService, TaxiService taxiService, TrajectoryService trajectoryService) {
        while (true) {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1) Actualizar mi ubicación");
            System.out.println("2) Pedir taxi");
            System.out.println("3) Ver mis trayectorias");
            System.out.println("4) Ver mi distancia total");
            System.out.println("0) Cerrar sesión");
            int opt = readInt("Opción: ");

            switch (opt) {
                case 1 -> {
                    double lat = readDouble("Nueva latitud: ");
                    double lon = readDouble("Nueva longitud: ");
                    client.setLocation(lat, lon);
                    System.out.println("Ubicación actualizada.");
                }
                case 2 -> requestTaxiFlow(client, taxiService, trajectoryService);
                case 3 -> {
                    List<Trajectory> list = trajectoryService.getTrajectoriesByUser(client.getId());
                    System.out.println("Trayectorias: " + list.size());
                    for (Trajectory t : list) System.out.println("- " + t);
                }
                case 4 -> {
                    double total = trajectoryService.getTotalDistanceByUser(client.getId());
                    System.out.println("Distancia total: " + String.format("%.2f", total) + " km");
                }
                case 0 -> {
                    System.out.println("Sesión cerrada.");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void adminMenu(AdminUser admin, UserService userService, TaxiService taxiService, TrajectoryService trajectoryService) {
        while (true) {
            System.out.println("\n--- MENÚ ADMIN ---");
            System.out.println("1) Registrar taxi");
            System.out.println("2) Listar taxis");
            System.out.println("3) Liberar taxi (poner disponible)");
            System.out.println("4) Reporte del sistema");
            System.out.println("0) Cerrar sesión");
            int opt = readInt("Opción: ");

            switch (opt) {
                case 1 -> registerTaxiFlow(taxiService);
                case 2 -> {
                    List<Taxi> taxis = taxiService.getAllTaxis();
                    System.out.println("Total taxis: " + taxis.size());
                    for (Taxi t : taxis) {
                        System.out.println("- " + t.getId() + " | " + t.getLicensePlate() + " | " + t.getDriverName()
                                + " | disponible=" + t.isAvailable()
                                + " | loc=(" + String.format("%.6f", t.getLatitude()) + "," + String.format("%.6f", t.getLongitude()) + ")");
                    }
                }
                case 3 -> {
                    String id = readString("ID del taxi a liberar: ");
                    Taxi taxi = taxiService.findTaxiById(id);
                    if (taxi == null) {
                        System.out.println("No existe ese taxi.");
                    } else {
                        taxiService.releaseTaxi(taxi);
                        System.out.println("Taxi liberado: " + taxi.getDriverName());
                    }
                }
                case 4 -> {
                    System.out.println("\n" + admin.generateReport());
                    System.out.println("Total usuarios: " + userService.getUserCount());
                    System.out.println("Total taxis: " + taxiService.getAllTaxis().size());
                    System.out.println("Total trayectorias: " + trajectoryService.getTrajectoryCount());
                }
                case 0 -> {
                    System.out.println("Sesión cerrada.");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ---------------- ACCIONES ----------------

    /**
     * Pedir taxi:
     * 1) usuario ingresa destino
     * 2) se muestra lista de taxis disponibles con distancia al cliente
     * 3) luego se asigna el más cercano (automático)
     * 4) se crea trayectoria
     * 5) al finalizar, se actualiza ubicación del cliente (queda en destino)
     *    y el taxi también se mueve a destino y se libera (para poder pedir otra vez)
     */
    private static void requestTaxiFlow(ClientUser client, TaxiService taxiService, TrajectoryService trajectoryService) {
        System.out.println("\n--- PEDIR TAXI ---");
        double destLat = readDouble("Latitud destino: ");
        double destLon = readDouble("Longitud destino: ");

        // 1) Mostrar taxis disponibles + distancia al cliente
        List<Taxi> available = taxiService.getAvailableTaxis();
        if (available.isEmpty()) {
            System.out.println("No hay taxis disponibles.");
            return;
        }

        System.out.println("\nTaxis disponibles (distancia al cliente):");
        for (Taxi t : available) {
            double d = t.calculateDistance(client.getLatitude(), client.getLongitude());
            System.out.println("- " + t.getId()
                    + " | " + t.getDriverName()
                    + " | placa=" + t.getLicensePlate()
                    + " | dist=" + String.format("%.2f", d) + " km");
        }

        // 2) Elegir el más cercano (automático, pero después de mostrar lista)
        Taxi nearest = taxiService.findNearestAvailableTaxi(client.getLatitude(), client.getLongitude());
        if (nearest == null) {
            System.out.println("No hay taxis disponibles.");
            return;
        }

        double distToClient = nearest.calculateDistance(client.getLatitude(), client.getLongitude());
        System.out.println("\nTaxi más cercano: " + nearest.getDriverName()
                + " (ID: " + nearest.getId()
                + ", distancia: " + String.format("%.2f", distToClient) + " km)");

        // 3) Asignar taxi al cliente
        boolean assigned = taxiService.assignTaxi(nearest, client);
        if (!assigned) {
            System.out.println("No se pudo asignar (ya no está disponible).");
            return;
        }

        // 4) Crear trayectoria (ID correlativo)
        String trId = "TR" + String.format("%03d", trajectoryService.getTrajectoryCount() + 1);
        Trajectory tr = new Trajectory(
                trId,
                client.getLatitude(), client.getLongitude(),
                destLat, destLon,
                nearest,
                client
        );
        trajectoryService.createTrajectory(tr);

        System.out.println("✅ Taxi asignado y trayectoria creada: " + trId);
        System.out.println("Distancia del viaje: " + String.format("%.2f", tr.getDistance())
                + " km | Duración estimada: " + tr.getDuration() + " min");

        // 5) Finalizar viaje: mover cliente a destino
        client.setLocation(destLat, destLon);

        // 6) Mover taxi a destino y liberarlo (para permitir nuevos viajes)
        nearest.setLocation(destLat, destLon);
        taxiService.releaseTaxi(nearest);

        System.out.println("✔ Viaje finalizado: ubicación del cliente actualizada al destino.");
        System.out.println("✔ Taxi quedó disponible nuevamente en el destino.");
    }

    /**
     * Registro de taxi por admin:
     * - se registran datos
     * - se setea ubicación inicial
     */
    private static void registerTaxiFlow(TaxiService taxiService) {
        System.out.println("\n--- REGISTRAR TAXI ---");
        String id = readString("ID taxi: ");
        String plate = readString("Placa: ");
        String brand = readString("Marca: ");
        String model = readString("Modelo: ");
        int year = readInt("Año: ");
        String driver = readString("Conductor: ");

        Taxi taxi = new Taxi(id, plate, brand, model, year, driver);

        double lat = readDouble("Latitud inicial: ");
        double lon = readDouble("Longitud inicial: ");
        taxi.setLocation(lat, lon);

        boolean ok = taxiService.registerTaxi(taxi);
        System.out.println(ok ? "Taxi registrado." : "No se pudo registrar (ID o placa duplicada).");
    }

    // ---------------- Datos iniciales ----------------

    /**
     * Datos iniciales para probar rápido sin registrar todo a mano.
     * También se inicializan passwords para poder loguearse.
     */
    private static void seedData(TaxiService taxiService, UserService userService) {
        Taxi t1 = new Taxi("T001", "ABC-123", "Toyota", "Corolla", 2020, "Juan Pérez");
        t1.setLocation(-12.0464, -77.0428);

        Taxi t2 = new Taxi("T002", "XYZ-456", "Nissan", "Sentra", 2021, "María García");
        t2.setLocation(-12.0564, -77.0528);

        taxiService.registerTaxi(t1);
        taxiService.registerTaxi(t2);

        AdminUser admin = new AdminUser(99L, "Admin", "admin@taxi.com", "Super Admin");
        admin.setLocation(-12.0500, -77.0500);
        userService.registerUser(admin);

        ClientUser c1 = new ClientUser(1L, "Carlos López", "carlos@email.com", "987654321");
        c1.setLocation(-12.0364, -77.0328);
        userService.registerUser(c1);

        // Passwords para login (DEMO)
        PASSWORDS_BY_EMAIL.put("admin@taxi.com", "admin12345");   // >= 8
        PASSWORDS_BY_EMAIL.put("carlos@email.com", "123456");     // >= 6
    }

    // ---------------- RANDOM GEO (OPCIÓN 2) ----------------

    /**
     * Genera ubicación cerca de un taxi aleatorio.
     * Si no hay taxis, usa fallback (centro) y genera cerca de esa coordenada.
     */
    private static double[] randomNearAnyTaxi(TaxiService taxiService, double fallbackLat, double fallbackLon) {
        List<Taxi> taxis = taxiService.getAllTaxis();
        if (taxis == null || taxis.isEmpty()) {
            return randomNear(fallbackLat, fallbackLon, 2.0);
        }
        Taxi base = taxis.get(ThreadLocalRandom.current().nextInt(taxis.size()));
        return randomNear(base.getLatitude(), base.getLongitude(), 1.0); // 1 km cerca del taxi
    }

    /**
     * Genera un punto aleatorio en un radio (km) alrededor del centro.
     * Aproximación simple para demo (1 grado ~ 111km).
     */
    private static double[] randomNear(double centerLat, double centerLon, double radiusKm) {
        double radiusInDegrees = radiusKm / 111.0;

        double u = ThreadLocalRandom.current().nextDouble();
        double v = ThreadLocalRandom.current().nextDouble();

        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;

        double dLat = w * Math.cos(t);
        double dLon = w * Math.sin(t) / Math.cos(Math.toRadians(centerLat));

        return new double[]{centerLat + dLat, centerLon + dLon};
    }

    // ---------------- ID AUTO (correlativo) ----------------

    /**
     * Genera ID correlativo (maxId + 1) basado en usuarios registrados.
     * Esto evita pedir ID manual en consola.
     */
    private static Long generateNextUserId(UserService userService) {
        long max = 0L;
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            if (u.getId() != null && u.getId() > max) {
                max = u.getId();
            }
        }
        return max + 1;
    }

    // ---------------- validaciones ----------------

    private static String readString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

    private static double readDouble(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Decimal inválido.");
            }
        }
    }
}