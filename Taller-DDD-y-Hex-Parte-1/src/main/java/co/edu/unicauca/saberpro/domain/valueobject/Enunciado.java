package co.edu.unicauca.saberpro.domain.valueobject;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;

public final class Enunciado {
    private final String texto;

    public Enunciado(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ReglaNegocioException("El enunciado no puede ser nulo o vacío.");
        }
        if (texto.trim().length() < 10) {
            throw new ReglaNegocioException("El enunciado debe tener al menos 10 caracteres.");
        }
        this.texto = texto.trim();
    }

    public String getTexto() {
        return texto;
    }
}