package com.api.learning.ElearningBE.form.quiz_submission;

import com.api.learning.ElearningBE.form.quiz_submission_result.CreateQuizSubmissionResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateQuizSubmissionForm {
    @NotNull(message = "Quiz id can not be null")
    @ApiModelProperty(name = "quizId", required = true)
    private Long quizId;
    @NotNull(message = "Course id can not be null")
    @ApiModelProperty(name = "courseId", required = true)
    private Long courseId;
    @NotNull(message = "Total time can not be null")
    @ApiModelProperty(name = "totalTime", required = true, notes = "This attribute stores the total time taken on the quiz")
    private Long totalTime;
    private List<@Valid CreateQuizSubmissionResult> results;
}
