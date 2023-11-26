package com.api.learning.ElearningBE.services.assignment_submssion;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.mapper.AssignmentSubmissionMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.AssignmentRepository;
import com.api.learning.ElearningBE.repositories.AssignmentSubmissionRepository;
import com.api.learning.ElearningBE.repositories.CourseRepository;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Assignment;
import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import com.api.learning.ElearningBE.storage.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public ApiMessageDto<String> submit(CreateAssignmentSubmissionForm createAssignmentSubmissionForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account student = accountRepository.findById(createAssignmentSubmissionForm.getStudentId())
                .orElseThrow(() -> new NotFoundException(String.format("Student with id %s not found", createAssignmentSubmissionForm.getStudentId())));
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

        apiMessageDto.setMessage("Submit successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<String> update(UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
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
