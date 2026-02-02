package model;

/**
 * Clase que representa un usuario administrador del sistema
 * 
 * @author marionzuloagazapana
 */

public class Adminuser extends User {
    private String role;

/**
 * Constructor de la clase AdminUser
 * @param id identificador único del administrador
 * @param name nombre del administrador
 * @param email correo electrónico del administrador
 * @param role rol del administrador (ej: "Super Admin", "Manager", etc.)
 */
public AdminUser(Long id, String name, String email, String role) {
    super(id, name, email);  // Llama al constructor de la clase padre
    this.role = role;
}

public String getRole(){
    return role;
}

    public void setRole(String role) {
        this.role = role;
    }

/**
 * @param password será la contraseña del usuario  averifica si es correcta
 * @return sera true si la autenticacionresulta exitosa, false en caso contrario
 */

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
    // Aquí podrías agregar más validaciones específicas para admin
    // Por ahora, retornamos true si cumple el mínimo
    return true;
}
