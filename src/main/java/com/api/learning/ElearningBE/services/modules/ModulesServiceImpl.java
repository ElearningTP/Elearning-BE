package com.api.learning.ElearningBE.services.modules;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.dto.modules.ModulesAdminDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.resources.ResourcesDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.modules.CreateModulesForm;
import com.api.learning.ElearningBE.form.modules.UpdateModuleForm;
import com.api.learning.ElearningBE.mapper.*;
import com.api.learning.ElearningBE.repositories.*;
import com.api.learning.ElearningBE.storage.criteria.ModulesCriteria;
import com.api.learning.ElearningBE.storage.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModulesServiceImpl implements ModulesService{

    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private LessonPlanRepository lessonPlanRepository;
    @Autowired
    private ModulesMapper modulesMapper;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizMapper quizMapper;


    @Override
    public ApiMessageDto<ResponseListDto<List<ModulesAdminDto>>> list(ModulesCriteria modulesCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ModulesAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<ModulesAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Modules> modules = modulesRepository.findAll(modulesCriteria.getSpecification(),pageable);
        List<ModulesAdminDto> modulesAdminDtoS = modulesMapper.fromEntityToModulesAdminDtoList(modules.getContent());

        if (!modulesAdminDtoS.isEmpty()) {
//            List<Modules> modules = modulesRepository.findAllByLessonPlanId(modulesCriteria.getLessonPlanId());
            List<Long> modulesIds = modules.getContent().stream().map(Modules::getId).collect(Collectors.toList());
//            List<ModulesAdminDto> modulesAdminDtoS = modulesMapper.fromEntityToModulesAdminDtoList(modules);

            List<Assignment> assignments = assignmentRepository.findAllByModulesIdIn(modulesIds);
            List<AssignmentDto> assignmentDtoS = assignmentMapper.fromEntityToAssignmentDtoList(assignments);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<AssignmentDto> assignmentDtoList = new ArrayList<>();
                assignmentDtoS.forEach(assignmentDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInAssignment = assignmentDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId, modulesIdInAssignment)) {
                        assignmentDtoList.add(assignmentDto);
                    }
                });
                modulesAdminDto.setAssignmentInfo(assignmentDtoList);
            });


            List<Lecture> lectures = lectureRepository.findAllByModulesIdIn(modulesIds);
            List<LectureDto> lectureDtoS = lectureMapper.fromEntityToLectureDtoList(lectures);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<LectureDto> lectureDtoList = new ArrayList<>();
                lectureDtoS.forEach(lectureDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInLecture = lectureDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId, modulesIdInLecture)) {
                        lectureDtoList.add(lectureDto);
                    }
                });
                modulesAdminDto.setLectureInfo(lectureDtoList);
            });

            List<Resources> resources = resourcesRepository.findAllByModulesIdIn(modulesIds);
            List<ResourcesDto> resourcesDtoS = resourcesMapper.fromEntityToResourcesDtoList(resources);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<ResourcesDto> resourcesDtoList = new ArrayList<>();
                resourcesDtoS.forEach(resourcesDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInResource = resourcesDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId, modulesIdInResource)) {
                        resourcesDtoList.add(resourcesDto);
                    }
                });
                modulesAdminDto.setResourceInfo(resourcesDtoList);
            });

            List<Quiz> quizzes = quizRepository.findAllByModulesIdIn(modulesIds);
            List<QuizDto> quizDtoS = quizMapper.fromEntityToQuizDtoList(quizzes);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<QuizDto> quizDtoList = new ArrayList<>();
                quizDtoS.forEach(quizDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInQuiz = quizDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId, modulesIdInQuiz)) {
                        quizDtoList.add(quizDto);
                    }
                });
                modulesAdminDto.setQuizInfo(quizDtoList);
            });
        }

        responseListDto.setContent(modulesAdminDtoS);
        responseListDto.setTotalPages(modules.getTotalPages());
        responseListDto.setTotalElements(modules.getTotalElements());
        responseListDto.setPageSize(modules.getSize());
        responseListDto.setPageIndex(modules.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve modules list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ModulesAdminDto> retrieve(Long id) {
        ApiMessageDto<ModulesAdminDto> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found", id)));
        ModulesAdminDto modulesAdminDto = modulesMapper.fromEntityToModulesAdminDto(modules);

        apiMessageDto.setData(modulesAdminDto);
        apiMessageDto.setMessage("Retrieve modules successfully");
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

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found",id)));
        modulesRepository.delete(modules);

        apiMessageDto.setMessage("Delete modules successfully");
        return apiMessageDto;
    }
}
