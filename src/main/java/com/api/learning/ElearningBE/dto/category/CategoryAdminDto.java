package com.api.learning.ElearningBE.dto.category;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryAdminDto extends BaseAdminDto {
    private String name;
}
