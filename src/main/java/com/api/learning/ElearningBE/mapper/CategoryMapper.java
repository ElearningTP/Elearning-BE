package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.category.CategoryAdminDto;
import com.api.learning.ElearningBE.dto.category.CategoryDto;
import com.api.learning.ElearningBE.storage.entities.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @Named("fromEntityToCategoryAdminDtoForGet")
    CategoryAdminDto fromEntityToCategoryAdminDtoForGet(Category category);

    @IterableMapping(elementTargetType = CategoryAdminDto.class, qualifiedByName = "fromEntityToCategoryAdminDtoForGet")
    List<CategoryAdminDto> fromEntityToCategoryAdminDtoList(List<Category> categories);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "categoryName")
    @Named("fromEntityToCategoryDtoAutoComplete")
    CategoryDto fromEntityToCategoryDtoAutoComplete(Category category);
}
