package co.edu.unicauca.saberpro.domain.valueobject;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;

public final class Justificacion {
    private final String texto;

    public Justificacion(String texto) {
        if (texto == null || texto.trim().length() < 15) {
            throw new ReglaNegocioException("La justificación técnica debe contener al menos 15 caracteres.");
        }
        this.texto = texto.trim();
    }

    public String getTexto() {
        return texto;
    }
}