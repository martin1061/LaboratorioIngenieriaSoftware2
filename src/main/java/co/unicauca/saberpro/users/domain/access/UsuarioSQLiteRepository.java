package co.unicauca.saberpro.users.domain.access;

import co.unicauca.saberpro.users.domain.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioSQLiteRepository implements IUsuarioRepository {

    // Ruta de la base de datos (va a la carpeta de este proyecto, así no la pierdo)
    private final String urlDB = "jdbc:sqlite:saberpro_usuarios.db";

    public UsuarioSQLiteRepository() {
        initDatabase();
    }

    // Método para crear la tabla de usuarios automáticamente al arrancar
    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS T_Usuario ("
                + " login TEXT PRIMARY KEY,"
                + " nombre_completo TEXT NOT NULL,"
                + " rol TEXT NOT NULL,"
                + " estado TEXT NOT NULL,"
                + " password TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(urlDB);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO T_Usuario(login, nombre_completo, rol, estado, password) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(urlDB);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getNombreCompleto());
            pstmt.setString(3, usuario.getRol());
            pstmt.setString(4, usuario.getEstado());
            pstmt.setString(5, usuario.getPassword());
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario en SQLite: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        String sql = "SELECT login, nombre_completo, rol, estado, password FROM T_Usuario WHERE login = ?";
        
        try (Connection conn = DriverManager.getConnection(urlDB);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getString("login"),
                    rs.getString("nombre_completo"),
                    rs.getString("rol"),
                    rs.getString("estado"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }
}