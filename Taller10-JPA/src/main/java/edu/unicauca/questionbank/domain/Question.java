package edu.unicauca.questionbank.domain;
/** Persistence-independent domain model for a question. */
public class Question {
    private Long id;
    private String context;
    private String statement;
    private String justification;
    private String bibliography;
    private Difficulty difficulty;
    private QuestionStatus status;
    private Long authorId;
    private Long topicId;
    /** Creates a new draft question. */
    public Question(Long id, String context, String statement, String justification, String bibliography,
                    Difficulty difficulty, QuestionStatus status, Long authorId, Long topicId) {
        this.id=id; this.context=context; this.statement=statement; this.justification=justification;
        this.bibliography=bibliography; this.difficulty=difficulty;
        this.status=status == null ? QuestionStatus.DRAFT : status; this.authorId=authorId; this.topicId=topicId;
    }
    /** Validates structure before submitting the question for review. */
    public void submitForReview() {
        if (status != QuestionStatus.DRAFT) throw new IllegalStateException("Only draft questions can be submitted.");
        if (context == null || context.isBlank() || statement == null || statement.isBlank())
            throw new IllegalStateException("Context and statement are required.");
        status=QuestionStatus.PENDING_REVIEW;
    }
    /** Changes a draft question to its approved state after review. */
    public void approve() {
        if (status != QuestionStatus.IN_REVIEW) throw new IllegalStateException("Only questions in review can be approved.");
        status=QuestionStatus.APPROVED;
    }
    /** Publishes an approved question. */
    public void publish() {
        if (status != QuestionStatus.APPROVED) throw new IllegalStateException("Only approved questions can be published.");
        status=QuestionStatus.PUBLISHED;
    }
    public Long getId(){return id;} public String getContext(){return context;} public String getStatement(){return statement;}
    public String getJustification(){return justification;} public String getBibliography(){return bibliography;}
    public Difficulty getDifficulty(){return difficulty;} public QuestionStatus getStatus(){return status;}
    public Long getAuthorId(){return authorId;} public Long getTopicId(){return topicId;}
}