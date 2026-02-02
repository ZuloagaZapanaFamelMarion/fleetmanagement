package model;

public class ClientUser extends User{
    
    private String phoneNumber;

    /**
 * Constructor de la clase ClientUser
 * @param id identificador único del cliente
 * @param name nombre del cliente
 * @param email correo electrónico del cliente
 * @param phoneNumber número de teléfono del cliente
 */
public ClientUser(Long id, String name, String email, String phoneNumber) {
    super(id, name, email);  // llamamos al constructor de la clase padre
    this.phoneNumber = phoneNumber;
}

public String getPhoneNumber(){
    return phoneNumber;
}

public void setPhoneNumber(String phoneNumber){
    this.phoneNumber = phoneNumber;
}

@Override
public boolean authenticate(String password){
   if (password == null || password.length() < 6){
    return false;
   }
   return true;
}
}
