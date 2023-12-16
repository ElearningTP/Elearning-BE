package com.api.learning.ElearningBE.form.answer_question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAnswerQuestionFormForCreateQuestion {
    @NotEmpty(message = "Answer content can not be empty")
    @ApiModelProperty(name = "answerContent", required = true)
    private String answerContent;
    @NotNull(message = "Correct answer can not be null")
    @ApiModelProperty(name = "isCorrect", required = true)
    private Boolean isCorrect;
}
