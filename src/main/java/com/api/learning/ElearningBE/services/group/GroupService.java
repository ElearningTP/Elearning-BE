package com.api.learning.ElearningBE.services.group;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.group.CreateGroupForm;

public interface GroupService {
    ApiMessageDto<String> create(CreateGroupForm createGroupForm);
}
