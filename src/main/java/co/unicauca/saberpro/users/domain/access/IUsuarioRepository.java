package co.unicauca.saberpro.users.domain.access;

import co.unicauca.saberpro.users.domain.Usuario;

public interface IUsuarioRepository {
    boolean registrarUsuario(Usuario usuario);
    Usuario buscarPorLogin(String login);
}