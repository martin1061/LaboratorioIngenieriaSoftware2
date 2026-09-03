package co.unicauca.saberpro.users.domain.service;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.unicauca.saberpro.users.domain.Usuario;
import co.unicauca.saberpro.users.domain.access.IUsuarioRepository;

public class UsuarioServiceTest {

    private UsuarioService servicio;

    /**
     * Repositorio Falso en memoria (Fake) para probar el servicio con el principio DIP
     */
    private class FakeUsuarioRepository implements IUsuarioRepository {
        private final Map<String, Usuario> baseDatosEnMemoria = new HashMap<>();

        @Override
        public boolean registrarUsuario(Usuario usuario) {
            if (baseDatosEnMemoria.containsKey(usuario.getLogin())) {
                return false;
            }
            baseDatosEnMemoria.put(usuario.getLogin(), usuario);
            return true;
        }

        @Override
        public Usuario buscarPorLogin(String login) {
            return baseDatosEnMemoria.get(login);
        }
    }

    @BeforeEach
    public void setUp() {
        servicio = new UsuarioService(new FakeUsuarioRepository());
    }

    @Test
    public void testCrearUsuarioExitoso() {
        boolean resultado = servicio.crearUsuario("jbenavidez", "Jonnathan Benavidez", "Estudiante", "Clave123!");
        assertTrue(resultado, "El usuario debería crearse con una clave válida");
    }

    @Test
    public void testCrearUsuarioPasswordCorta() {
        boolean resultado = servicio.crearUsuario("jbenavidez", "Jonnathan Benavidez", "Estudiante", "Ab1!");
        assertFalse(resultado, "Debería rechazar claves de menos de 6 caracteres");
    }

    @Test
    public void testCrearUsuarioSinMayuscula() {
        boolean resultado = servicio.crearUsuario("jbenavidez", "Jonnathan Benavidez", "Estudiante", "clave123!");
        assertFalse(resultado, "Debería rechazar claves sin mayúsculas");
    }

    @Test
    public void testCrearUsuarioSinCaracterEspecial() {
        boolean resultado = servicio.crearUsuario("jbenavidez", "Jonnathan Benavidez", "Estudiante", "Clave1234");
        assertFalse(resultado, "Debería rechazar claves sin caracteres especiales");
    }

    @Test
    public void testAutenticarUsuarioExitoso() {
        servicio.crearUsuario("jbenavidez", "Jonnathan Benavidez", "Estudiante", "Clave123!");
        // Se corrigió "jbonilla" por "jbenavidez"
        Usuario usuario = servicio.autenticarUsuario("jbenavidez", "Clave123!");
        
        assertNotNull(usuario, "El usuario debería autenticarse correctamente");
        assertEquals("Jonnathan Benavidez", usuario.getNombreCompleto());
    }

    @Test
    public void testAutenticarUsuarioClaveErronea() {
        servicio.crearUsuario("jbenavidez", "Jonnathan Benavidez", "Estudiante", "Clave123!");
        // Se corrigió "jbonilla" por "jbenavidez"
        Usuario usuario = servicio.autenticarUsuario("jbenavidez", "ClaveErronea123!");
        
        assertNull(usuario, "No debería permitir autenticación con clave incorrecta");
    }
}