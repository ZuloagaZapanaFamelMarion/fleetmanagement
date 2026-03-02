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
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones relacionadas con usuarios.
 * 
 * Implementa un repositorio genérico de entidades identificables (User),
 * demostrando el uso de tipos genéricos con bounded types (T extends Identifiable).
 * 
 * @author marionzuloagazapana
 */
public class UserService implements Repository<User> {
    
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

    // Implementación del contrato Repository<User>
    @Override
    public void save(User entity) {
        // Reutilizamos la lógica de registro para mantener las reglas de negocio
        registerUser(entity);
    }

    @Override
    public User findById(Long id) {
        return findUserById(id);
    }

    @Override
    public List<User> findAll() {
        return getAllUsers();
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
    //implementacion de stream para buscar un usuario por su email
    public User findUserByEmail(String email) {
        return usersList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
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
    //implementacion de stream para obtener los usuarios clientes
    public List<ClientUser> getClientUsers() {
        return usersList.stream()
                .filter(user -> user instanceof ClientUser)
                .map(user -> (ClientUser) user)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene solo los usuarios administradores
     * @return lista de usuarios administradores
     */
    //implementacion de stream para obtener los usuarios administradores
    public List<AdminUser> getAdminUsers() {
        return usersList.stream()
                .filter(user -> user instanceof AdminUser)
                .map(user -> (AdminUser) user)
                .collect(Collectors.toList());
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
