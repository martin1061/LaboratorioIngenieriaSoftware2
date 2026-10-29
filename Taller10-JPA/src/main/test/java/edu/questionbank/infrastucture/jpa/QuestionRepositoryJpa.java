package edu.unicauca.questionbank.infrastructure.jpa;

import edu.unicauca.questionbank.domain.MultipleChoiceQuestion;
import edu.unicauca.questionbank.domain.Question;
import edu.unicauca.questionbank.domain.repository.QuestionRepository;
import edu.unicauca.questionbank.infrastructure.jpa.record.AnswerOptionRecord;
import edu.unicauca.questionbank.infrastructure.jpa.record.MultipleChoiceQuestionRecord;
import edu.unicauca.questionbank.infrastructure.jpa.record.QuestionRecord;
import edu.unicauca.questionbank.infrastructure.jpa.record.TopicRecord;
import edu.unicauca.questionbank.infrastructure.jpa.record.UserRecord;
import edu.unicauca.questionbank.infrastructure.jpa.repository.SpringQuestionDataRepository;
import edu.unicauca.questionbank.infrastructure.jpa.repository.SpringTopicDataRepository;
import edu.unicauca.questionbank.infrastructure.jpa.repository.SpringUserDataRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepositoryJpa implements QuestionRepository {

    private final SpringQuestionDataRepository data;
    private final SpringUserDataRepository users;
    private final SpringTopicDataRepository topics;

    public QuestionRepositoryJpa(SpringQuestionDataRepository data, 
                                 SpringUserDataRepository users, 
                                 SpringTopicDataRepository topics) {
        this.data = data;
        this.users = users;
        this.topics = topics;
    }

    @Override
    public Question save(Question q) {
        UserRecord author = users.findById(q.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        TopicRecord topic = topics.findById(q.getTopicId()).orElseThrow(() -> new IllegalArgumentException("Topic not found"));

        QuestionRecord r;
        if (q instanceof MultipleChoiceQuestion mcq) {
            MultipleChoiceQuestionRecord mcqRecord = new MultipleChoiceQuestionRecord(
                mcq.getContext(), mcq.getStatement(), mcq.getJustification(), mcq.getBibliography(),
                mcq.getDifficulty(), mcq.getStatus(), author, topic
            );
            List<AnswerOptionRecord> optionRecords = new ArrayList<>();
            for (int i = 0; i < mcq.getOptions().size(); i++) {
                optionRecords.add(new AnswerOptionRecord(mcq.getOptions().get(i), i == mcq.getCorrectOptionIndex(), mcqRecord));
            }
            mcqRecord.getOptions().clear();
            mcqRecord.getOptions().addAll(optionRecords);
            r = mcqRecord;
        } else {
            r = new QuestionRecord(q.getContext(), q.getStatement(), q.getJustification(), q.getBibliography(), q.getDifficulty(), q.getStatus(), author, topic);
        }
        r = data.save(r);
        return toDomain(r);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return data.findById(id).map(this::toDomain);
    }

    @Override
    public List<Question> findAll() {
        return data.findAll().stream().map(this::toDomain).toList();
    }

    private Question toDomain(QuestionRecord r) {
        return new Question(r.getId(), r.getContext(), r.getStatement(), r.getJustification(), 
                            r.getBibliography(), r.getDifficulty(), r.getStatus(), 
                            r.getAuthor().getId(), r.getTopic().getId());
    }
}