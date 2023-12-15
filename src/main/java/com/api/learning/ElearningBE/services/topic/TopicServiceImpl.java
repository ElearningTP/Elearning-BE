package com.api.learning.ElearningBE.services.topic;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.notification.TopicNotificationMessage;
import com.api.learning.ElearningBE.dto.topic.TopicAdminDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.topic.CreateTopicForm;
import com.api.learning.ElearningBE.form.topic.UpdateTopicForm;
import com.api.learning.ElearningBE.mapper.TopicCommentMapper;
import com.api.learning.ElearningBE.mapper.TopicMapper;
import com.api.learning.ElearningBE.repositories.*;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.services.notitfication.NotifyService;
import com.api.learning.ElearningBE.storage.criteria.TopicCriteria;
import com.api.learning.ElearningBE.storage.entities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;

    @Override
    public ApiMessageDto<ResponseListDto<List<TopicDto>>> list(TopicCriteria topicCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<TopicDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<TopicDto>> responseListDto = new ResponseListDto<>();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate").descending());
        Page<Topic> topics = topicRepository.findAll(topicCriteria.getSpecification(),pageable);
        List<TopicDto> topicDtoS = topicMapper.fromEntityToTopicDtoList(topics.getContent());
        List<Long> topicIds = topics.getContent().stream().map(Topic::getId).collect(Collectors.toList());
        List<TopicComment> topicComments = topicCommentRepository.findAllByTopicIdIn(topicIds);

        Map<Long, List<TopicCommentAdminDto>> commentsMap = topicComments.stream()
                .map(topicCommentMapper::fromEntityToTopicCommentAdminDto)
                .collect(Collectors.groupingByConcurrent(comment -> comment.getTopicInfo().getId()));
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
        createNotificationAndSendMessage(topic);

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


    private void createNotificationAndSendMessage(Topic topic) {
        List<Notification> notifications = new ArrayList<>();
        Long accountId = userService.getAccountId();
        List<Object[]> objectsResult = accountRepository.findAllMemberOfCourseByTopicId(topic.getId(), accountId);
        if (userService.getAccountKind().equals(ELearningConstant.ROLE_KIND_STUDENT)) {
            List<Long> memberOfCourseId = objectsResult.parallelStream().map(objects -> (Long)objects[0]).collect(Collectors.toList());
            Long teacherId = objectsResult.parallelStream().map(objects -> (Long)objects[1]).findFirst().orElse(null);
            if (teacherId != null){
                memberOfCourseId.add(teacherId);
            }
            notifications = notificationMapping(topic,memberOfCourseId);
        }
        if (userService.getAccountKind().equals(ELearningConstant.ROLE_KIND_TEACHER)) {
            List<Long> studentIds = objectsResult.parallelStream().map(objects -> (Long)objects[0]).collect(Collectors.toList());
            notifications = notificationMapping(topic, studentIds);
        }
        notificationRepository.saveAll(notifications);
        for (Notification notification : notifications){
            String jsonMessage = getJsonMessage(topic, notification);
            notification.setMessage(jsonMessage);
        }
        notificationRepository.saveAll(notifications);
        for (Notification notification: notifications){
            notifyService.sendMessage(notification.getMessage(), null, notification.getIdUser());
        }
    }

    private List<Notification> notificationMapping(Topic topic, List<Long> userIds){
        List<Notification> notifications = new ArrayList<>();
        for (Long userId : userIds) {
            Notification notification = new Notification();
            notification.setIdUser(userId);
            notification.setKind(ELearningConstant.NOTIFICATION_KIND_TOPIC);
            notification.setRefId(topic.getId().toString());
            notifications.add(notification);
        }
        return notifications;
    }

    private String getJsonMessage(Topic topic, Notification notification){
        TopicNotificationMessage topicNotificationMessage = new TopicNotificationMessage();
        topicNotificationMessage.setForumId(topic.getForum().getId());
        topicNotificationMessage.setTopicId(topic.getId());
        topicNotificationMessage.setNotificationId(notification.getId());
        topicNotificationMessage.setTopicContent(topic.getContent());
        topicNotificationMessage.setForumTitle(topic.getForum().getTitle());
        topicNotificationMessage.setKind(notification.getKind());
        return convertObjectToJson(topicNotificationMessage);
    }
    public String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
