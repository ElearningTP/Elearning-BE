package com.api.learning.ElearningBE.services.lesson_plan;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.exceptions.UnauthorizedException;
import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;
import com.api.learning.ElearningBE.mapper.LessonPlanMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.LessonPlanRepository;
import com.api.learning.ElearningBE.storage.criteria.LessonPlanCriteria;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonPlanServiceImpl implements LessonPlanService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LessonPlanRepository lessonPlanRepository;
    @Autowired
    private LessonPlanMapper lessonPlanMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> list(LessonPlanCriteria lessonPlanCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<LessonPlanDto>> responseListDto = new ResponseListDto<>();
        Page<LessonPlan> lessonPlans = lessonPlanRepository.findAll(lessonPlanCriteria.getSpecification(),pageable);
        List<LessonPlanDto> lessonPlanDtoS = lessonPlanMapper.fromEntityToLessonPlanDtoList(lessonPlans.getContent());

        responseListDto.setContent(lessonPlanDtoS);
        responseListDto.setTotalPages(lessonPlans.getTotalPages());
        responseListDto.setTotalElements(lessonPlans.getTotalElements());
        responseListDto.setPageIndex(lessonPlans.getNumber());
        responseListDto.setPageSize(lessonPlans.getSize());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve lesson plan list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<LessonPlanAdminDto> retrieve(Long id) {
        ApiMessageDto<LessonPlanAdminDto> apiMessageDto = new ApiMessageDto<>();
        LessonPlan lessonPlan = lessonPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lesson plan with id %s not found", id)));
        LessonPlanAdminDto lessonPlanDto = lessonPlanMapper.fromEntityToLessonPlanAdminDto(lessonPlan);

        apiMessageDto.setData(lessonPlanDto);
        apiMessageDto.setMessage("Retrieve lesson plan successfully");
        return apiMessageDto;
    }

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
