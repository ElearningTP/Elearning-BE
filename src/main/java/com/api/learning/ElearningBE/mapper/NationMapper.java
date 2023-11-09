package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.nation.NationAdminDto;
import com.api.learning.ElearningBE.dto.nation.NationDto;
import com.api.learning.ElearningBE.storage.entities.Nation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NationMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "nationName")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @Named("fromEntityToNationAdminDtoForGet")
    NationAdminDto fromEntityToNationAdminDtoForGet(Nation nation);
    @IterableMapping(elementTargetType = NationAdminDto.class, qualifiedByName = "fromEntityToNationAdminDtoForGet")
    List<NationAdminDto> fromEntityToNationAdminDtoList(List<Nation> nations);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "nationName")
    @Mapping(source = "status", target = "status")
    @Named("fromEntityToNationDto")
    NationDto fromEntityToNationDto(Nation nation);
    @IterableMapping(elementTargetType = NationDto.class, qualifiedByName = "fromEntityToNationDto")
    List<NationDto> fromEntityToNationDtoList(List<Nation> nations);
}
