package com.api.learning.ElearningBE.services.student;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.student.CreateStudentForm;
import com.api.learning.ElearningBE.repositories.RoleRepository;
import com.api.learning.ElearningBE.repositories.StudentRepository;
import com.api.learning.ElearningBE.storage.entities.Role;
import com.api.learning.ElearningBE.storage.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;


//    @Override
//    public ApiMessageDto<String> create(CreateStudentForm createStudentForm) {
//        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
//        Role role = roleRepository.findById(createStudentForm.getRoleId())
//                .orElseThrow(() -> new NotFoundException(String.format("Role with id %s not found",createStudentForm.getRoleId())));
//        Student student =
//    }
}
