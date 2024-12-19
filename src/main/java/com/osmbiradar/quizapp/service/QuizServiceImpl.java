package com.osmbiradar.quizapp.service;

import com.osmbiradar.quizapp.dto.AnswerRequestDTO;
import com.osmbiradar.quizapp.dto.QuestionResponseDTO;
import com.osmbiradar.quizapp.dto.QuizStatsDTO;
import com.osmbiradar.quizapp.entity.Questions;
import com.osmbiradar.quizapp.entity.QuizSession;
import com.osmbiradar.quizapp.entity.User;
import com.osmbiradar.quizapp.exception.QuestionsNotFoundException;
import com.osmbiradar.quizapp.exception.UserNotFoundException;
import com.osmbiradar.quizapp.repository.QuestionRepository;
import com.osmbiradar.quizapp.repository.QuizSessionRepository;
import com.osmbiradar.quizapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuestionRepository questionRepository;
    private final QuizSessionRepository quizSessionRepository;
    private final UserRepository userRepository;

    public QuizServiceImpl(
            QuestionRepository questionRepository,
            QuizSessionRepository quizSessionRepository,
            UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.quizSessionRepository = quizSessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String startQuiz(Long userId) {
        log.info("Starting quiz for user ID: {}", userId);

        // Fetch user and questions (logging at each step)
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        log.info("User found: {}", user.getName());

        // Create new unique quiz session
        QuizSession session = new QuizSession();
        session.setId(Long.valueOf(UUID.randomUUID().toString()));
        session.setIsActive(true);
        quizSessionRepository.save(session);
        log.info("Quiz session created with ID: {}", session.getId());

        return "Session Created, Quiz Started Successfully....";
    }

    @Override
    public QuestionResponseDTO getRandomQuestion() {
        List<Questions> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            log.error("No questions found!");
            throw new QuestionsNotFoundException("No questions found!");
        }

        Random random = new Random();
        Questions question = questions.get(random.nextInt(questions.size()));
        log.info("Retrieved random question ID: {}", question.getId());

        return new QuestionResponseDTO(question.getId(), question.getQuestionText(),
                question.getOptionA(), question.getOptionB(), question.getOptionC(), question.getOptionD());
    }

    @Override
    public String submitAnswer(Long userId, AnswerRequestDTO answerRequestDTO) {
        log.info("Submitting answer for user ID: {} on question ID: {}", userId, answerRequestDTO.getQuestionId());

        Optional<Questions> question = questionRepository.findById(answerRequestDTO.getQuestionId());
        if (question.isPresent()) {
            String correctAnswer = question.get().getCorrectAnswer();
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

            // Check if the answer is correct and update the user's score
            if (correctAnswer.equalsIgnoreCase(answerRequestDTO.getCorrectAnswer())) {
                user.setCorrectAnswers(user.getCorrectAnswers() + 1);
            } else {
                user.setIncorrectAnswers(user.getIncorrectAnswers() + 1);
            }
            userRepository.save(user);
            log.info("User ID: {} updated with correct/incorrect answers.", userId);

            return "Answer submitted successfully...";
        }

        log.error("Question not found...");
        return "Question not found...";
    }

    @Override
    public QuizStatsDTO getQuizStats(Long userId) {
        log.info("Fetching quiz stats for user ID: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new QuizStatsDTO(user.getCorrectAnswers(), user.getIncorrectAnswers());
    }
}
