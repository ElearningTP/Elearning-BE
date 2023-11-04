package com.api.learning.ElearningBE.security.impl;

import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.StudentRepository;
import com.api.learning.ElearningBE.repositories.TeacherRepository;
import com.api.learning.ElearningBE.storage.entities.Student;
import com.api.learning.ElearningBE.storage.entities.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public UserDetailsImpl loadUserByUsername(String emailStudent) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(emailStudent);
        if (student == null){
            throw new NotFoundException("Email not found");
        }
        Set<GrantedAuthority> grantedAuthorities = grantedAuthorityStudent(student);
        return new UserDetailsImpl(student.getName(), student.getEmail(), student.getPassword(), grantedAuthorities, student.getStatus(), "", student.getRole().getKind());
    }
    public UserDetailsImpl loadTeacherByEmail(String emailTeacher) throws UsernameNotFoundException{
        Teacher teacher = teacherRepository.findByEmail(emailTeacher);
        if (teacher == null){
            throw new NotFoundException("Email not found");
        }
        Set<GrantedAuthority> grantedAuthorities = grantedAuthorityTeacher(teacher);
        return new UserDetailsImpl(teacher.getName(), teacher.getEmail(), teacher.getPassword(), grantedAuthorities, teacher.getStatus(), "teacher.getAvatarPath()", teacher.getRole().getKind());
    }
    private Set<GrantedAuthority> grantedAuthorityStudent(Student student){
        List<String> roles = new ArrayList<>();
        student.getRole().getPermissions()
                .stream()
                .filter(permission -> permission.getPermissionCode() != null)
                .forEach(pName -> roles.add(pName.getPermissionCode()));
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())).collect(Collectors.toSet());
    }
    private Set<GrantedAuthority> grantedAuthorityTeacher(Teacher teacher){
        List<String> roles = new ArrayList<>();
        teacher.getRole().getPermissions()
                .stream()
                .filter(permission -> permission.getPermissionCode() != null)
                .forEach(pName -> roles.add(pName.getPermissionCode()));
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())).collect(Collectors.toSet());
    }
}
