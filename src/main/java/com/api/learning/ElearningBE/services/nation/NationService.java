package com.api.learning.ElearningBE.services.nation;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.nation.NationAdminDto;
import com.api.learning.ElearningBE.dto.nation.NationDto;
import com.api.learning.ElearningBE.form.nation.CreateNationForm;
import com.api.learning.ElearningBE.form.nation.UpdateNationForm;
import com.api.learning.ElearningBE.storage.criteria.NationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NationService {
    ApiMessageDto<ResponseListDto<List<NationDto>>> autoComplete(NationCriteria nationCriteria, Pageable pageable);
    ApiMessageDto<ResponseListDto<List<NationAdminDto>>> list(NationCriteria nationCriteria, Pageable pageable);
    ApiMessageDto<NationAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateNationForm createNationForm);
    ApiMessageDto<String> update(UpdateNationForm updateNationForm);
}
