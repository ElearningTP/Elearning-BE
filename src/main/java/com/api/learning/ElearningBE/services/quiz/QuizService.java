package com.api.learning.ElearningBE.services.quiz;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz.QuizAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.dto.quiz.StartQuizDto;
import com.api.learning.ElearningBE.form.quiz.CreateQuizForm;
import com.api.learning.ElearningBE.form.quiz.UpdateQuizForm;
import com.api.learning.ElearningBE.storage.criteria.QuizCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizService {
    ApiMessageDto<StartQuizDto> start(Long id, Long courseId);
    ApiMessageDto<ResponseListDto<List<QuizDto>>> list(QuizCriteria quizCriteria, Pageable pageable);
    ApiMessageDto<QuizAdminDto> retrieve(Long id);
    ApiMessageDto<QuizDto> create(CreateQuizForm createQuizForm);
    ApiMessageDto<QuizDto> update(UpdateQuizForm updateQuizForm);
    ApiMessageDto<String> delete(Long id);
}
