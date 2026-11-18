package co.edu.unicauca.saberpro.infrastructure.inbound.rest;

import co.edu.unicauca.saberpro.domain.ports.inbound.CrearPreguntaUseCase;
import co.edu.unicauca.saberpro.domain.model.Pregunta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {

    private final CrearPreguntaUseCase crearPreguntaUseCase;

    public PreguntaController(CrearPreguntaUseCase crearPreguntaUseCase) {
        this.crearPreguntaUseCase = crearPreguntaUseCase;
    }

    @PostMapping
    public ResponseEntity<Pregunta> crearBorrador(@RequestBody PreguntaDtos.CrearPreguntaRequest request) {
        Pregunta pregunta = crearPreguntaUseCase.crearPreguntaBorrador(request.getEnunciado(), request.getJustificacion());
        return new ResponseEntity<>(pregunta, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/opciones")
    public ResponseEntity<Pregunta> agregarOpcion(@PathVariable Long id, @RequestBody PreguntaDtos.OpcionRequest request) {
        Pregunta pregunta = crearPreguntaUseCase.agregarOpcion(id, request.getTexto(), request.getEsCorrecta());
        return ResponseEntity.ok(pregunta);
    }

    @PostMapping("/{id}/enviar-revision")
    public ResponseEntity<Pregunta> enviarARevision(@PathVariable Long id) {
        Pregunta pregunta = crearPreguntaUseCase.enviarARevision(id);
        return ResponseEntity.ok(pregunta);
    }

    @GetMapping
    public ResponseEntity<List<Pregunta>> listar() {
        return ResponseEntity.ok(crearPreguntaUseCase.listarPreguntas());
    }
}