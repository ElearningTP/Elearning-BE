package com.api.learning.ElearningBE.services.resources;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.resources.ResourcesDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.resources.CreateResourcesForm;
import com.api.learning.ElearningBE.form.resources.UpdateResourcesForm;
import com.api.learning.ElearningBE.mapper.ResourcesMapper;
import com.api.learning.ElearningBE.repositories.ModulesRepository;
import com.api.learning.ElearningBE.repositories.ResourcesRepository;
import com.api.learning.ElearningBE.services.FileService;
import com.api.learning.ElearningBE.storage.criteria.ResourcesCriteria;
import com.api.learning.ElearningBE.storage.entities.Modules;
import com.api.learning.ElearningBE.storage.entities.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesServiceImpl implements ResourcesService{

    @Autowired
    private ResourcesRepository resourcesRepository;
    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private FileService fileService;

    @Override
    public ApiMessageDto<ResponseListDto<List<ResourcesDto>>> list(ResourcesCriteria resourcesCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ResourcesDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<ResourcesDto>> responseListDto = new ResponseListDto<>();
        Page<Resources> resources = resourcesRepository.findAll(resourcesCriteria.getSpecification(),pageable);
        List<ResourcesDto> resourcesDtoS = resourcesMapper.fromEntityToResourcesDtoList(resources.getContent());

        responseListDto.setContent(resourcesDtoS);
        responseListDto.setTotalPages(resources.getTotalPages());
        responseListDto.setTotalElements(resources.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve resources list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResourcesDto> retrieve(Long id) {
        ApiMessageDto<ResourcesDto> apiMessageDto = new ApiMessageDto<>();
        Resources resources = resourcesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Resources with id %s not found", id)));
        ResourcesDto resourcesDto = resourcesMapper.fromEntityToResourcesDto(resources);

        apiMessageDto.setData(resourcesDto);
        apiMessageDto.setMessage("Retrieve resources successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateResourcesForm createResourcesForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(createResourcesForm.getModulesId())
                .orElseThrow(() -> new NotFoundException(String.format("Modules with ifd %s not found", createResourcesForm.getModulesId())));
        Resources resources = resourcesMapper.fromCreateResourcesFormToEntity(createResourcesForm);
        resources.setModules(modules);
        resourcesRepository.save(resources);

        apiMessageDto.setMessage("Create resources successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateResourcesForm updateResourcesForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Resources resources = resourcesRepository.findById(updateResourcesForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Resources with id %s not found", updateResourcesForm.getId())));
        resources.setTitle(updateResourcesForm.getTitle());
        resourcesRepository.save(resources);

        apiMessageDto.setMessage("Update resources successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Resources resources = resourcesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Resources with id %s not found", id)));
        fileService.deleteFile(resources.getUrlDocument());
        resourcesRepository.delete(resources);

        apiMessageDto.setMessage("Delete resources successfully");
        return apiMessageDto;
    }
}
