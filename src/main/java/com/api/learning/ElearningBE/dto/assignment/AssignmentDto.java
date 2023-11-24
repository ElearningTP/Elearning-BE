package com.api.learning.ElearningBE.dto.assignment;

import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;

import java.util.Date;

@Data
public class AssignmentDto {
    private Long id;
    private String assignmentTitle;
    private String assignmentContent;
    private Integer assignmentType;
    private Integer state;
    private Date startDate;
    private Date endDate;
    private String urlDocument;
    private ModulesDto modulesInfo;
}
