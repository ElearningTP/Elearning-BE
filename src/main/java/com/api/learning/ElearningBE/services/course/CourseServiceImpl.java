package com.api.learning.ElearningBE.services.course;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.dto.modules.ModulesAdminDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import com.api.learning.ElearningBE.dto.resources.ResourcesDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.mapper.*;
import com.api.learning.ElearningBE.repositories.*;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.storage.criteria.CourseCriteria;
import com.api.learning.ElearningBE.storage.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private UserService userService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LessonPlanRepository lessonPlanRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private ModulesMapper modulesMapper;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    @Autowired
    private AssignmentSubmissionMapper assignmentSubmissionMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<CourseDto>>> autoComplete(CourseCriteria courseCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CourseDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<CourseDto>> responseListDto = new ResponseListDto<>();
        Page<Course> courses = courseRepository.findAll(courseCriteria.getSpecification(),pageable);
        List<CourseDto> courseDtoS = courseMapper.fromEntityToCourseDtoAutoCompleteList(courses.getContent());

        responseListDto.setContent(courseDtoS);
        responseListDto.setTotalElements(courses.getTotalElements());
        responseListDto.setTotalPages(courses.getTotalPages());

        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<CourseAdminDto> retrieve(Long id) {
        ApiMessageDto<CourseAdminDto> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", id)));
        CourseAdminDto courseAdminDto = courseMapper.fromEntityToCourseAdminDto(course);
        if (course.getLessonPlan() != null){
            List<Modules> modules = modulesRepository.findAllByLessonPlanId(course.getLessonPlan().getId());
            List<Long> modulesIds = modules.stream().map(Modules::getId).collect(Collectors.toList());
            List<ModulesAdminDto> modulesAdminDtoS = modulesMapper.fromEntityToModulesAdminDtoList(modules);

            List<Assignment> assignments = assignmentRepository.findAllByModulesIdIn(modulesIds);
            List<AssignmentDto> assignmentDtoS = assignmentMapper.fromEntityToAssignmentDtoList(assignments);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<AssignmentDto> assignmentDtoList = new ArrayList<>();
                assignmentDtoS.forEach(assignmentDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInAssignment = assignmentDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId,modulesIdInAssignment)){
                        assignmentDtoList.add(assignmentDto);
                    }
                });
                modulesAdminDto.setAssignmentInfo(assignmentDtoList);
            });

            List<Long> assignmentIds = assignments.stream().map(Assignment::getId).collect(Collectors.toList());
            List<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionRepository.findAllByAssignmentIdInAndStudentIdAndCourseId(assignmentIds, userService.getAccountId(),course.getId());
            List<AssignmentSubmissionDto> assignmentSubmissionDtoS = assignmentSubmissionMapper.fromEntityToAssignmentSubmissionDtoList(assignmentSubmissions);
            assignmentDtoS.forEach(assignmentDto -> {
                List<AssignmentSubmissionDto> assignmentSubmissionDtoList = new ArrayList<>();
                assignmentSubmissionDtoS.forEach(assignmentSubmissionDto -> {
                    Long assignmentId = assignmentDto.getId();
                    Long assignmentInInSubmission = assignmentSubmissionDto.getAssignmentInfo().getId();
                    if (Objects.equals(assignmentId, assignmentInInSubmission)){
                        assignmentSubmissionDtoList.add(assignmentSubmissionDto);
                    }
                });
                assignmentDto.setAssignmentSubmissionInfo(assignmentSubmissionDtoList);
            });


            List<Lecture> lectures = lectureRepository.findAllByModulesIdIn(modulesIds);
            List<LectureDto> lectureDtoS = lectureMapper.fromEntityToLectureDtoList(lectures);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<LectureDto> lectureDtoList = new ArrayList<>();
                lectureDtoS.forEach(lectureDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInLecture = lectureDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId,modulesIdInLecture)){
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
                    if (Objects.equals(modulesId,modulesIdInResource)){
                        resourcesDtoList.add(resourcesDto);
                    }
                });
                modulesAdminDto.setResourceInfo(resourcesDtoList);
            });

            courseAdminDto.getLessonPlanInfo().setModulesInfo(modulesAdminDtoS);
        }

        apiMessageDto.setData(courseAdminDto);
        apiMessageDto.setMessage("Retrieve course successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<CourseDto>>> list(CourseCriteria courseCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CourseDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<CourseDto>> responseListDto = new ResponseListDto<>();
        Page<Course> courses = courseRepository.findAll(courseCriteria.getSpecification(),pageable);
        List<CourseDto> courseDtoS = courseMapper.fromEntityToCourseDtoList(courses.getContent());

        responseListDto.setContent(courseDtoS);
        responseListDto.setTotalElements(courses.getTotalElements());
        responseListDto.setTotalPages(courses.getTotalPages());
        responseListDto.setPageIndex(courses.getNumber());
        responseListDto.setPageSize(courses.getSize());

        apiMessageDto.setData(responseListDto);
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateCourseForm createCourseForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(createCourseForm.getTeacherId())
                .orElseThrow(() -> new NotFoundException(String.format("Teacher with id %s not found", createCourseForm.getTeacherId())));
        if (!account.getRole().getKind().equals(ELearningConstant.ROLE_KIND_TEACHER)){
            throw new InvalidException("Account has not to teacher");
        }
        LessonPlan lessonPlan = null;
        if (createCourseForm.getLessonPlanId() != null) {
            lessonPlan = lessonPlanRepository.findById(createCourseForm.getLessonPlanId())
                    .orElseThrow(() -> new NotFoundException(String.format("Lesson plan with id %s not found", createCourseForm.getLessonPlanId())));
        }
        Category category = categoryRepository.findById(createCourseForm.getCategoryId())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", createCourseForm.getCategoryId())));

        Course course = courseMapper.fromCreateCourseFormToEntity(createCourseForm);
        course.setTeacher(account);
        course.setLessonPlan(lessonPlan);
        course.setCategory(category);
        courseRepository.save(course);

        apiMessageDto.setMessage("Create course successfully");
        return apiMessageDto;
    }
}
