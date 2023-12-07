package com.api.learning.ElearningBE.services.answer_question;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionAdminDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.form.answer_question.CreateAnswerQuestionForm;
import com.api.learning.ElearningBE.form.answer_question.UpdateAnswerQuestionForm;
import com.api.learning.ElearningBE.storage.criteria.AnswerQuestionCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerQuestionService {
    ApiMessageDto<ResponseListDto<List<AnswerQuestionDto>>> list(AnswerQuestionCriteria answerQuestionCriteria, Pageable pageable);
    ApiMessageDto<AnswerQuestionAdminDto> retrieve(Long id);
    ApiMessageDto<AnswerQuestionDto> create(CreateAnswerQuestionForm createAnswerQuestionForm);
    ApiMessageDto<AnswerQuestionDto> update(UpdateAnswerQuestionForm updateAnswerQuestionForm);
    ApiMessageDto<String> delete(Long id);
}
