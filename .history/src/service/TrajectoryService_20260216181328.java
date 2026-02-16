package service;

import model.Trajectory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Servicio para gestionar operaciones relacionadas con trayectorias
 * @author marionzuloagazapana
 */
public class TrajectoryService {
    
    // Map para búsqueda rápida por ID de trayectoria
    private Map<String, Trajectory> trajectoriesById;
    
    // Map para agrupar trayectorias por usuario
    private Map<Long, List<Trajectory>> trajectoriesByUser;
    
    // Map para agrupar trayectorias por taxi
    private Map<String, List<Trajectory>> trajectoriesByTaxi;
    
    // Set para mantener IDs únicos de trayectorias
    private Set<String> trajectoryIds;
    
    // List para mantener orden de creación
    private List<Trajectory> trajectoriesList;
    
    /**
     * Constructor del servicio
     */
    public TrajectoryService() {
        this.trajectoriesById = new HashMap<>();
        this.trajectoriesByUser = new HashMap<>();
        this.trajectoriesByTaxi = new HashMap<>();
        this.trajectoryIds = new HashSet<>();
        this.trajectoriesList = new ArrayList<>();
    }
    
    /**
     * Crea y registra una nueva trayectoria
     * @param trajectory trayectoria a registrar
     * @return true si se registró correctamente, false si el ID ya existe
     */
    public boolean createTrajectory(Trajectory trajectory) {
        if (trajectory == null || trajectory.getId() == null) {
            return false;
        }
        
        // Verificar que el ID no esté duplicado
        if (trajectoryIds.contains(trajectory.getId())) {
            return false;
        }
        
        trajectoriesById.put(trajectory.getId(), trajectory);
        trajectoryIds.add(trajectory.getId());
        trajectoriesList.add(trajectory);
        
        // Agrupar por usuario
        Long userId = trajectory.getUser().getId();
        trajectoriesByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(trajectory);
        
        // Agrupar por taxi
        String taxiId = trajectory.getTaxi().getId();
        trajectoriesByTaxi.computeIfAbsent(taxiId, k -> new ArrayList<>()).add(trajectory);
        
        return true;
    }
    
    /**
     * Busca una trayectoria por su ID
     * @param id identificador de la trayectoria
     * @return trayectoria encontrada o null si no existe
     */
    public Trajectory findTrajectoryById(String id) {
        return trajectoriesById.get(id);
    }
    
    /**
     * Obtiene todas las trayectorias de un usuario
     * @param userId identificador del usuario
     * @return lista de trayectorias del usuario
     */
    public List<Trajectory> getTrajectoriesByUser(Long userId) {
        List<Trajectory> trajectories = trajectoriesByUser.get(userId);
        return trajectories != null ? new ArrayList<>(trajectories) : new ArrayList<>();
    }
    
    /**
     * Obtiene todas las trayectorias de un taxi
     * @param taxiId identificador del taxi
     * @return lista de trayectorias del taxi
     */
    public List<Trajectory> getTrajectoriesByTaxi(String taxiId) {
        List<Trajectory> trajectories = trajectoriesByTaxi.get(taxiId);
        return trajectories != null ? new ArrayList<>(trajectories) : new ArrayList<>();
    }
    
    /**
     * Obtiene todas las trayectorias registradas
     * @return lista de todas las trayectorias
     */
    public List<Trajectory> getAllTrajectories() {
        return new ArrayList<>(trajectoriesList);
    }
    
    /**
     * Calcula la distancia total recorrida por un usuario
     * @param userId identificador del usuario
     * @return distancia total en kilómetros
     */
    public double getTotalDistanceByUser(Long userId) {
        double total = 0.0;
        List<Trajectory> trajectories = getTrajectoriesByUser(userId);
        for (Trajectory trajectory : trajectories) {
            total += trajectory.getDistance();
        }
        return total;
    }
    
    /**
     * Calcula la distancia total recorrida por un taxi
     * @param taxiId identificador del taxi
     * @return distancia total en kilómetros
     */
    public double getTotalDistanceByTaxi(String taxiId) {
        double total = 0.0;
        List<Trajectory> trajectories = getTrajectoriesByTaxi(taxiId);
        for (Trajectory trajectory : trajectories) {
            total += trajectory.getDistance();
        }
        return total;
    }
    
    /**
     * Obtiene el número total de trayectorias registradas
     * @return cantidad de trayectorias
     */
    public int getTrajectoryCount() {
        return trajectoriesList.size();
    }
}
