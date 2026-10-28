package edu.unicauca.questionbank.api;
import edu.unicauca.questionbank.application.QuestionService;
import edu.unicauca.questionbank.domain.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/** REST endpoints for creating and querying questions. */
@RestController @RequestMapping("/api/questions")
public class QuestionController {
 private final QuestionService service;
 public QuestionController(QuestionService service){this.service=service;}
 @PostMapping public Question create(@RequestBody CreateQuestionRequest request){
  return service.create(request.context(),request.statement(),request.justification(),request.bibliography(),request.difficulty(),request.authorId(),request.topicId());
 }
 @GetMapping public List<Question> all(){return service.findAll();}
 @GetMapping("/{id}") public Question byId(@PathVariable Long id){return service.find(id);}
 /** Request payload for a question. */
 public record CreateQuestionRequest(String context,String statement,String justification,String bibliography,Difficulty difficulty,Long authorId,Long topicId){}
}