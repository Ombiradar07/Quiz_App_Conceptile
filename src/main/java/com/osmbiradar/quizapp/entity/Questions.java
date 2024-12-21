package com.osmbiradar.quizapp.entity;

import jakarta.persistence.*;
import lombok.Setter;


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

    //  getter setters because lombok is not working

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
