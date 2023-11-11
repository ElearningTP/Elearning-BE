package com.api.learning.ElearningBE.services.lesson_plan;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.exceptions.UnauthorizedException;
import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;
import com.api.learning.ElearningBE.mapper.LessonPlanMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.LessonPlanRepository;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LessonPlanServiceImpl implements LessonPlanService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LessonPlanRepository lessonPlanRepository;
    @Autowired
    private LessonPlanMapper lessonPlanMapper;

    @Override
    public ApiMessageDto<String> create(CreateLessonPlanForm createLessonPlanForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(createLessonPlanForm.getTeacherId())
                .orElseThrow(() -> new NotFoundException(String.format("Teacher with id %s not found", createLessonPlanForm.getTeacherId())));
        if (!account.getRole().getKind().equals(ELearningConstant.ROLE_KIND_TEACHER)){
            throw new UnauthorizedException("Only teachers are allowed to create lesson plans");
        }
        LessonPlan lessonPlan = lessonPlanMapper.fromCreateLessonPlanFormToEntity(createLessonPlanForm);
        lessonPlan.setTeacher(account);
        lessonPlanRepository.save(lessonPlan);

        apiMessageDto.setMessage("Create lesson plan successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateLessonPlanForm updateLessonPlanForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        LessonPlan lessonPlan = lessonPlanRepository.findById(updateLessonPlanForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Lesson plan with id %s not found", updateLessonPlanForm.getId())));
//        if (Objects.equals(lessonPlan.getTeacher().getId(),u))
        lessonPlanMapper.fromUpdateLessonPlanFormToEntity(updateLessonPlanForm,lessonPlan);
        lessonPlanRepository.save(lessonPlan);

        apiMessageDto.setMessage("Update lesson plan successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        LessonPlan lessonPlan = lessonPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lesson plan with id %s not found", id)));
        lessonPlanRepository.delete(lessonPlan);

        apiMessageDto.setMessage("Delete lesson plan successfully");
        return apiMessageDto;
    }
}
