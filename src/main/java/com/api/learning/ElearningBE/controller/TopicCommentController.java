package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.topic_comment.CreateTopicCommentForm;
import com.api.learning.ElearningBE.form.topic_comment.UpdateTopicCommentForm;
import com.api.learning.ElearningBE.services.topic_comment.TopicCommentService;
import com.api.learning.ElearningBE.storage.criteria.TopicCommentCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topic-comment")
public class TopicCommentController {

    private final TopicCommentService topicCommentService;

    public TopicCommentController(TopicCommentService topicCommentService) {
        this.topicCommentService = topicCommentService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('T_COMMENT_L')")
    public ApiMessageDto<ResponseListDto<List<TopicCommentAdminDto>>> list(TopicCommentCriteria topicCommentCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<TopicCommentAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicCommentService.list(topicCommentCriteria,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('T_COMMENT_V')")
    public ApiMessageDto<TopicCommentAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<TopicCommentAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicCommentService.retrieve(id);
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
    @PreAuthorize("hasRole('T_COMMENT_C')")
    public ApiMessageDto<TopicCommentAdminDto> create(@Valid @RequestBody CreateTopicCommentForm createTopicCommentForm){
        ApiMessageDto<TopicCommentAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicCommentService.create(createTopicCommentForm);
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
    @PreAuthorize("hasRole('T_COMMENT_U')")
    public ApiMessageDto<TopicCommentAdminDto> update(@Valid @RequestBody UpdateTopicCommentForm updateTopicCommentForm){
        ApiMessageDto<TopicCommentAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicCommentService.update(updateTopicCommentForm);
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
    @PreAuthorize("hasRole('T_COMMENT_D')")
    public ResponseEntity<ApiMessageDto<String>> delete(@PathVariable Long id){
        ResponseEntity<ApiMessageDto<String>> response;
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = topicCommentService.delete(id);
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.OK);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
