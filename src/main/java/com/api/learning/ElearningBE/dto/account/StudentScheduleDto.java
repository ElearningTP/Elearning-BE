package com.api.learning.ElearningBE.dto.account;

import com.api.learning.ElearningBE.dto.assignment.AssignmentScheduleDto;
import com.api.learning.ElearningBE.dto.quiz.QuizScheduleDto;
import lombok.Data;

import java.util.List;

@Data
public class StudentScheduleDto {
    private List<AssignmentScheduleDto> assignmentsInfo;
    private List<QuizScheduleDto> quizzesInfo;
}
