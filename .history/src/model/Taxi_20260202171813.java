package model;

/**
 * Clase que representa un taxi, hereda de Vehicle e implementa IGeolocalizable
 * @author marionzuloagazapana
 */
public class Taxi extends Vehicle implements IGeolocalizable {
    
    private String driverName;
    private boolean available;
    private double latitude;
    private double longitude;
    
    /**
     * Constructor de la clase Taxi
     * @param id identificador único del taxi
     * @param licensePlate placa del taxi
     * @param brand marca del taxi
     * @param model modelo del taxi
     * @param year año del taxi
     * @param driverName nombre del conductor
     */
    public Taxi(String id, String licensePlate, String brand, String model, int year, String driverName) {
        super(id, licensePlate, brand, model, year);
        this.driverName = driverName;
        this.available = true;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
    
    // Implementación de IGeolocalizable
    @Override
    public double getLatitude() {
        return latitude;
    }
    
    @Override
    public double getLongitude() {
        return longitude;
    }
    
    @Override
    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    // Getters y Setters específicos de Taxi
    public String getDriverName() {
        return driverName;
    }
    
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    /**
     * Calcula la distancia entre el taxi y un punto dado
     * @param lat latitud del punto destino
     * @param lon longitud del punto destino
     * @return distancia aproximada en kilómetros (fórmula de Haversine simplificada)
     */
    public double calculateDistance(double lat, double lon) {
        // Fórmula simplificada para cálculo de distancia
        // En producción, usar fórmula de Haversine completa
        double latDiff = Math.abs(this.latitude - lat);
        double lonDiff = Math.abs(this.longitude - lon);
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111; // Aproximación
    }
    
    @Override
    public String toString() {
        return "Taxi{" + 
               "id='" + id + '\'' + 
               ", licensePlate='" + licensePlate + '\'' + 
               ", driverName='" + driverName + '\'' + 
               ", available=" + available + 
               ", location=(" + latitude + ", " + longitude + ")" + 
               '}';
    }
}
