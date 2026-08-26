package co.unicauca.saberpro.users.domain.service;

import co.unicauca.saberpro.users.domain.Usuario;
import co.unicauca.saberpro.users.domain.access.IUsuarioRepository;
import java.security.MessageDigest;//generar funciones hash criptográficas de longitud fija a partir de datos de cualquier tamaño
import java.security.NoSuchAlgorithmException;//sirve para avisar que un programa pidió un algoritmo criptográfico que no está disponible en el entorno de Java
import java.util.regex.Pattern;//para representar y compilar una expresión regular

/*Esta clase será la encargada de tres cosas:

1. Recibir la interfaz del repositorio (inyección de dependencias, cumpliendo el DIP)

Validar que la contraseña cumpla con las políticas de seguridad usando Expresiones Regulares (RegEx)

Transformar la contraseña a SHA-256 antes de enviarla a la base de datos*/
public class UsuarioService {

    //El servicio depende de la ABSTRACCIÓN, no de SQLite directamente
    private final IUsuarioRepository repository;

    //Se inyecta la dependencia mediante el constructor
    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Validar reglas de negocio, cifrar la clave y guardar el usuario
     */
    public boolean crearUsuario(String login, String nombre, String rol, String passwordPlana) {
        // 1. Validar fortaleza de la contraseña
        if (!validarPassword(passwordPlana)) {
            System.out.println("Error: La contraseña debe tener al menos 6 caracteres, una mayúscula, un número y un carácter especial");
            return false; //Falla la creación
        }

        // 2. Verificar que el login no exista ya en la base de datos
        if (repository.buscarPorLogin(login) != null) {
            System.out.println("Error: El usuario '" + login + "' ya está registrado");
            return false; // Falla la creación
        }

        // 3. Cifrar la contraseña
        String passwordCifrada = cifrarSHA256(passwordPlana);

        // 4. Crear el objeto modelo (Asigna estado "Activo" por defecto)
        Usuario nuevoUsuario = new Usuario(login, nombre, rol, "Activo", passwordCifrada);

        // 5. Enviar al repositorio
        return repository.registrarUsuario(nuevoUsuario);
    }

    /**
     * Autenticar a un usuario para el inicio de sesión
     */
    public Usuario autenticarUsuario(String login, String passwordPlana) {
        Usuario usuario = repository.buscarPorLogin(login);
        
        // Si el usuario no existe, retornamos null
        if (usuario == null) {
            return null; 
        }
        
        // Cifrar la clave ingresada en el login para compararla con la de la BD
        String passwordCifrada = cifrarSHA256(passwordPlana);
        
        if (usuario.getPassword().equals(passwordCifrada)) {
            return usuario; // Login exitoso
        }
        
        return null; // Contraseña incorrecta
    }

    /**
     * Expresión regular: Mínimo 6 caracteres, 1 mayúscula, 1 número, 1 carácter especial
     */
    private boolean validarPassword(String password) {
        // (?=.*[A-Z])         -> Al menos una mayúscula
        // (?=.*\d)            -> Al menos un dígito (osea número)
        // (?=.*[^a-zA-Z0-9])  -> Al menos un carácter especial (ni letra ni número)
        // .{6,}               -> Longitud mínima de 6 caracteres
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{6,}$";
        return Pattern.matches(regex, password);
    }

    /**
     * Algoritmo de cifrado nativo de Java
     */
    private String cifrarSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // Añadir un cero si es un solo dígito hexadecimal
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error fatal: Algoritmo SHA-256 no disponible en tu entorno de Java.", e);
        }
    }
}