package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.topic.TopicAdminDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.topic.CreateTopicForm;
import com.api.learning.ElearningBE.form.topic.UpdateTopicForm;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.services.topic.TopicService;
import com.api.learning.ElearningBE.storage.criteria.TopicCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private final TopicService topicService;
    private final UserService userService;

    public TopicController(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('TOPIC_L')")
    public ApiMessageDto<ResponseListDto<List<TopicDto>>> list(TopicCriteria topicCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<TopicDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicService.list(topicCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('TOPIC_V')")
    public ApiMessageDto<TopicAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<TopicAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicService.retrieve(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('TOPIC_C')")
    public ApiMessageDto<TopicDto> create(@Valid @RequestBody CreateTopicForm createTopicForm){
        ApiMessageDto<TopicDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicService.create(createTopicForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('TOPIC_U')")
    public ApiMessageDto<TopicDto> update(@Valid @RequestBody UpdateTopicForm updateTopicForm){
        ApiMessageDto<TopicDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicService.update(updateTopicForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('TOPIC_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicService.delete(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }
}
