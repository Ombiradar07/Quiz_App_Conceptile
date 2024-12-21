package com.osmbiradar.quizapp.service;

import com.osmbiradar.quizapp.dto.AnswerRequestDTO;
import com.osmbiradar.quizapp.dto.QuestionResponseDTO;
import com.osmbiradar.quizapp.dto.QuizStatsDTO;
import com.osmbiradar.quizapp.entity.Questions;
import com.osmbiradar.quizapp.entity.QuizSession;
import com.osmbiradar.quizapp.entity.User;
import com.osmbiradar.quizapp.exception.ResourceNotFoundException;
import com.osmbiradar.quizapp.repository.QuestionRepository;
import com.osmbiradar.quizapp.repository.QuizSessionRepository;
import com.osmbiradar.quizapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    @Autowired
    private UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(QuizServiceImpl.class);


    @Override
    public String startQuiz(Long userId) {
        log.info("Starting quiz for user ID: {}", userId);

        //TODO Step 1:  Fetch user from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        log.debug("User validated: {}", user.getName());

        //TODO Step 2:  Clean up previous session if it exists
        quizSessionRepository.findByUserIdAndIsActive(userId, true).ifPresent(session -> {
            session.setIsActive(false);
            quizSessionRepository.save(session);
            log.debug("Previous session deactivated for user ID: {}", userId);
        });

        // TODO Step 3: Create a new session for the user
        QuizSession newSession = new QuizSession();
        newSession.setUserId(userId);
        newSession.setIsActive(true);
        quizSessionRepository.save(newSession);

        log.info("New quiz session created for user ID: {}", userId);
        return "Quiz started successfully.";
    }

    @Override
    public QuestionResponseDTO getRandomQuestion(Long userId) {
        log.info("Fetching random question for user ID: {}", userId);

        //TODO Step 1: Validate the session
        quizSessionRepository.findByUserIdAndIsActive(userId, true)
                .orElseThrow(() -> new ResourceNotFoundException("Active session not found for user ID: " + userId));

        //TODO Step 2: Fetch all questions from the database
        List<Questions> allQuestions = questionRepository.findAll();
        if (allQuestions.isEmpty()) {
            log.error("No questions found in the database!");
            throw new ResourceNotFoundException("No questions available!");
        }

        //TODO Step 3: Select a random question
        Random random = new Random();
        Questions randomQuestion = allQuestions.get(random.nextInt(allQuestions.size()));

        log.info("Retrieved random question ID: {}", randomQuestion.getId());

        // Prepare the response
        QuestionResponseDTO response = new QuestionResponseDTO();
        response.setId(randomQuestion.getId());
        response.setQuestionText(randomQuestion.getQuestionText());
        response.setOptionA(randomQuestion.getOptionA());
        response.setOptionB(randomQuestion.getOptionB());
        response.setOptionC(randomQuestion.getOptionC());
        response.setOptionD(randomQuestion.getOptionD());

        return response;
    }

    @Override
    public String submitAnswer(Long userId, AnswerRequestDTO answerRequestDTO) {
        log.info("Submitting answer for user ID: {} on question ID: {}", userId, answerRequestDTO.getQuestionId());

        // TODO Step 1: Check whether the session is active or not
        QuizSession session = quizSessionRepository.findByUserIdAndIsActive(userId, true).orElseThrow(() -> new ResourceNotFoundException("Active session not found for user ID: " + userId));

        // TODO Step 2: Fetch the question from the database based on the ID
        Questions question = questionRepository.findById(answerRequestDTO.getQuestionId()).orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + answerRequestDTO.getQuestionId()));

        // TODO Step 3:  Fetch the user from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));


        // TODO Step 4: Check if the user's answer is correct and update the stats
        String usersAnswer = answerRequestDTO.getCorrectAnswer();
        String correctAnswer = question.getCorrectAnswer();
        log.debug("Correct answer: {}, User's answer: {}", correctAnswer, usersAnswer);

        if (usersAnswer.equalsIgnoreCase(correctAnswer)) {
            user.setCorrectAnswers(user.getCorrectAnswers() + 1);
        } else {
            user.setIncorrectAnswers(user.getIncorrectAnswers() + 1);
        }

        userRepository.save(user);
        log.info("User stats updated for user ID: {}", userId);

        // TODO: Step 5 Check if the user has answered all the questions and end the session if so
        long totalQuestions = questionRepository.count();
        if (user.getCorrectAnswers() + user.getIncorrectAnswers() == totalQuestions) {
            session.setIsActive(false);
            quizSessionRepository.save(session);
            log.info("All questions answered. Quiz session ended for user ID: {}", userId);
        }

        return "Answer submitted successfully.";
    }

    @Override
    public QuizStatsDTO getQuizStats(Long userId) {
        log.info("Fetching quiz stats for user ID: {}", userId);

        // TODO: Fetch user stats
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return new QuizStatsDTO(user.getCorrectAnswers(), user.getIncorrectAnswers());
    }
}
