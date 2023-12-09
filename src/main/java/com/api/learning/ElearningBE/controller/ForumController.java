package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.forum.ForumAdminDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.forum.CreateForumForm;
import com.api.learning.ElearningBE.form.forum.UpdateForumForm;
import com.api.learning.ElearningBE.services.forum.ForumService;
import com.api.learning.ElearningBE.storage.criteria.ForumCriteria;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('FORUM_L')")
    public ApiMessageDto<ResponseListDto<List<ForumDto>>> list(ForumCriteria forumCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<ForumDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = forumService.list(forumCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('FORUM_V')")
    public ApiMessageDto<ForumAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<ForumAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = forumService.retrieve(id);
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
    @PreAuthorize("hasRole('FORUM_C')")
    public ApiMessageDto<ForumDto> create(@Valid @RequestBody CreateForumForm createForumForm){
        ApiMessageDto<ForumDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = forumService.create(createForumForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (InvalidException e) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.BAD_REQUEST.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('FORUM_U')")
    public ApiMessageDto<ForumDto> update(@Valid @RequestBody UpdateForumForm updateForumForm){
        ApiMessageDto<ForumDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = forumService.update(updateForumForm);
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
    @PreAuthorize("hasRole('FORUM_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = forumService.delete(id);
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
