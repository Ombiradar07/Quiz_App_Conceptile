package com.osmbiradar.quizapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "Option_A", nullable = false)
    private String optionA;
    @Column(name = "Option_B", nullable = false)
    private String optionB;
    @Column(name = "Option_C", nullable = false)
    private String optionC;
    @Column(name = "Option_D", nullable = false)
    private String optionD;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;


}
