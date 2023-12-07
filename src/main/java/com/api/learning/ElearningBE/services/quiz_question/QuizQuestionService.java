package com.api.learning.ElearningBE.services.quiz_question;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionAdminDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import com.api.learning.ElearningBE.form.quiz_question.CreateQuizQuestionForm;
import com.api.learning.ElearningBE.form.quiz_question.UpdateQuizQuestionForm;
import com.api.learning.ElearningBE.storage.criteria.QuizQuestionCriteria;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizQuestionService {
    ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> list(QuizQuestionCriteria quizQuestionCriteria, Pageable pageable);
    ApiMessageDto<QuizQuestionAdminDto> retrieve(Long id);
    ApiMessageDto<QuizQuestionDto> create(CreateQuizQuestionForm createQuizQuestionForm);
    ApiMessageDto<QuizQuestionDto> update(UpdateQuizQuestionForm updateQuizQuestionForm);
    ApiMessageDto<String> delete(Long id);
}
