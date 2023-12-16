package com.api.learning.ElearningBE.form.quiz_question;

import com.api.learning.ElearningBE.form.answer_question.CreateAnswerQuestionFormForCreateQuestion;
import com.api.learning.ElearningBE.validation.QuizQuestionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    @Size(min = 1, message = "Have to least 1 question")
    private List<@Valid CreateAnswerQuestionFormForCreateQuestion> answers;
}
