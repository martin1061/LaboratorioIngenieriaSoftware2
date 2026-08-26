package co.unicauca.saberpro.users.domain.access;

public class Factory {
    private static Factory instance;

    private Factory() {}

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public IUsuarioRepository getRepository(String tipo) {
        //Por ahora retorno el repositorio de SQLite
        //Si cambia la BD a otra, solo se modifico aquí (principio OCP / DIP)
        if (tipo.equalsIgnoreCase("sqlite")) {
            return new UsuarioSQLiteRepository();
        }
        return null;
    }
}