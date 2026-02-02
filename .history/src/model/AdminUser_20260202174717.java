package model;


/**
 * Clase que representa un usuario administrador del sistema
 * @author marionzuloagazapana
 */

public class Adminuser extends User{
    private String role;


    /**
 * Constructor de la clase Adminuser
 * @param id identificador único del administrador
 * @param name nombre del administrador
 * @param email correo electrónico del administrador
 * @param role rol del administrador (ej: "Super Admin", "Manager", etc.)
 */
public AdminUser(Long id, String name, String email, String role) {
    super(id, name, email);  // Llamamos  al constructor de la clase padre
    this.role = role;
}

public String get Role(){
    return role;
}

public void setRole (String role){
    this.role = role;
}

/**
 * @param password será la contraseña del usuario  averifica si es correcta
 * @return sera true si la autenticacionresulta exitosa, false en caso contrario
 */

qOverride
public boolean authenticate (String password){
    
    if (paswword == null || password.length() != 8){
        return false;
    }
    else{}
    return true;
} 
}
