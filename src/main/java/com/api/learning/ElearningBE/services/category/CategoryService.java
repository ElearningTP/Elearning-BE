package com.api.learning.ElearningBE.services.category;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.category.CategoryAdminDto;
import com.api.learning.ElearningBE.form.category.CreateCategoryForm;
import com.api.learning.ElearningBE.form.category.UpdateCategoryForm;
import com.api.learning.ElearningBE.storage.criteria.CategoryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    ApiMessageDto<ResponseListDto<List<CategoryAdminDto>>> list(CategoryCriteria categoryCriteria, Pageable pageable);
    ApiMessageDto<CategoryAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateCategoryForm createCategoryForm);
    ApiMessageDto<String> update(UpdateCategoryForm updateCategoryForm);
    ApiMessageDto<String> delete(Long id);
}
