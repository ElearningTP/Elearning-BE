package com.api.learning.ElearningBE.form.quiz_question;

import com.api.learning.ElearningBE.validation.QuizQuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateQuizQuestionForm {
    @NotEmpty(message = "Question content can not be empty")
    @ApiModelProperty(name = "questionContent", required = true)
    private String questionContent;
    @NotNull(message = "Question type can not be null")
    @ApiModelProperty(name = "questionType", required = true)
    @QuizQuestionType
    private Integer questionType;
    @NotNull(message = "Quiz id can not be null")
    @ApiModelProperty(name = "quizId", required = true)
    private Long quizId;
}
