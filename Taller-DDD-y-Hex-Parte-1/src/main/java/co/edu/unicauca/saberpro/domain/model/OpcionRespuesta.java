package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;

public class OpcionRespuesta {
    private String texto;
    private boolean esCorrecta;

    public OpcionRespuesta(String texto, boolean esCorrecta) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ReglaNegocioException("El texto de la opción de respuesta no puede estar vacío.");
        }
        this.texto = texto.trim();
        this.esCorrecta = esCorrecta;
    }

    public String getTexto() { return texto; }
    public boolean isEsCorrecta() { return esCorrecta; }
}