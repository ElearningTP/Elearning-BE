package com.api.learning.ElearningBE.services.quiz;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz.QuizAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz.CreateQuizForm;
import com.api.learning.ElearningBE.form.quiz.UpdateQuizForm;
import com.api.learning.ElearningBE.mapper.ModulesMapper;
import com.api.learning.ElearningBE.mapper.QuizMapper;
import com.api.learning.ElearningBE.repositories.ModulesRepository;
import com.api.learning.ElearningBE.repositories.QuizRepository;
import com.api.learning.ElearningBE.storage.criteria.QuizCriteria;
import com.api.learning.ElearningBE.storage.entities.Modules;
import com.api.learning.ElearningBE.storage.entities.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private ModulesMapper modulesMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<QuizDto>>> list(QuizCriteria quizCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<QuizDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<QuizDto>> responseListDto = new ResponseListDto<>();
        Page<Quiz> quizzes = quizRepository.findAll(quizCriteria.getSpecification(),pageable);
        List<QuizDto> quizDtoS = quizMapper.fromEntityToQuizDtoList(quizzes.getContent());

        responseListDto.setContent(quizDtoS);
        responseListDto.setTotalElements(quizzes.getTotalElements());
        responseListDto.setTotalPages(quizzes.getTotalPages());
        responseListDto.setPageSize(quizzes.getSize());
        responseListDto.setPageIndex(quizzes.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve quiz list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizAdminDto> retrieve(Long id) {
        ApiMessageDto<QuizAdminDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", id)));
        QuizAdminDto quizAdminDto = quizMapper.fromEntityToQuizAdminDto(quiz);

        apiMessageDto.setData(quizAdminDto);
        apiMessageDto.setMessage("Retrieve quiz successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizDto> create(CreateQuizForm createQuizForm) {
        ApiMessageDto<QuizDto> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(createQuizForm.getModulesId())
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found", createQuizForm.getModulesId())));
        Quiz quiz = quizMapper.fromCreateQuizFormToEntity(createQuizForm);
        quiz.setModules(modules);
        quizRepository.save(quiz);
        QuizDto quizDto = quizMapper.fromEntityToQuizDto(quiz);

        apiMessageDto.setData(quizDto);
        apiMessageDto.setMessage("Create quiz successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizDto> update(UpdateQuizForm updateQuizForm) {
        ApiMessageDto<QuizDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(updateQuizForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", updateQuizForm.getId())));
        quizMapper.fromUpdateQuizFormToEntity(updateQuizForm,quiz);
        quizRepository.save(quiz);
        QuizDto quizDto = quizMapper.fromEntityToQuizDto(quiz);

        apiMessageDto.setData(quizDto);
        apiMessageDto.setMessage("Update quiz successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", id)));
        quizRepository.delete(quiz);

        apiMessageDto.setMessage("Update quiz successfully");
        return apiMessageDto;
    }
}
