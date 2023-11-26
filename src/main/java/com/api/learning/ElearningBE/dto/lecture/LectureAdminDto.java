package com.api.learning.ElearningBE.dto.lecture;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LectureAdminDto extends BaseAdminDto {
    private String lectureName;
    private String lectureContent;
    private ModulesDto modulesInfo;
}
