package com.api.learning.ElearningBE.dto.forum;

import com.api.learning.ElearningBE.dto.course.CourseDto;
import lombok.Data;

@Data
public class ForumDto {
    private Long id;
    private String forumTitle;
    private String description;
    private CourseDto courseInfo;
}
