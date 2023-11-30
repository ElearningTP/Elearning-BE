package com.api.learning.ElearningBE.services.topic_comment;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.topic_comment.CreateTopicCommentForm;
import com.api.learning.ElearningBE.form.topic_comment.UpdateTopicCommentForm;
import com.api.learning.ElearningBE.mapper.TopicCommentMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.TopicCommentRepository;
import com.api.learning.ElearningBE.repositories.TopicRepository;
import com.api.learning.ElearningBE.storage.criteria.TopicCommentCriteria;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Topic;
import com.api.learning.ElearningBE.storage.entities.TopicComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicCommentServiceImpl implements TopicCommentService {

    @Autowired
    private TopicCommentRepository topicCommentRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TopicCommentMapper topicCommentMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<TopicCommentAdminDto>>> list(TopicCommentCriteria topicCommentCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<TopicCommentAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<TopicCommentAdminDto>> responseListDto = new ResponseListDto<>();
        Page<TopicComment> topicComments = topicCommentRepository.findAll(topicCommentCriteria.getSpecification(),pageable);
        List<TopicCommentAdminDto> topicCommentDtoS = topicCommentMapper.fromEntityToTopicCommentAdminDto(topicComments.getContent());

        responseListDto.setPageSize(topicComments.getSize());
        responseListDto.setContent(topicCommentDtoS);
        responseListDto.setPageIndex(topicComments.getNumber());
        responseListDto.setTotalElements(topicComments.getTotalElements());
        responseListDto.setTotalPages(topicComments.getTotalPages());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve topic comment list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<TopicCommentAdminDto> retrieve(Long id) {
        ApiMessageDto<TopicCommentAdminDto> apiMessageDto = new ApiMessageDto<>();
        TopicComment topicComment = topicCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Topic comment with id %s not found", id)));
        TopicCommentAdminDto topicCommentAdminDto = topicCommentMapper.fromEntityToTopicCommentAdminDto(topicComment);

        apiMessageDto.setData(topicCommentAdminDto);
        apiMessageDto.setMessage("Retrieve topic comment successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<TopicCommentAdminDto> create(CreateTopicCommentForm createTopicCommentForm) {
        ApiMessageDto<TopicCommentAdminDto> apiMessageDto = new ApiMessageDto<>();
        Topic topic = topicRepository.findById(createTopicCommentForm.getTopicId())
                .orElseThrow(() -> new NotFoundException(String.format("Topic with id %s not found", createTopicCommentForm.getTopicId())));
        Account account = accountRepository.findById(createTopicCommentForm.getAccountId())
                .orElseThrow(() -> new NotFoundException(String.format("Account with id %s not found", createTopicCommentForm.getAccountId())));
        TopicComment topicComment = topicCommentMapper.fromCreateTopicCommentFormToEntity(createTopicCommentForm);
        topicComment.setTopic(topic);
        topicComment.setAccount(account);
        topicCommentRepository.save(topicComment);
        TopicCommentAdminDto topicCommentAdminDto = topicCommentMapper.fromEntityToTopicCommentAdminDto(topicComment);

        apiMessageDto.setData(topicCommentAdminDto);
        apiMessageDto.setMessage("Create comment successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<TopicCommentAdminDto> update(UpdateTopicCommentForm updateTopicCommentForm) {
        ApiMessageDto<TopicCommentAdminDto> apiMessageDto = new ApiMessageDto<>();
        TopicComment topicComment = topicCommentRepository.findById(updateTopicCommentForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Topic comment with id %s not found", updateTopicCommentForm.getId())));
        topicCommentMapper.fromUpdateTopicCommentFormToEntity(updateTopicCommentForm,topicComment);
        TopicCommentAdminDto topicCommentAdminDto = topicCommentMapper.fromEntityToTopicCommentAdminDto(topicComment);

        apiMessageDto.setData(topicCommentAdminDto);
        apiMessageDto.setMessage("Update comment successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        TopicComment topicComment = topicCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Topic comment with id %s not found", id)));
        topicCommentRepository.delete(topicComment);

        apiMessageDto.setMessage("Delete comment successfully");
        return apiMessageDto;
    }
}
