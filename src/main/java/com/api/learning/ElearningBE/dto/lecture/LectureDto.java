package com.api.learning.ElearningBE.dto.lecture;

import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;

@Data
public class LectureDto {
    private Long id;
    private String lectureName;
    private String lectureContent;
    private ModulesDto modulesInfo;
}
