package com.api.learning.ElearningBE.services.quiz_submission;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.quiz_submission.ReviewQuizSubmissionDto;
import com.api.learning.ElearningBE.form.quiz_submission.CreateQuizSubmissionForm;

public interface QuizSubmissionService {
    ApiMessageDto<QuizSubmissionDto> submit(CreateQuizSubmissionForm createQuizSubmissionForm);
    ApiMessageDto<ReviewQuizSubmissionDto> review(Long id);
}
