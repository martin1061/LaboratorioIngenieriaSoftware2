package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.domain.exception.ReglaNegocioException;
import co.edu.unicauca.saberpro.domain.valueobject.Enunciado;
import co.edu.unicauca.saberpro.domain.valueobject.Justificacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pregunta {
    private Long id;
    private Enunciado enunciado;
    private Justificacion justificacion;
    private List<OpcionRespuesta> opciones = new ArrayList<>();
    private EstadoPregunta estado = EstadoPregunta.BORRADOR;

    public Pregunta(Long id, Enunciado enunciado, Justificacion justificacion) {
        this.id = id;
        this.enunciado = enunciado;
        this.justificacion = justificacion;
    }

    public void agregarOpcion(String texto, boolean esCorrecta) {
        if (this.estado != EstadoPregunta.BORRADOR) {
            throw new ReglaNegocioException("Solo se pueden modificar opciones en estado Borrador.");
        }
        if (this.opciones.size() >= 4) {
            throw new ReglaNegocioException("Una pregunta no puede tener más de 4 opciones de respuesta.");
        }
        this.opciones.add(new OpcionRespuesta(texto, esCorrecta));
    }

    public void enviarARevision() {
        validarInvariantesParaEnvio();
        this.estado = EstadoPregunta.PENDIENTE_REVISION;
    }

    private void validarInvariantesParaEnvio() {
        if (this.opciones.size() != 4) {
            throw new ReglaNegocioException("La pregunta debe tener exactamente 4 opciones de respuesta.");
        }
        long correctas = opciones.stream().filter(OpcionRespuesta::isEsCorrecta).count();
        if (correctas != 1) {
            throw new ReglaNegocioException("Debe existir exactamente 1 opción correcta (clave válida).");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Enunciado getEnunciado() { return enunciado; }
    public Justificacion getJustificacion() { return justificacion; }
    public List<OpcionRespuesta> getOpciones() { return Collections.unmodifiableList(opciones); }
    public EstadoPregunta getEstado() { return estado; }
}