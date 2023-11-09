package com.api.learning.ElearningBE.dto.nation;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NationAdminDto extends BaseAdminDto {
    private String nationName;
}
