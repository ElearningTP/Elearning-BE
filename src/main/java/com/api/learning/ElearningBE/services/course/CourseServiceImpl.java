package com.api.learning.ElearningBE.services.course;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.mapper.CourseMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.CategoryRepository;
import com.api.learning.ElearningBE.repositories.CourseRepository;
import com.api.learning.ElearningBE.repositories.LessonPlanRepository;
import com.api.learning.ElearningBE.storage.criteria.CourseCriteria;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Category;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

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
    public ApiMessageDto<CourseAdminDto> retrieve(Long id) {
        ApiMessageDto<CourseAdminDto> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", id)));
        CourseAdminDto courseAdminDto = courseMapper.fromEntityToCourseAdminDto(course);

        apiMessageDto.setData(courseAdminDto);
        apiMessageDto.setMessage("Retrieve course successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<CourseAdminDto>>> list(CourseCriteria courseCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CourseAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<CourseAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Course> courses = courseRepository.findAll(courseCriteria.getSpecification(),pageable);
        List<CourseAdminDto> courseDtoS = courseMapper.fromEntityToCourseAdminDtoList(courses.getContent());

        responseListDto.setContent(courseDtoS);
        responseListDto.setTotalElements(courses.getTotalElements());
        responseListDto.setTotalPages(courses.getTotalPages());

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
