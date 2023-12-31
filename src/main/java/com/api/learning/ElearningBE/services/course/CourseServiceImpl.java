package com.api.learning.ElearningBE.services.course;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.dto.forum.ForumAdminDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.dto.modules.ModulesAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.resources.ResourcesDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.form.course.UpdateCourseForm;
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

import java.util.*;
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
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    private QuizSubmissionMapper quizSubmissionMapper;
    @Autowired
    private QuizSubmissionResultRepository quizSubmissionResultRepository;
    @Autowired
    private TopicCommentRepository topicCommentRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

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

            List<Quiz> quizzes = quizRepository.findAllByModulesIdIn(modulesIds);
            List<QuizDto> quizDtoS = quizMapper.fromEntityToQuizDtoList(quizzes);
            modulesAdminDtoS.forEach(modulesAdminDto -> {
                List<QuizDto> quizDtoList = new ArrayList<>();
                quizDtoS.forEach(quizDto -> {
                    Long modulesId = modulesAdminDto.getId();
                    Long modulesIdInQuiz = quizDto.getModulesInfo().getId();
                    if (Objects.equals(modulesId,modulesIdInQuiz)){
                        quizDtoList.add(quizDto);
                    }
                });
                modulesAdminDto.setQuizInfo(quizDtoList);
            });

            Long accountId = userService.getAccountId();
            List<Long> quizIds = quizzes.stream().map(Quiz::getId).collect(Collectors.toList());
            Map<Long,List<QuizSubmission>> quizSubmissionsMap = quizSubmissionRepository.findAllByStudentIdAndCourseIdAndQuizIdIn(accountId, course.getId(), quizIds).stream()
                    .collect(Collectors.groupingByConcurrent(quizSubmission -> quizSubmission.getQuiz().getId()));
            quizDtoS.forEach(quizDto -> {
                List<QuizSubmissionDto> quizSubmissionDtoS = quizSubmissionMapper.fromEntityToQuizSubmissionDtoList(quizSubmissionsMap.getOrDefault(quizDto.getId(), Collections.emptyList()));
                quizDto.setQuizSubmissionInfo(quizSubmissionDtoS);
            });

            courseAdminDto.getLessonPlanInfo().setModulesInfo(modulesAdminDtoS);
        }
        Forum forum = forumRepository.findByCourseId(course.getId());
        ForumAdminDto forumAdminDto = forumMapper.fromEntityToForumAdminDto(forum);
        courseAdminDto.setForumInfo(forumAdminDto);



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
    public ApiMessageDto<CourseDto> create(CreateCourseForm createCourseForm) {
        ApiMessageDto<CourseDto> apiMessageDto = new ApiMessageDto<>();
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
        CourseDto courseDto = courseMapper.fromEntityToCourseDto(course);

        Forum forum = new Forum();
        forum.setTitle(course.getName());
        forum.setCourse(course);
        forumRepository.save(forum);

        apiMessageDto.setData(courseDto);
        apiMessageDto.setMessage("Create course successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<CourseDto> update(UpdateCourseForm updateCourseForm) {
        ApiMessageDto<CourseDto> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(updateCourseForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", updateCourseForm.getId())));
        courseMapper.fromUpdateCourseFormToEntity(updateCourseForm, course);

        if (updateCourseForm.getTeacherId() != null){
            Account teacher = accountRepository.findById(updateCourseForm.getTeacherId())
                    .orElseThrow(() -> new NotFoundException(String.format("Teacher with id %s not found", updateCourseForm.getTeacherId())));
            course.setTeacher(teacher);
        }
        if (updateCourseForm.getLessonPlanId() != null){
            LessonPlan lessonPlan = lessonPlanRepository.findById(updateCourseForm.getLessonPlanId())
                    .orElseThrow(() -> new NotFoundException(String.format("Lesson plan id %s not found", updateCourseForm.getLessonPlanId())));
            course.setLessonPlan(lessonPlan);
        }
        if (updateCourseForm.getCategoryId() != null){
            Category category = categoryRepository.findById(updateCourseForm.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id %s can not be null", updateCourseForm.getCategoryId())));
            course.setCategory(category);
        }
        courseRepository.save(course);
        CourseDto courseDto = courseMapper.fromEntityToCourseDto(course);

        apiMessageDto.setData(courseDto);
        apiMessageDto.setMessage("Update course successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", id)));
        quizSubmissionResultRepository.deleteAllByCourseId(course.getId());
        quizSubmissionRepository.deleteAllByCourseId(course.getId());
        topicCommentRepository.deleteAllByCourseId(course.getId());
        topicRepository.deleteAllByCourseId(course.getId());
        forumRepository.deleteAllByCourseId(course.getId());
        assignmentSubmissionRepository.deleteAllByCourseId(course.getId());
        courseRegistrationRepository.deleteAllByCourseId(course.getId());
        courseRepository.delete(course);

        apiMessageDto.setMessage("Delete course successfully");
        return apiMessageDto;
    }
}
