package com.osmbiradar.quizapp.controlller;

import com.osmbiradar.quizapp.dto.AnswerRequestDTO;
import com.osmbiradar.quizapp.dto.QuestionResponseDTO;
import com.osmbiradar.quizapp.dto.QuizStatsDTO;
import com.osmbiradar.quizapp.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startQuiz(Long userId) {
        return new ResponseEntity<>(quizService.startQuiz(userId), HttpStatus.CREATED);
    }

    @GetMapping("/question")
    public ResponseEntity<QuestionResponseDTO> getRandomQuestion() {
        return new ResponseEntity<>(quizService.getRandomQuestion(), HttpStatus.OK);
    }

    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestHeader Long userId, @Valid @RequestBody AnswerRequestDTO answerRequestDTO) {
        return new ResponseEntity<>(quizService.submitAnswer(userId,answerRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<QuizStatsDTO> getQuizStats(@RequestParam Long userId) {
        return new ResponseEntity<>(quizService.getQuizStats(userId), HttpStatus.OK);
    }
}
