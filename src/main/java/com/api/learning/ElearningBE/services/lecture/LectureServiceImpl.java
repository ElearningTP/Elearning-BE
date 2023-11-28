package com.api.learning.ElearningBE.services.lecture;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.lecture.LectureAdminDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.lecture.CreateLectureForm;
import com.api.learning.ElearningBE.form.lecture.UpdateLectureForm;
import com.api.learning.ElearningBE.mapper.LectureMapper;
import com.api.learning.ElearningBE.repositories.LectureRepository;
import com.api.learning.ElearningBE.repositories.ModulesRepository;
import com.api.learning.ElearningBE.storage.criteria.LectureCriteria;
import com.api.learning.ElearningBE.storage.entities.Lecture;
import com.api.learning.ElearningBE.storage.entities.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService{

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private LectureMapper lectureMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<LectureDto>>> list(LectureCriteria lectureCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<LectureDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<LectureDto>> responseListDto = new ResponseListDto<>();
        Page<Lecture> lectures = lectureRepository.findAll(lectureCriteria.getSpecification(),pageable);
        List<LectureDto> lectureDtoS = lectureMapper.fromEntityToLectureDtoList(lectures.getContent());

        responseListDto.setContent(lectureDtoS);
        responseListDto.setTotalPages(lectures.getTotalPages());
        responseListDto.setTotalElements(lectures.getTotalElements());
        responseListDto.setPageIndex(lectures.getNumber());
        responseListDto.setPageSize(lectures.getSize());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve lecture list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<LectureAdminDto> retrieve(Long id) {
        ApiMessageDto<LectureAdminDto> apiMessageDto = new ApiMessageDto<>();
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lecture with id %s not found", id)));
        LectureAdminDto lectureAdminDto = lectureMapper.fromEntityToLectureAdminDto(lecture);

        apiMessageDto.setData(lectureAdminDto);
        apiMessageDto.setMessage("Retrieve lecture successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateLectureForm createLectureForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(createLectureForm.getModulesId())
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found", createLectureForm.getModulesId())));
        Lecture lecture = lectureMapper.fromCreateLectureToFormToEntity(createLectureForm);
        lecture.setModules(modules);
        lectureRepository.save(lecture);

        apiMessageDto.setMessage("Create lecture successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateLectureForm updateLectureForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Lecture lecture = lectureRepository.findById(updateLectureForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Lecture with id %s not found", updateLectureForm.getId())));
        lectureMapper.fromUpdateLectureFormToEntity(updateLectureForm,lecture);
        lectureRepository.save(lecture);

        apiMessageDto.setMessage("Update lecture successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lecture with id %s not found", id)));
        lectureRepository.delete(lecture);

        apiMessageDto.setMessage("Delete lecture successfully");
        return apiMessageDto;
    }
}
