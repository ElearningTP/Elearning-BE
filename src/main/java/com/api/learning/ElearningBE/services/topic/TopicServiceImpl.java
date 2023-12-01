package com.api.learning.ElearningBE.services.topic;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.topic.TopicAdminDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.topic.CreateTopicForm;
import com.api.learning.ElearningBE.form.topic.UpdateTopicForm;
import com.api.learning.ElearningBE.mapper.TopicCommentMapper;
import com.api.learning.ElearningBE.mapper.TopicMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.ForumRepository;
import com.api.learning.ElearningBE.repositories.TopicCommentRepository;
import com.api.learning.ElearningBE.repositories.TopicRepository;
import com.api.learning.ElearningBE.storage.criteria.TopicCriteria;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Forum;
import com.api.learning.ElearningBE.storage.entities.Topic;
import com.api.learning.ElearningBE.storage.entities.TopicComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicCommentRepository topicCommentRepository;
    @Autowired
    private TopicCommentMapper topicCommentMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<TopicDto>>> list(TopicCriteria topicCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<TopicDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<TopicDto>> responseListDto = new ResponseListDto<>();
        Page<Topic> topics = topicRepository.findAll(topicCriteria.getSpecification(),pageable);
        List<TopicDto> topicDtoS = topicMapper.fromEntityToTopicDtoList(topics.getContent());
        List<Long> topicIds = topics.getContent().stream().map(Topic::getId).collect(Collectors.toList());
        List<TopicComment> topicComments = topicCommentRepository.findAllByTopicIdIn(topicIds);
//        List<TopicCommentAdminDto> topicCommentAdminDtoList = topicCommentMapper.fromEntityToTopicCommentAdminDto(topicComments);
//
//        topicDtoS.forEach( topicDto -> {
//            List<TopicCommentAdminDto> topicCommentAdminDtoS = new ArrayList<>();
//            topicCommentAdminDtoList.forEach( topicCommentAdminDto -> {
//                if (Objects.equals(topicDto.getId(), topicCommentAdminDto.getTopicInfo().getId())){
//                    topicCommentAdminDtoS.add(topicCommentAdminDto);
//                }
//            });
//            topicDto.setCommentInfo(topicCommentAdminDtoS);
//        });

//        Map<Long, List<TopicCommentAdminDto>> commentsMap = topicComments.stream()
//                .map(topicCommentMapper::fromEntityToTopicCommentAdminDto)
//                .collect(Collectors.groupingBy(comment -> comment.getTopicInfo().getId()));

        Map<Long, List<TopicCommentAdminDto>> commentsMap = topicComments.parallelStream()
                .map(topicCommentMapper::fromEntityToTopicCommentAdminDto)
                .collect(Collectors.toMap(
                        comment -> comment.getTopicInfo().getId(),
                        Collections::singletonList,
                        (existingList, newList) -> {
                            List<TopicCommentAdminDto> mergedList = new ArrayList<>(existingList);
                            mergedList.addAll(newList);
                            return mergedList;
                        }
                ));
        List<TopicDto> topicDtoList = topicDtoS.parallelStream()
                .peek(topicDto -> {
                    Long topicId = topicDto.getId();
                    commentsMap.computeIfAbsent(topicId, k -> Collections.emptyList());
                    topicDto.setCommentInfo(commentsMap.get(topicId));
                })
                .collect(Collectors.toList());

        responseListDto.setTotalPages(topics.getTotalPages());
        responseListDto.setContent(topicDtoList);
        responseListDto.setTotalElements(topics.getTotalElements());
        responseListDto.setPageIndex(topics.getNumber());
        responseListDto.setPageSize(topics.getSize());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve topic list success;");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<TopicAdminDto> retrieve(Long id) {
        ApiMessageDto<TopicAdminDto> apiMessageDto = new ApiMessageDto<>();
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Topic with id %s not found",id)));
        TopicAdminDto topicAdminDto = topicMapper.fromEntityToTopicAdminDto(topic);

        apiMessageDto.setData(topicAdminDto);
        apiMessageDto.setMessage("Retrieve topic successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<TopicDto> create(CreateTopicForm createTopicForm) {
        ApiMessageDto<TopicDto> apiMessageDto = new ApiMessageDto<>();
        Forum forum = forumRepository.findById(createTopicForm.getForumId())
                .orElseThrow(() -> new NotFoundException(String.format("Forum with id %s not found", createTopicForm.getForumId())));
        Account account = accountRepository.findById(createTopicForm.getAccountId())
                .orElseThrow(() -> new NotFoundException(String.format("Account with id %s not found", createTopicForm.getAccountId())));
        Topic topic = topicMapper.fromCreateTopicFormToEntity(createTopicForm);
        topic.setForum(forum);
        topic.setAccount(account);
        topicRepository.save(topic);
        TopicDto topicDto = topicMapper.fromEntityToTopicDto(topic);

        apiMessageDto.setData(topicDto);
        apiMessageDto.setMessage("Create topic successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<TopicDto> update(UpdateTopicForm updateTopicForm) {
        ApiMessageDto<TopicDto> apiMessageDto = new ApiMessageDto<>();
        Topic topic = topicRepository.findById(updateTopicForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Topic with id %s not found", updateTopicForm.getId())));
        topicMapper.fromUpdateTopicFormToEntity(updateTopicForm,topic);
        topicRepository.save(topic);
        TopicDto topicDto = topicMapper.fromEntityToTopicDto(topic);

        apiMessageDto.setData(topicDto);
        apiMessageDto.setMessage("Update topic successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Topic with id %s not found",id)));
        topicRepository.delete(topic);

        apiMessageDto.setMessage("Delete topic successfully");
        return apiMessageDto;
    }
}
