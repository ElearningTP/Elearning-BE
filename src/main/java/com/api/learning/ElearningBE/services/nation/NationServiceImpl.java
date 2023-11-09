package com.api.learning.ElearningBE.services.nation;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.nation.NationAdminDto;
import com.api.learning.ElearningBE.dto.nation.NationDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.nation.CreateNationForm;
import com.api.learning.ElearningBE.form.nation.UpdateNationForm;
import com.api.learning.ElearningBE.mapper.NationMapper;
import com.api.learning.ElearningBE.repositories.NationRepository;
import com.api.learning.ElearningBE.storage.criteria.NationCriteria;
import com.api.learning.ElearningBE.storage.entities.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationServiceImpl implements NationService{

    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private NationMapper nationMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<NationDto>>> autoComplete(NationCriteria nationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<NationDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<NationDto>> responseListDto = new ResponseListDto<>();
        nationCriteria.setStatus(ELearningConstant.NATION_STATUS_ACTIVE);
        Page<Nation> nations =  nationRepository.findAll(nationCriteria.getSpecification(),pageable);
        List<NationDto> nationDtoS = nationMapper.fromEntityToNationDtoList(nations.getContent());

        responseListDto.setContent(nationDtoS);
        responseListDto.setTotalPages(nations.getTotalPages());
        responseListDto.setTotalElements(nations.getTotalElements());

        apiMessageDto.setData(responseListDto);
        return apiMessageDto;

    }

    @Override
    public ApiMessageDto<ResponseListDto<List<NationAdminDto>>> list(NationCriteria nationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<NationAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<NationAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Nation> nations =  nationRepository.findAll(nationCriteria.getSpecification(),pageable);
        List<NationAdminDto> nationAdminDtoS = nationMapper.fromEntityToNationAdminDtoList(nations.getContent());

        responseListDto.setContent(nationAdminDtoS);
        responseListDto.setTotalPages(nations.getTotalPages());
        responseListDto.setTotalElements(nations.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve nation list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<NationAdminDto> retrieve(Long id) {
        ApiMessageDto<NationAdminDto> apiMessageDto = new ApiMessageDto<>();
        Nation nation = nationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Nation with id %s not found", id)));
        NationAdminDto nationDto = nationMapper.fromEntityToNationAdminDtoForGet(nation);

        apiMessageDto.setData(nationDto);
        apiMessageDto.setMessage("Retrieve nation successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateNationForm createNationForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean existNationName = nationRepository.existsByName(createNationForm.getName().trim());
        if (existNationName){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Nation name %s is existed", createNationForm.getName()));
            return apiMessageDto;
        }
        Nation nation = new Nation();
        nation.setName(createNationForm.getName());
        nationRepository.save(nation);

        apiMessageDto.setMessage("Create nation successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateNationForm updateNationForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Nation nation = nationRepository.findById(updateNationForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Nation with id %s not found", updateNationForm.getId())));
        Boolean existNationName = nationRepository.existsByName(updateNationForm.getName().trim());
        if (!nation.getName().equalsIgnoreCase(updateNationForm.getName()) && existNationName){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Nation name %s is existed", updateNationForm.getName()));
            return apiMessageDto;
        }
        nation.setName(updateNationForm.getName());
        nation.setStatus(updateNationForm.getStatus());
        nationRepository.save(nation);

        apiMessageDto.setResult(false);
        apiMessageDto.setMessage("Update nation successfully");
        return apiMessageDto;
    }
}
