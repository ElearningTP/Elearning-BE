package com.api.learning.ElearningBE.form.student;

import lombok.Data;

@Data
public class CreateStudentForm {
    private String nameStudent;
    private String emailStudent;
    private String nation;
    private String password;
    private Long roleId;
}
