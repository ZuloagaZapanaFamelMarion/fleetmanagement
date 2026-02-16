package service;

import model.User;
import model.ClientUser;
import model.AdminUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Servicio para gestionar operaciones relacionadas con usuarios
 * @author marionzuloagazapana
 */
public class UserService {
    
    // Map para búsqueda rápida por ID
    private Map<Long, User> usersById;
    
    // Set para almacenar emails únicos (evitar duplicados)
    private Set<String> registeredEmails;
    
    // List para mantener orden de registro
    private List<User> usersList;
    
    /**
     * Constructor del servicio
     */
    public UserService() {
        this.usersById = new HashMap<>();
        this.registeredEmails = new HashSet<>();
        this.usersList = new ArrayList<>();
    }
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param user usuario a registrar
     * @return true si el registro fue exitoso, false si el email ya existe
     */
    public boolean registerUser(User user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }
        
        // Verificar que el email no esté duplicado
        if (registeredEmails.contains(user.getEmail())) {
            return false;
        }
        
        usersById.put(user.getId(), user);
        registeredEmails.add(user.getEmail());
        usersList.add(user);
        return true;
    }
    
    /**
     * Busca un usuario por su ID
     * @param id identificador del usuario
     * @return usuario encontrado o null si no existe
     */
    public User findUserById(Long id) {
        return usersById.get(id);
    }
    
    /**
     * Busca un usuario por su email
     * @param email email del usuario
     * @return usuario encontrado o null si no existe
     */
    public User findUserByEmail(String email) {
        for (User user : usersList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Obtiene todos los usuarios registrados
     * @return lista de todos los usuarios
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(usersList);
    }
    
    /**
     * Obtiene solo los usuarios clientes
     * @return lista de usuarios clientes
     */
    public List<ClientUser> getClientUsers() {
        List<ClientUser> clients = new ArrayList<>();
        for (User user : usersList) {
            if (user instanceof ClientUser) {
                clients.add((ClientUser) user);
            }
        }
        return clients;
    }
    
    /**
     * Obtiene solo los usuarios administradores
     * @return lista de usuarios administradores
     */
    public List<AdminUser> getAdminUsers() {
        List<AdminUser> admins = new ArrayList<>();
        for (User user : usersList) {
            if (user instanceof AdminUser) {
                admins.add((AdminUser) user);
            }
        }
        return admins;
    }
    
    /**
     * Autentica un usuario
     * @param email email del usuario
     * @param password contraseña a verificar
     * @return usuario autenticado o null si la autenticación falla
     */
    public User authenticateUser(String email, String password) {
        User user = findUserByEmail(email);
        if (user != null && user.authenticate(password)) {
            return user;
        }
        return null;
    }
    
    /**
     * Elimina un usuario del sistema
     * @param id identificador del usuario a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean removeUser(Long id) {
        User user = usersById.remove(id);
        if (user != null) {
            registeredEmails.remove(user.getEmail());
            usersList.remove(user);
            return true;
        }
        return false;
    }
    
    /**
     * Obtiene el número total de usuarios registrados
     * @return cantidad de usuarios
     */
    public int getUserCount() {
        return usersList.size();
    }
}
