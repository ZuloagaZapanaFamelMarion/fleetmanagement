package service;

import model.Taxi;
import model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Servicio para gestionar operaciones relacionadas con taxis
 * @author marionzuloagazapana
 */
public class TaxiService {
    
    // List para mantener orden de registro
    private List<Taxi> taxis;
    
    // Map para búsqueda rápida por ID
    private Map<String, Taxi> taxisById;
    
    // Map para búsqueda rápida por placa
    private Map<String, Taxi> taxisByLicensePlate;
    
    // Set para mantener placas únicas
    private Set<String> licensePlates;
    
    /**
     * Constructor del servicio
     */
    public TaxiService() {
        this.taxis = new ArrayList<>();
        this.taxisById = new HashMap<>();
        this.taxisByLicensePlate = new HashMap<>();
        this.licensePlates = new HashSet<>();
    }
    
    /**
     * Registra un nuevo taxi en el sistema
     * @param taxi taxi a registrar
     * @return true si se registró correctamente, false si el ID o placa ya existe
     */
    public boolean registerTaxi(Taxi taxi) {
        if (taxi == null || taxi.getId() == null || taxi.getLicensePlate() == null) {
            return false;
        }
        
        // Verificar que el ID y la placa no estén duplicados
        if (taxisById.containsKey(taxi.getId()) || licensePlates.contains(taxi.getLicensePlate())) {
            return false;
        }
        
        taxis.add(taxi);
        taxisById.put(taxi.getId(), taxi);
        taxisByLicensePlate.put(taxi.getLicensePlate(), taxi);
        licensePlates.add(taxi.getLicensePlate());
        return true;
    }
    
    /**
     * Busca un taxi por su ID
     * @param id identificador del taxi
     * @return taxi encontrado o null si no existe
     */
    public Taxi findTaxiById(String id) {
        return taxisById.get(id);
    }
    
    /**
     * Busca un taxi por su placa
     * @param licensePlate placa del taxi
     * @return taxi encontrado o null si no existe
     */
    public Taxi findTaxiByLicensePlate(String licensePlate) {
        return taxisByLicensePlate.get(licensePlate);
    }
    
    /**
     * Busca un taxi disponible cercano a una ubicación
     * @param latitude latitud del punto de referencia
     * @param longitude longitud del punto de referencia
     * @return taxi más cercano disponible, o null si no hay taxis disponibles
     */
    public Taxi findNearestAvailableTaxi(double latitude, double longitude) {
        Taxi nearestTaxi = null;
        double minDistance = Double.MAX_VALUE;
        
        for (Taxi taxi : taxis) {
            if (taxi.isAvailable()) {
                double distance = taxi.calculateDistance(latitude, longitude);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestTaxi = taxi;
                }
            }
        }
        
        return nearestTaxi;
    }
    
    /**
     * Obtiene todos los taxis disponibles
     * @return lista de taxis disponibles
     */
    public List<Taxi> getAvailableTaxis() {
        List<Taxi> available = new ArrayList<>();
        for (Taxi taxi : taxis) {
            if (taxi.isAvailable()) {
                available.add(taxi);
            }
        }
        return available;
    }
    
    /**
     * Obtiene todos los taxis registrados
     * @return lista de todos los taxis
     */
    public List<Taxi> getAllTaxis() {
        return new ArrayList<>(taxis);
    }
    
    /**
     * Asigna un taxi a un usuario
     * @param taxi taxi a asignar
     * @param user usuario que solicita el taxi
     * @return true si la asignación fue exitosa, false si el taxi no está disponible
     */
    public boolean assignTaxi(Taxi taxi, User user) {
        if (taxi.isAvailable()) {
            taxi.setAvailable(false);
            taxi.setLocation(user.getLatitude(), user.getLongitude());
            return true;
        }
        return false;
    }
    
    /**
     * se libera un taxi (se marcará como disponible)
     * @param taxi taxi a liberar
     */
    public void releaseTaxi(Taxi taxi) {
        taxi.setAvailable(true);
    }
}
