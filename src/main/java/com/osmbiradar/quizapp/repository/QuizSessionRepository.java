package com.osmbiradar.quizapp.repository;

import com.osmbiradar.quizapp.entity.Questions;
import com.osmbiradar.quizapp.entity.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
    Optional<QuizSession> findByUserIdAndIsActive(Long userId, Boolean isActive);
}
