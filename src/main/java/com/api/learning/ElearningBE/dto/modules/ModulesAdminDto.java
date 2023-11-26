package com.api.learning.ElearningBE.dto.modules;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModulesAdminDto extends BaseAdminDto {
    private String modulesName;
    private String description;
    private LessonPlanAdminDto lessonPlanInfo;
    List<LectureDto> lectureInfo;
    List<AssignmentDto> assignmentInfo;
}
