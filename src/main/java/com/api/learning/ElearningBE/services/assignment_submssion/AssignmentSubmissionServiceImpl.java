package com.api.learning.ElearningBE.services.assignment_submssion;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionAdminDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.mapper.AssignmentSubmissionMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.AssignmentRepository;
import com.api.learning.ElearningBE.repositories.AssignmentSubmissionRepository;
import com.api.learning.ElearningBE.repositories.CourseRepository;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.storage.criteria.AssignmentSubmissionCriteria;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Assignment;
import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import com.api.learning.ElearningBE.storage.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService{

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentSubmissionMapper assignmentSubmissionMapper;
    @Autowired
    private UserService userService;

    @Override
    public ApiMessageDto<ResponseListDto<List<AssignmentSubmissionAdminDto>>> list(AssignmentSubmissionCriteria assignmentSubmissionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AssignmentSubmissionAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AssignmentSubmissionAdminDto>> responseListDto = new ResponseListDto<>();
        Page<AssignmentSubmission> assignmentSubmissions = assignmentSubmissionRepository.findAll(assignmentSubmissionCriteria.getSpecification(), pageable);
        List<AssignmentSubmissionAdminDto> assignmentSubmissionDtoS = assignmentSubmissionMapper.fromEntityToAssignmentSubmissionAdminDtoList(assignmentSubmissions.getContent());

        responseListDto.setPageIndex(assignmentSubmissions.getNumber());
        responseListDto.setContent(assignmentSubmissionDtoS);
        responseListDto.setTotalPages(assignmentSubmissions.getTotalPages());
        responseListDto.setPageSize(assignmentSubmissions.getSize());
        responseListDto.setTotalElements(assignmentSubmissions.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve submission list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<AssignmentSubmissionAdminDto> retrieve(Long id) {
        ApiMessageDto<AssignmentSubmissionAdminDto> apiMessageDto = new ApiMessageDto<>();
        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Assignment submission with id %s not found", id)));
        AssignmentSubmissionAdminDto assignmentSubmissionAdminDto = assignmentSubmissionMapper.fromEntityToAssignmentSubmissionAdminDto(assignmentSubmission);

        apiMessageDto.setData(assignmentSubmissionAdminDto);
        apiMessageDto.setMessage("Retrieve submission successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<AssignmentSubmissionDto> submit(CreateAssignmentSubmissionForm createAssignmentSubmissionForm) {
        ApiMessageDto<AssignmentSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        Long studentId = userService.getAccountId();
        Account student = accountRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id %s not found",studentId)));
        Assignment assignment = assignmentRepository.findById(createAssignmentSubmissionForm.getAssignmentId())
                .orElseThrow(() -> new NotFoundException(String.format("Assignment with id %s not found", createAssignmentSubmissionForm.getAssignmentId())));
        Course course = courseRepository.findById(createAssignmentSubmissionForm.getCourseId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", createAssignmentSubmissionForm.getCourseId())));
        Boolean existSubmission = assignmentSubmissionRepository.existsByStudentIdAndAssignmentIdAndCourseId(student.getId(), assignment.getId(), course.getId());
        if (existSubmission){
            throw new InvalidException("You have successfully submitted your assignment. No further submissions are allowed");
        }
        if (Objects.equals(assignment.getAssignmentType(), ELearningConstant.ASSIGNMENT_TYPE_FILE) && createAssignmentSubmissionForm.getFileSubmissionUrl().isEmpty()){
            throw new IllegalArgumentException("File can not be empty");
        }
        if (Objects.equals(assignment.getAssignmentType(), ELearningConstant.ASSIGNMENT_TYPE_TEXT) && createAssignmentSubmissionForm.getTextSubmission().isEmpty()){
            throw new IllegalArgumentException("Content can not be empty");
        }
        AssignmentSubmission assignmentSubmission = assignmentSubmissionMapper.fromCreateAssignmentSubmissionFormToEntity(createAssignmentSubmissionForm);
        assignmentSubmission.setStudent(student);
        assignmentSubmission.setAssignment(assignment);
        assignmentSubmission.setCourse(course);
        assignmentSubmissionRepository.save(assignmentSubmission);
        AssignmentSubmissionDto assignmentSubmissionDto = assignmentSubmissionMapper.fromEntityToAssignmentSubmissionDto(assignmentSubmission);

        apiMessageDto.setData(assignmentSubmissionDto);
        apiMessageDto.setMessage("Submit successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<AssignmentSubmissionDto> update(UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm) {
        ApiMessageDto<AssignmentSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(updateAssignmentSubmissionForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Assignment submission with id %s not found", updateAssignmentSubmissionForm.getId())));
        if (Objects.equals(assignmentSubmission.getAssignment().getAssignmentType(), ELearningConstant.ASSIGNMENT_TYPE_FILE) && updateAssignmentSubmissionForm.getFileSubmissionUrl().isEmpty()){
            throw new IllegalArgumentException("File can not be empty");
        }
        if (Objects.equals(assignmentSubmission.getAssignment().getAssignmentType(), ELearningConstant.ASSIGNMENT_TYPE_TEXT) && updateAssignmentSubmissionForm.getTextSubmission().isEmpty()){
            throw new IllegalArgumentException("Content can not be empty");
        }
        assignmentSubmissionMapper.fromUpdateAssignmentSubmissionFormToEntity(updateAssignmentSubmissionForm,assignmentSubmission);
        assignmentSubmissionRepository.save(assignmentSubmission);
        AssignmentSubmissionDto assignmentSubmissionDto = assignmentSubmissionMapper.fromEntityToAssignmentSubmissionDto(assignmentSubmission);

        apiMessageDto.setData(assignmentSubmissionDto);
        apiMessageDto.setMessage("Update submission successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Assignment submission with id %s not found", id)));
        assignmentSubmissionRepository.delete(assignmentSubmission);

        apiMessageDto.setMessage("Delete submission successfully");
        return apiMessageDto;
    }
}
