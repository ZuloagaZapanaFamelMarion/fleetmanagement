package model;

/**
 * Interfaz para objetos que pueden ser geolocalizados
 * @author marionzuloagazapana
 */
public interface IGeolocalizable {
    
    /**
     * Obtiene la latitud del objeto
     * @return latitud en grados decimales
     */
    double getLatitude();
    
    /**
     * Obtiene la longitud del objeto
     * @return longitud en grados decimales
     */
    double getLongitude();
    
    /**
     * Establece la ubicación del objeto
     * @param latitude latitud en grados decimales
     * @param longitude longitud en grados decimales
     */
    void setLocation(double latitude, double longitude);
}
