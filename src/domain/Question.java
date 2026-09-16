package domain;

import java.util.List;

public class Question {
    private String id;
    private String nameQuestion;
    private String contextQuestion;
    private List<String> options;
    private String correctAnswer;
    private String state;
    public Question(String id, String nameQuestion, String contextQuestion, List<String> options, String correctAnswer, String state) {
        this.id = id;
        this.nameQuestion = nameQuestion;
        this.contextQuestion = contextQuestion;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.state = state;
    }
    public String getId() { return id; }
    public String getName() { return nameQuestion; }
    public String getContext() { return contextQuestion; }
    public List<String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getState() { return state; }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return id + " - " + nameQuestion;
    }
  
}
