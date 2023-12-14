package com.api.learning.ElearningBE.services.modules;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.modules.ModulesAdminDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import com.api.learning.ElearningBE.form.modules.CreateModulesForm;
import com.api.learning.ElearningBE.form.modules.UpdateModuleForm;
import com.api.learning.ElearningBE.storage.criteria.ModulesCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModulesService {
    ApiMessageDto<ResponseListDto<List<ModulesAdminDto>>> list(ModulesCriteria modulesCriteria, Pageable pageable);
    ApiMessageDto<ModulesAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateModulesForm createModulesForm);
    ApiMessageDto<String> update(UpdateModuleForm updateModuleForm);
    ApiMessageDto<String> delete(Long id);
}
