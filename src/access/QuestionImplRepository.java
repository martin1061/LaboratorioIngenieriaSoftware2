package access;

import domain.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionImplRepository {
    private final List<Question> database = new ArrayList<>();

    public QuestionImplRepository() {
        // Datos de prueba iniciales basados en el diagrama
        database.add(new Question(
            "P-001","Pregunta sobre DDD", "Contexto de la pregunta",
            Arrays.asList("A. Diseñar bases de datos", "B. Modelar el dominio del negocio", "C. Eliminar UML", "D. Crear interfaces gráficas"),
            "B",
            "Borrador"
        ));
    }

    public List<Question> findAll() {
        return database;
    }

    public Question findById(String id) {
        return database.stream()
            .filter(q -> q.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
      public void save(Question question) {
        database.add(question);
    }
}