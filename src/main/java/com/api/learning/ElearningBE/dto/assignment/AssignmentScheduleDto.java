package com.api.learning.ElearningBE.dto.assignment;


import com.api.learning.ElearningBE.dto.course.CourseDto;
import lombok.Data;

@Data
public class AssignmentScheduleDto {
    private CourseDto courseInfo;
    private AssignmentDto assignmentInfo;
}
