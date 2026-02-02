package model;

/**
 * Clase que representa un usuario administrador del sistema
 * @author marionzuloagazapana
 */
public class AdminUser extends User {
    
    private String role;
    
    /**
     * Constructor de la clase AdminUser
     * @param id identificador único del administrador
     * @param name nombre del administrador
     * @param email correo electrónico del administrador
     * @param role rol del administrador (ej: "Super Admin", "Manager", etc.)
     */
    public AdminUser(Long id, String name, String email, String role) {
        super(id, name, email);
        this.role = role;
    }
    
    // Getters y Setters
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * Implementación del método abstracto authenticate
     * Para administradores, la autenticación puede ser más estricta
     * @param password contraseña a verificar
     * @return true si la contraseña es válida, false en caso contrario
     */
    @Override
    public boolean authenticate(String password) {
        // Lógica de autenticación para administradores
        // Por ejemplo: contraseña debe tener al menos 8 caracteres
        if (password == null || password.length() < 8) {
            return false;
        }
        // Por ahora, retornamos true si cumple el mínimo
        return true;
    }
    
    /**
     * Genera un reporte del sistema (método específico de AdminUser)
     * @return reporte en formato String
     */
    public String generateReport() {
        return "Reporte generado por: " + getName() + 
               " (Rol: " + role + ")\n" +
               "Fecha: " + java.time.LocalDateTime.now() + "\n" +
               "Estado del sistema: Operativo";
    }
    
    @Override
    public String toString() {
        return "AdminUser{" + 
               "id=" + getId() + 
               ", name='" + getName() + '\'' + 
               ", email='" + getEmail() + '\'' + 
               ", role='" + role + '\'' + 
               ", location=(" + getLatitude() + ", " + getLongitude() + ")" + 
               '}';
    }
}
