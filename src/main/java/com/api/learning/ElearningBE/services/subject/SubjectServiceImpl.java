package com.api.learning.ElearningBE.services.subject;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.subject.SubjectAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.subject.CreateSubjectForm;
import com.api.learning.ElearningBE.form.subject.UpdateSubjectForm;
import com.api.learning.ElearningBE.mapper.SubjectMapper;
import com.api.learning.ElearningBE.repositories.SubjectRepository;
import com.api.learning.ElearningBE.storage.criteria.SubjectCriteria;
import com.api.learning.ElearningBE.storage.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<SubjectAdminDto>>> list(SubjectCriteria subjectCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<SubjectAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<SubjectAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Subject> subjects = subjectRepository.findAll(subjectCriteria.getSpecification(), pageable);
        List<SubjectAdminDto> subjectAdminDtoList = subjectMapper.fromEntityToSubjectAdminDtoList(subjects.getContent());

        responseListDto.setContent(subjectAdminDtoList);
        responseListDto.setTotalPages(subjects.getTotalPages());
        responseListDto.setTotalElements(subjects.getTotalElements());
        responseListDto.setPageIndex(subjects.getNumber());
        responseListDto.setPageSize(subjects.getSize());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve subject list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<SubjectAdminDto> retrieve(Long id) {
        ApiMessageDto<SubjectAdminDto> apiMessageDto = new ApiMessageDto<>();
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Subject with id %s not found", id)));
        SubjectAdminDto subjectAdminDto = subjectMapper.fromEntityToSubjectAdminDtoForGet(subject);

        apiMessageDto.setData(subjectAdminDto);
        apiMessageDto.setMessage("Retrieve subject successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateSubjectForm createSubjectForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean existSubjectCode = subjectRepository.existsByCode(createSubjectForm.getSubjectCode().trim());
        if (existSubjectCode){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Subject code %s is existed", createSubjectForm.getSubjectCode()));
            return apiMessageDto;
        }
        Subject subject = subjectMapper.fromCreateSubjectFormToEntity(createSubjectForm);
        subjectRepository.save(subject);

        apiMessageDto.setMessage("Create subject successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateSubjectForm updateSubjectForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean existSubjectCode = subjectRepository.existsByCode(updateSubjectForm.getSubjectCode().trim());
        Subject subject = subjectRepository.findById(updateSubjectForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Subject with id %s not found", updateSubjectForm.getId())));
        if (!subject.getCode().equalsIgnoreCase(updateSubjectForm.getSubjectCode()) && existSubjectCode){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Subject code %s is existed", updateSubjectForm.getSubjectCode()));
            return apiMessageDto;
        }
        subjectMapper.fromUpdateSubjectFormToEntity(updateSubjectForm,subject);
        subjectRepository.save(subject);

        apiMessageDto.setMessage("Update subject successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Subject with id %s not found", id)));
        subjectRepository.delete(subject);

        apiMessageDto.setMessage("Delete subject successfully");
        return apiMessageDto;
    }

}
