package com.api.learning.ElearningBE.services.nation;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.nation.CreateNationForm;

public interface NationService {
    ApiMessageDto<String> create(CreateNationForm createNationForm);
}
