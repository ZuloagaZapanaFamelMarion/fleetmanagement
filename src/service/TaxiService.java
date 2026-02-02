package service;

import model.Taxi;
import model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestionar operaciones relacionadas con taxis
 * @author marionzuloagazapana
 */
public class TaxiService {
    
    private List<Taxi> taxis;
    
    /**
     * Constructor del servicio
     */
    public TaxiService() {
        this.taxis = new ArrayList<>();
    }
    
    /**
     * Registra un nuevo taxi en el sistema
     * @param taxi taxi a registrar
     */
    public void registerTaxi(Taxi taxi) {
        taxis.add(taxi);
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
     * Libera un taxi (lo marca como disponible)
     * @param taxi taxi a liberar
     */
    public void releaseTaxi(Taxi taxi) {
        taxi.setAvailable(true);
    }
}
