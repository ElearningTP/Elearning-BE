package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.resources.ResourcesDto;
import com.api.learning.ElearningBE.form.resources.CreateResourcesForm;
import com.api.learning.ElearningBE.storage.entities.Resources;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ModulesMapper.class})
public interface ResourcesMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "urlDocument", target = "urlDocument")
    Resources fromCreateResourcesFormToEntity(CreateResourcesForm createResourcesForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "urlDocument", target = "urlDocument")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    @Named("fromEntityToResourcesDto")
    ResourcesDto fromEntityToResourcesDto(Resources resources);

    @IterableMapping(elementTargetType = ResourcesDto.class, qualifiedByName = "fromEntityToResourcesDto")
    List<ResourcesDto> fromEntityToResourcesDtoList(List<Resources> resources);
}
