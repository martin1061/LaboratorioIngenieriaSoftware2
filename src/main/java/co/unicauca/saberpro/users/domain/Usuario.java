package co.unicauca.saberpro.users.domain;

public class Usuario {
    private String login;
    private String nombreCompleto;
    private String rol;    // Como Administrador, Autor de preguntas, Revisor, Docente, Estudiante
    private String estado; // Activo* o Inactivo*
    private String password; // Contraseña cifrada con SHA-256

    public Usuario() {}

    public Usuario(String login, String nombreCompleto, String rol, String estado, String password) {
        this.login = login;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.estado = estado;
        this.password = password;
    }

    // Getters y Setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}