package co.edu.unicauca.saberpro.infrastructure.inbound.rest;

import lombok.Getter;
import lombok.Setter;

public class PreguntaDtos {

    @Getter @Setter
    public static class CrearPreguntaRequest {
        private String enunciado;
        private String justificacion;
    }

    @Getter @Setter
    public static class OpcionRequest {
        private String texto;
        private boolean esCorrecta;

        // Método explícito por si Lombok genera isEsCorrecta
        public boolean getEsCorrecta() {
            return esCorrecta;
        }

        public void setEsCorrecta(boolean esCorrecta) {
            this.esCorrecta = esCorrecta;
        }
    }
}