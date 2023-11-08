package com.api.learning.ElearningBE.dto.subject;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubjectAdminDto extends BaseAdminDto {
    private String subjectName;
    private String subjectCode;
    private String description;
}
