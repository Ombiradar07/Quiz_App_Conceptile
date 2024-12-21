package com.osmbiradar.quizapp.repository;

import com.osmbiradar.quizapp.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Questions, Long> {
}
