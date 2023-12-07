package com.api.learning.ElearningBE.dto.assignment;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentAdminDto extends BaseAdminDto {
    private String assignmentTitle;
    private String assignmentContent;
    private Integer state;
    private Date startDate;
    private Date endDate;
    private String urlDocument;
    private ModulesDto modulesInfo;
}
