package com.api.learning.ElearningBE.services.quiz_submission;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.quiz_submission.ReviewQuizSubmissionDto;
import com.api.learning.ElearningBE.form.quiz_submission.CreateQuizSubmissionForm;
import com.api.learning.ElearningBE.storage.criteria.QuizSubmissionCriteria;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface QuizSubmissionService {
    ApiMessageDto<ResponseListDto<List<QuizSubmissionDto>>> list(QuizSubmissionCriteria quizSubmissionCriteria, Pageable pageable);
    ApiMessageDto<QuizSubmissionDto> submit(CreateQuizSubmissionForm createQuizSubmissionForm);
    ApiMessageDto<ReviewQuizSubmissionDto> review(Long id);
}
