package com.osmbiradar.quizapp.service;

import com.osmbiradar.quizapp.dto.AnswerRequestDTO;
import com.osmbiradar.quizapp.dto.QuestionResponseDTO;
import com.osmbiradar.quizapp.dto.QuizStatsDTO;

public interface QuizService {

    String startQuiz(Long userId);

    QuestionResponseDTO getRandomQuestion(Long userId);

    String submitAnswer(Long userId, AnswerRequestDTO answerRequestDTO);

    QuizStatsDTO getQuizStats(Long userId);
}
