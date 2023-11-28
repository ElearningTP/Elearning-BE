package com.api.learning.ElearningBE.dto.forum;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForumAdminDto extends BaseAdminDto {
    private String forumTitle;
    private String description;
    private CourseDto courseInfo;
    List<TopicDto> topicInfo;
}
