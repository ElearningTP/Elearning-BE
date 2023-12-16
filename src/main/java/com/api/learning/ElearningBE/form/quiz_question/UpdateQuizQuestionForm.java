package com.api.learning.ElearningBE.form.quiz_question;

import com.api.learning.ElearningBE.form.answer_question.CreateAnswerQuestionForm;
import com.api.learning.ElearningBE.form.answer_question.UpdateAnswerQuestionForm;
import com.api.learning.ElearningBE.validation.QuizQuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateQuizQuestionForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Question content can not be empty")
    @ApiModelProperty(name = "questionContent", required = true)
    private String questionContent;
    @NotNull(message = "Question type can not be null")
    @ApiModelProperty(name = "questionType", required = true)
    @QuizQuestionType
    private Integer questionType;
    private List<@Valid UpdateAnswerQuestionForm> answers;
}
