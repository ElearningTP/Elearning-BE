package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.forum.ForumAdminDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import com.api.learning.ElearningBE.form.forum.CreateForumForm;
import com.api.learning.ElearningBE.form.forum.UpdateForumForm;
import com.api.learning.ElearningBE.storage.entities.Forum;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CourseMapper.class})
public interface ForumMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "forumTitle", target = "title")
    @Mapping(source = "description", target = "description")
    Forum fromCreateForumFormToEntity(CreateForumForm createForumForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "forumTitle", target = "title")
    @Mapping(source = "description", target = "description")
    void fromUpdateForumFormToEntity(UpdateForumForm updateForumForm, @MappingTarget Forum forum);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "forumTitle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "course", target = "courseInfo", qualifiedByName = "fromEntityToCourseDtoAutoComplete")
    @Named("fromEntityToForumDto")
    ForumDto fromEntityToForumDto(Forum forum);
    @IterableMapping(elementTargetType = ForumDto.class, qualifiedByName = "fromEntityToForumDto")
    List<ForumDto> fromEntityToForumDtoList(List<Forum> forums);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "forumTitle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "course", target = "courseInfo", qualifiedByName = "fromEntityToCourseDtoAutoComplete")
    @Named("fromEntityToForumAdminDto")
    ForumAdminDto fromEntityToForumAdminDto(Forum forum);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "forumTitle")
    @Named("fromEntityToForumDtoForAutoComplete")
    ForumDto fromEntityToForumDtoForAutoComplete(Forum forum);
    @IterableMapping(elementTargetType = ForumDto.class, qualifiedByName = "fromEntityToForumDtoForAutoComplete")
    List<ForumDto> fromEntityToForumDtoForAutoCompleteList(List<Forum> forums);
}
