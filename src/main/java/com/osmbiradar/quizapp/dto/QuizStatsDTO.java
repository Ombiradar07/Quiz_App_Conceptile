package com.osmbiradar.quizapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizStatsDTO {
    private Long correctAnswers;
    private Long incorrectAnswers;

}
