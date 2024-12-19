package com.osmbiradar.quizapp.repository;

import com.osmbiradar.quizapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
