package com.api.learning.ElearningBE.dto.quiz;

import com.api.learning.ElearningBE.dto.course.CourseDto;
import lombok.Data;

@Data
public class QuizScheduleDto {
    private CourseDto courseInfo;
    private QuizDto quizInfo;
}
