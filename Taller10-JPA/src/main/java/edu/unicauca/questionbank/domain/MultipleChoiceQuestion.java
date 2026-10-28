package edu.unicauca.questionbank.domain;
import java.util.List;
/** Domain representation of a multiple-choice question. */
public class MultipleChoiceQuestion extends Question {
    private final List<String> options;
    private final int correctOptionIndex;
    /** Creates a multiple-choice question and validates its five options. */
    public MultipleChoiceQuestion(Long id, String context, String statement, String justification, String bibliography,
      Difficulty difficulty, QuestionStatus status, Long authorId, Long topicId, List<String> options, int correctOptionIndex) {
      super(id,context,statement,justification,bibliography,difficulty,status,authorId,topicId);
      if(options==null || options.size()!=5) throw new IllegalArgumentException("Exactly five options are required: four distractors and one correct answer.");
      if(correctOptionIndex<0 || correctOptionIndex>=options.size()) throw new IllegalArgumentException("A single correct option is required.");
      for(String option:options) {
        if(option==null || option.isBlank()) throw new IllegalArgumentException("Options cannot be empty.");
        String normalized=option.trim().toLowerCase();
        if(normalized.equals("todas las anteriores") || normalized.equals("ninguna de las anteriores"))
          throw new IllegalArgumentException("Prohibited option expression.");
      }
      this.options=List.copyOf(options); this.correctOptionIndex=correctOptionIndex;
    }
    public List<String> getOptions(){return options;} public int getCorrectOptionIndex(){return correctOptionIndex;}
}