package com.api.learning.ElearningBE.dto.resources;

import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;

@Data
public class ResourcesDto {
    private Long id;
    private String title;
    private String urlDocument;
    private ModulesDto modulesInfo;
}
