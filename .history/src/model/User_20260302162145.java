package model;
import java.io.Serializable;

/**
 * Clase abstracta que representa un usuario del sistema
 * y que además es geolocalizable e identificable.
 * 
 * Se declara como sealed para restringir las subclases permitidas.
 * También implementa Identifiable para usarla como cota en tipos genéricos.
 * 
 * @author marionzuloagazapana
 */
public sealed abstract class User implements IGeolocalizable, Identifiable
        permits AdminUser, ClientUser {
    
    private Long id;
    private String name;
    private String email;
    private double latitude;
    private double longitude;
    
    /**
     * Constructor de la clase User
     * @param id identificador único del usuario
     * @param name nombre del usuario
     * @param email correo electrónico del usuario
     */
    public User(Long id, String name, String email ) {
        this.id = id;
        this.name = name;
        this.email = email;
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
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    /** 
    @param password contraseña del usuario
    @return sera true si la autenticacionresulta exitosa, false en caso contrario
    */
   public abstract boolean authenticate(String password);

    
    @Override
    public String toString() {
        return "User{" + 
               "id='" + id + '\'' + 
               ", name='" + name + '\'' + 
               ", email='" + email + '\'' + 
               ", location=(" + latitude + ", " + longitude + ")" + 
               '}';
    }
}
