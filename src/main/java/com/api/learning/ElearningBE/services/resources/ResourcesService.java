package com.api.learning.ElearningBE.services.resources;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.resources.ResourcesDto;
import com.api.learning.ElearningBE.form.resources.CreateResourcesForm;
import com.api.learning.ElearningBE.form.resources.UpdateResourcesForm;
import com.api.learning.ElearningBE.storage.criteria.ResourcesCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourcesService {
    ApiMessageDto<ResponseListDto<List<ResourcesDto>>> list(ResourcesCriteria resourcesCriteria, Pageable pageable);
    ApiMessageDto<ResourcesDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateResourcesForm createResourcesForm);
    ApiMessageDto<String> update(UpdateResourcesForm updateResourcesForm);
    ApiMessageDto<String> delete(Long id);
}
