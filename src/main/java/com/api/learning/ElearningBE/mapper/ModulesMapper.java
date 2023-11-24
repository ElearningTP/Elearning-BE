package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import com.api.learning.ElearningBE.form.modules.CreateModulesForm;
import com.api.learning.ElearningBE.form.modules.UpdateModuleForm;
import com.api.learning.ElearningBE.storage.entities.Modules;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {LessonPlanMapper.class})
public interface ModulesMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "modulesName", target = "name")
    @Mapping(source = "description", target = "description")
    Modules fromCreateModulesFormToEntity(CreateModulesForm createModulesForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "modulesName", target = "name")
    @Mapping(source = "description", target = "description")
    void fromUpdateModulesFormToEntity(UpdateModuleForm updateModuleForm, @MappingTarget Modules modules);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "modulesName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "lessonPlan", target = "lessonPlanInfo", qualifiedByName = "fromEntityToLessonPlanDto")
    @Named("fromEntityToModulesDto")
    ModulesDto fromEntityToModulesDto(Modules modules);
    @IterableMapping(elementTargetType = ModulesDto.class, qualifiedByName = "fromEntityToModulesDto")
    List<ModulesDto> fromEntityToModulesDtoList(List<Modules> modules);
}
