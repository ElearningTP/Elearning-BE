package com.api.learning.ElearningBE.services.forum;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.forum.ForumAdminDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.forum.CreateForumForm;
import com.api.learning.ElearningBE.form.forum.UpdateForumForm;
import com.api.learning.ElearningBE.mapper.ForumMapper;
import com.api.learning.ElearningBE.mapper.TopicMapper;
import com.api.learning.ElearningBE.repositories.CourseRepository;
import com.api.learning.ElearningBE.repositories.ForumRepository;
import com.api.learning.ElearningBE.repositories.TopicRepository;
import com.api.learning.ElearningBE.storage.criteria.ForumCriteria;
import com.api.learning.ElearningBE.storage.entities.Course;
import com.api.learning.ElearningBE.storage.entities.Forum;
import com.api.learning.ElearningBE.storage.entities.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService{

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;

    @Override
    @Transactional
    public ApiMessageDto<ResponseListDto<List<ForumDto>>> list(ForumCriteria forumCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<ForumDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<ForumDto>> responseListDto = new ResponseListDto<>();
        Page<Forum> forums = forumRepository.findAll(forumCriteria.getSpecification(),pageable);
        List<ForumDto> forumDtoS = forumMapper.fromEntityToForumDtoList(forums.getContent());

        responseListDto.setContent(forumDtoS);
        responseListDto.setTotalPages(forums.getTotalPages());
        responseListDto.setTotalElements(forums.getTotalElements());
        responseListDto.setPageSize(forums.getSize());
        responseListDto.setPageIndex(forums.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve forum list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ForumAdminDto> retrieve(Long id) {
        ApiMessageDto<ForumAdminDto> apiMessageDto = new ApiMessageDto<>();
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Forum with id %s not found", id)));
        List<Topic> topics = topicRepository.findAllByForumId(forum.getId());
        List<TopicDto> topicDtoS = topicMapper.fromEntityToTopicDtoList(topics);
        ForumAdminDto forumAdminDto = forumMapper.fromEntityToForumAdminDto(forum);
        forumAdminDto.setTopicInfo(topicDtoS);

        apiMessageDto.setData(forumAdminDto);
        apiMessageDto.setMessage("Retrieve forum successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<ForumDto> create(CreateForumForm createForumForm) {
        ApiMessageDto<ForumDto> apiMessageDto = new ApiMessageDto<>();
        Course course = courseRepository.findById(createForumForm.getCourseId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", createForumForm.getCourseId())));
        if (forumRepository.existsByCourseId(course.getId())){
            throw new InvalidException("Course already has a forum. Cannot create another forum for the same course");
        }
        Forum forum = forumMapper.fromCreateForumFormToEntity(createForumForm);
        forum.setCourse(course);
        forumRepository.save(forum);
        ForumDto forumDto = forumMapper.fromEntityToForumDto(forum);

        apiMessageDto.setData(forumDto);
        apiMessageDto.setMessage("Create forum successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ForumDto> update(UpdateForumForm updateForumForm) {
        ApiMessageDto<ForumDto> apiMessageDto = new ApiMessageDto<>();
        Forum forum = forumRepository.findById(updateForumForm.getId())
                .orElseThrow(() -> new RuntimeException(String.format("Forum with id %s not found", updateForumForm.getId())));
        forumMapper.fromUpdateForumFormToEntity(updateForumForm,forum);
        forumRepository.save(forum);
        ForumDto forumDto = forumMapper.fromEntityToForumDto(forum);

        apiMessageDto.setData(forumDto);
        apiMessageDto.setMessage("Update forum successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Forum with id %s not found", id)));
        forumRepository.delete(forum);

        apiMessageDto.setMessage("Delete forum successfully");
        return apiMessageDto;
    }
}
