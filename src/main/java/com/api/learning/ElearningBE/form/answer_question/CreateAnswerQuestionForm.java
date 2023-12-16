package com.api.learning.ElearningBE.form.answer_question;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateAnswerQuestionForm {
    @NotNull(message = "Question id can not be null")
    @ApiModelProperty(name = "questionId", required = true)
    private Long questionId;
    @Size(min = 1, message = "Have to at least 1 answer")
    List<@Valid CreateAnswerQuestionFormForCreateQuestion> answers;
}
