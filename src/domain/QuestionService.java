package domain;

import access.QuestionImplRepository;
import infra.Subject;
import java.util.List;

public class QuestionService extends Subject {
    private final QuestionImplRepository repository;

    public QuestionService(QuestionImplRepository repository) {
        this.repository = repository;
    }

    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    public Question getQuestionById(String id) {
        return repository.findById(id);
    }

    public void updateQuestionState(String id, String newState) {
        Question q = repository.findById(id);
        if (q != null) {
            q.setState(newState);
            notifyAllObservers(); // Notifica a las vistas de estadísticas y gráfica
        }
    }
     public void guardarPregunta(Question question) {
        repository.save(question);
    }
}