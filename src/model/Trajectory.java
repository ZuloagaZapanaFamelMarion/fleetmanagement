package model;

/**
 * Clase que representa una trayectoria o ruta de un viaje
 * @author marionzuloagazapana
 */
public class Trajectory {
    
    private String id;
    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private double distance; // en kilómetros
    private int duration; // en minutos
    private Taxi taxi;
    private User user;
    
    /**
     * Constructor de la clase Trajectory
     * @param id identificador único de la trayectoria
     * @param originLatitude latitud del origen
     * @param originLongitude longitud del origen
     * @param destinationLatitude latitud del destino
     * @param destinationLongitude longitud del destino
     * @param taxi taxi asignado a esta trayectoria
     * @param user usuario que solicita el viaje
     */
    public Trajectory(String id, double originLatitude, double originLongitude, 
                     double destinationLatitude, double destinationLongitude, 
                     Taxi taxi, User user) {
        this.id = id;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.taxi = taxi;
        this.user = user;
        this.distance = calculateDistance();
        this.duration = estimateDuration();
    }
    
    /**
     * Calcula la distancia entre origen y destino
     * @return distancia en kilómetros
     */
    private double calculateDistance() {
        // Fórmula simplificada para cálculo de distancia
        double latDiff = Math.abs(originLatitude - destinationLatitude);
        double lonDiff = Math.abs(originLongitude - destinationLongitude);
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111; // Aproximación
    }
    
    /**
     * Estima la duración del viaje basado en la distancia
     * @return duración estimada en minutos
     */
    private int estimateDuration() {
        // Estimación: 2 minutos por kilómetro (promedio en ciudad)
        return (int) Math.ceil(distance * 2);
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public double getOriginLatitude() {
        return originLatitude;
    }
    
    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }
    
    public double getOriginLongitude() {
        return originLongitude;
    }
    
    public void setOriginLongitude(double originLongitude) {
        this.originLongitude = originLongitude;
    }
    
    public double getDestinationLatitude() {
        return destinationLatitude;
    }
    
    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }
    
    public double getDestinationLongitude() {
        return destinationLongitude;
    }
    
    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public Taxi getTaxi() {
        return taxi;
    }
    
    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Trajectory{" + 
               "id='" + id + '\'' + 
               ", origin=(" + originLatitude + ", " + originLongitude + ")" + 
               ", destination=(" + destinationLatitude + ", " + destinationLongitude + ")" + 
               ", distance=" + String.format("%.2f", distance) + " km" + 
               ", duration=" + duration + " min" + 
               '}';
    }
}
