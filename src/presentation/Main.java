package presentation;



import access.QuestionImplRepository;
import domain.Question;
import domain.QuestionService;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Repositorio
        QuestionImplRepository repository = new QuestionImplRepository();

        // Servicio
        QuestionService service = new QuestionService(repository);

        // Preguntas de prueba
        Question pregunta1 = new Question(
                "1",
                "Pregunta 1",
                "¿Cuál es la capital de Colombia?",
                Arrays.asList("Bogotá", "Medellín", "Cali", "Popayán"),
                "Bogotá",
                "borrador"
        );

        Question pregunta2 = new Question(
                "2",
                "Pregunta 2",
                "¿Cuánto es 2 + 2?",
                Arrays.asList("2", "3", "4", "5"),
                "4",
                "Pendiente de revisión"
        );

        Question pregunta3 = new Question(
                "3",
                "Pregunta 3",
                "¿Cuál es el resultado de 5 × 5?",
                Arrays.asList("10", "15", "20", "25"),
                "25",
                "Eliminada"
        );

        // Guardar preguntas
        service.guardarPregunta(pregunta1);
        service.guardarPregunta(pregunta2);
        service.guardarPregunta(pregunta3);

        // Crear las ventanas
        GUIQuestions guiQuestions = new GUIQuestions(service);
        GUIObserver1 observer1 = new GUIObserver1(service);
        GUIObserver2 observer2 = new GUIObserver2(service);

        // Registrar observadores
        service.addObserver(observer1);
        service.addObserver(observer2);

        // Mostrar ventanas
        guiQuestions.setVisible(true);
        observer1.setVisible(true);
        observer2.setVisible(true);
    }
}
    
