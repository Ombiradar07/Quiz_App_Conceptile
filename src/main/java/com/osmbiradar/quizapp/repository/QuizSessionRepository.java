package com.osmbiradar.quizapp.repository;

import com.osmbiradar.quizapp.entity.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}
