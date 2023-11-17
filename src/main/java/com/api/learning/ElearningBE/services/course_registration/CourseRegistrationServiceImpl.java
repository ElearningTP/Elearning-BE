package com.api.learning.ElearningBE.services.course_registration;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.course_registration.CourseRegistrationDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.course_registration.CreateCourseRegistrationForm;
import com.api.learning.ElearningBE.mapper.CourseRegistrationMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.CourseRegistrationRepository;
import com.api.learning.ElearningBE.repositories.CourseRepository;
import com.api.learning.ElearningBE.storage.criteria.CourseRegistrationCriteria;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.CourseRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseRegistrationServiceImpl implements CourseRegistrationService{

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseRegistrationMapper courseRegistrationMapper;

//    @Override
//    public ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> autoComplete(CourseRegistrationCriteria courseRegistrationCriteria, Pageable pageable) {
//        ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> apiMessageDto = new ApiMessageDto<>();
//        ResponseListDto<List<CourseRegistrationDto>> responseListDto = new ResponseListDto<>();
//        Page<CourseRegistration> courseRegistrations = courseRegistrationRepository.findAll(courseRegistrationCriteria.getSpecification(),pageable);
//        List<CourseRegistrationDto> courseRegistrationDtoS = courseRegistrationMapper.fromEntityToCourseRegistrationDtoList(courseRegistrations.getContent());
//
//        responseListDto.setContent(courseRegistrationDtoS);
//        responseListDto.setTotalElements(courseRegistrations.getTotalElements());
//        responseListDto.setTotalPages(courseRegistrations.getTotalPages());
//
//        apiMessageDto.setData(responseListDto);
//        return apiMessageDto;
//    }

    @Override
    public ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> list(CourseRegistrationCriteria courseRegistrationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<CourseRegistrationDto>> responseListDto = new ResponseListDto<>();
        Page<CourseRegistration> courseRegistrations = courseRegistrationRepository.findAll(courseRegistrationCriteria.getSpecification(),pageable);
        List<CourseRegistrationDto> courseRegistrationDtoS = courseRegistrationMapper.fromEntityToCourseRegistrationDtoList(courseRegistrations.getContent());

        responseListDto.setContent(courseRegistrationDtoS);
        responseListDto.setTotalElements(courseRegistrations.getTotalElements());
        responseListDto.setTotalPages(courseRegistrations.getTotalPages());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateCourseRegistrationForm createCourseRegistrationForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account student = accountRepository.findById(createCourseRegistrationForm.getStudentId())
                .orElseThrow(() -> new NotFoundException(String.format("Student with id %s not found",createCourseRegistrationForm.getStudentId())));
        Course course = courseRepository.findById(createCourseRegistrationForm.getCourseId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", createCourseRegistrationForm.getCourseId())));

        Long studentId = createCourseRegistrationForm.getStudentId();
        Long courseId = createCourseRegistrationForm.getCourseId();
        Boolean existStudent = courseRegistrationRepository.existsByStudentIdAndCourseId(studentId,courseId);
        if (existStudent){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Student has enrolled this course");
            return apiMessageDto;
        }

        CourseRegistration courseRegistration = new CourseRegistration();
        courseRegistration.setStudent(student);
        courseRegistration.setCourse(course);
        courseRegistrationRepository.save(courseRegistration);

        apiMessageDto.setMessage("Create successfully");
        return apiMessageDto;
    }
}
