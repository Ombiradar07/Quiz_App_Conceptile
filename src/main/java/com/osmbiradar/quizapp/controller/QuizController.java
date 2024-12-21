package com.osmbiradar.quizapp.controller;

import com.osmbiradar.quizapp.dto.AnswerRequestDTO;
import com.osmbiradar.quizapp.dto.QuestionResponseDTO;
import com.osmbiradar.quizapp.dto.QuizStatsDTO;
import com.osmbiradar.quizapp.service.QuizService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    Logger log = LoggerFactory.getLogger(QuizController.class);

    @PostMapping("/start")
    public ResponseEntity<String> startQuiz(@RequestParam Long userId) {
        return new ResponseEntity<>(quizService.startQuiz(userId), HttpStatus.CREATED);
    }

    @GetMapping("/question")
    public ResponseEntity<QuestionResponseDTO> getRandomQuestion(@RequestParam Long userId) {
        log.info("Fetching random question for user ID: {}", userId);
        QuestionResponseDTO randomQuestion = quizService.getRandomQuestion(userId);
        log.info("Returning question: {}", randomQuestion);
        return ResponseEntity.ok(randomQuestion);
    }

    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestParam Long userId, @Valid @RequestBody AnswerRequestDTO answerRequestDTO) {
        return new ResponseEntity<>(quizService.submitAnswer(userId, answerRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<QuizStatsDTO> getQuizStats(@RequestParam Long userId) {
        return new ResponseEntity<>(quizService.getQuizStats(userId), HttpStatus.OK);
    }
}
