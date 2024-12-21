package com.osmbiradar.quizapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizStatsDTO {
    private Long correctAnswers;
    private Long incorrectAnswers;

    public QuizStatsDTO(Long correctAnswers, Long incorrectAnswers) {
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
    }

    public Long getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Long correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Long getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(Long incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}
