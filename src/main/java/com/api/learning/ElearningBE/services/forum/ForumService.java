package com.api.learning.ElearningBE.services.forum;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.forum.ForumAdminDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import com.api.learning.ElearningBE.form.forum.CreateForumForm;
import com.api.learning.ElearningBE.form.forum.UpdateForumForm;
import com.api.learning.ElearningBE.storage.criteria.ForumCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ForumService {
    ApiMessageDto<ResponseListDto<List<ForumDto>>> list(ForumCriteria forumCriteria, Pageable pageable);
    ApiMessageDto<ForumAdminDto> retrieve(Long id);
    ApiMessageDto<ForumDto> create(CreateForumForm createForumForm);
    ApiMessageDto<ForumDto> update(UpdateForumForm updateForumForm);
    ApiMessageDto<String> delete(Long id);
}
