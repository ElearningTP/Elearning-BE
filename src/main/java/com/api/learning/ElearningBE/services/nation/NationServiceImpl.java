package com.api.learning.ElearningBE.services.nation;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.nation.CreateNationForm;
import com.api.learning.ElearningBE.repositories.NationRepository;
import com.api.learning.ElearningBE.storage.entities.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NationServiceImpl implements NationService{

    @Autowired
    private NationRepository nationRepository;
    @Override
    public ApiMessageDto<String> create(CreateNationForm createNationForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Nation nation = new Nation();
        nation.setName(createNationForm.getName());
        nationRepository.save(nation);

        apiMessageDto.setMessage("Create nation successfully");
        return apiMessageDto;
    }
}
