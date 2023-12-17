package com.api.learning.ElearningBE.services.assignment;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentAdminDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.assignment.CreateAssignmentForm;
import com.api.learning.ElearningBE.form.assignment.UpdateAssignmentForm;
import com.api.learning.ElearningBE.mapper.AssignmentMapper;
import com.api.learning.ElearningBE.repositories.AssignmentRepository;
import com.api.learning.ElearningBE.repositories.AssignmentSubmissionRepository;
import com.api.learning.ElearningBE.repositories.ModulesRepository;
import com.api.learning.ElearningBE.storage.criteria.AssignmentCriteria;
import com.api.learning.ElearningBE.storage.entities.Assignment;
import com.api.learning.ElearningBE.storage.entities.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Override
    public ApiMessageDto<ResponseListDto<List<AssignmentDto>>> list(AssignmentCriteria assignmentCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AssignmentDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AssignmentDto>> responseListDto = new ResponseListDto<>();
        Page<Assignment> assignments = assignmentRepository.findAll(assignmentCriteria.getSpecification(),pageable);
        List<AssignmentDto> assignmentDtoS = assignmentMapper.fromEntityToAssignmentDtoList(assignments.getContent());

        responseListDto.setContent(assignmentDtoS);
        responseListDto.setTotalPages(assignments.getTotalPages());
        responseListDto.setTotalElements(assignments.getTotalElements());
        responseListDto.setPageIndex(assignments.getNumber());
        responseListDto.setPageSize(assignments.getSize());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve assigment list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<AssignmentAdminDto> retrieve(Long id) {
        ApiMessageDto<AssignmentAdminDto> apiMessageDto = new ApiMessageDto<>();
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Assignment with id %s not found", id)));
        AssignmentAdminDto assignmentAdminDto = assignmentMapper.fromEntityToAssignmentAdminDto(assignment);

        apiMessageDto.setData(assignmentAdminDto);
        apiMessageDto.setMessage("Retrieve assignment successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateAssignmentForm createAssignmentForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(createAssignmentForm.getModulesId())
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found", createAssignmentForm.getModulesId())));
        Assignment assignment = assignmentMapper.fromCreateAssignmentFormToEntity(createAssignmentForm);
        assignment.setModules(modules);
        assignmentRepository.save(assignment);

        apiMessageDto.setMessage("Create assignment successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateAssignmentForm updateAssignmentForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Assignment assignment = assignmentRepository.findById(updateAssignmentForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Assignment with id %s not found", updateAssignmentForm.getId())));
        assignmentMapper.fromUpdateAssignmentFormToEntity(updateAssignmentForm,assignment);
        assignmentRepository.save(assignment);

        apiMessageDto.setMessage("Update assignment successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Assignment with id %s not found", id)));
        assignmentSubmissionRepository.deleteAllByAssignmentId(assignment.getId());
        assignmentRepository.delete(assignment);

        apiMessageDto.setMessage("Delete assignment successfully");
        return apiMessageDto;
    }
}
