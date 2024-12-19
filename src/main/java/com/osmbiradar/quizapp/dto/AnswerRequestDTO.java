package com.osmbiradar.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerRequestDTO {

    @NotNull(message = "Question ID cannot be null")
    private Long questionId;

    @NotNull(message = "Correct Answer cannot be null")
    private String correctAnswer;
}
