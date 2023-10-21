package com.api.learning.ElearningBE.services.group;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.group.CreateGroupForm;
import com.api.learning.ElearningBE.form.group.UpdateGroupForm;

public interface GroupService {
    ApiMessageDto<String> create(CreateGroupForm createGroupForm);
    ApiMessageDto<String> update(UpdateGroupForm updateGroupForm);
}
