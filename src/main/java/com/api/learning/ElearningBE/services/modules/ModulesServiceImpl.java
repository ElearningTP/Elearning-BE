package com.api.learning.ElearningBE.services.modules;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.modules.CreateModulesForm;
import com.api.learning.ElearningBE.form.modules.UpdateModuleForm;
import com.api.learning.ElearningBE.mapper.ModulesMapper;
import com.api.learning.ElearningBE.repositories.LessonPlanRepository;
import com.api.learning.ElearningBE.repositories.ModulesRepository;
import com.api.learning.ElearningBE.storage.criteria.ModulesCriteria;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import com.api.learning.ElearningBE.storage.entities.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModulesServiceImpl implements ModulesService{

    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private LessonPlanRepository lessonPlanRepository;
    @Autowired
    private ModulesMapper modulesMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<ModulesDto>>> list(ModulesCriteria modulesCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ModulesDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<ModulesDto>> responseListDto = new ResponseListDto<>();
        Page<Modules> modules = modulesRepository.findAll(modulesCriteria.getSpecification(),pageable);
        List<ModulesDto> modulesDtoS = modulesMapper.fromEntityToModulesDtoList(modules.getContent());

        responseListDto.setContent(modulesDtoS);
        responseListDto.setTotalPages(modules.getTotalPages());
        responseListDto.setTotalElements(modules.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve modules list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateModulesForm createModulesForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        LessonPlan lessonPlan = lessonPlanRepository.findById(createModulesForm.getLessonPlanId())
                .orElseThrow(() -> new NotFoundException(String.format("Lesson plan with id %s not found", createModulesForm.getLessonPlanId())));
        Modules modules = modulesMapper.fromCreateModulesFormToEntity(createModulesForm);
        modules.setLessonPlan(lessonPlan);
        modulesRepository.save(modules);

        apiMessageDto.setMessage("Create module successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateModuleForm updateModuleForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(updateModuleForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found", updateModuleForm.getId())));
        modulesMapper.fromUpdateModulesFormToEntity(updateModuleForm,modules);
        modulesRepository.save(modules);

        apiMessageDto.setMessage("Update modules successfully");
        return apiMessageDto;
    }
}
