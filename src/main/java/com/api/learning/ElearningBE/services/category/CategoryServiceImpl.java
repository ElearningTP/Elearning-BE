package com.api.learning.ElearningBE.services.category;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.category.CategoryAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.category.CreateCategoryForm;
import com.api.learning.ElearningBE.form.category.UpdateCategoryForm;
import com.api.learning.ElearningBE.mapper.CategoryMapper;
import com.api.learning.ElearningBE.repositories.CategoryRepository;
import com.api.learning.ElearningBE.storage.criteria.CategoryCriteria;
import com.api.learning.ElearningBE.storage.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<CategoryAdminDto>>> list(CategoryCriteria categoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<CategoryAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<CategoryAdminDto>> responseListDto = new ResponseListDto<>();
        Page<Category> categories = categoryRepository.findAll(categoryCriteria.getSpecification(),pageable);
        List<CategoryAdminDto> categoryAdminDtoS = categoryMapper.fromEntityToCategoryAdminDtoList(categories.getContent());

        responseListDto.setContent(categoryAdminDtoS);
        responseListDto.setTotalPages(categories.getTotalPages());
        responseListDto.setTotalElements(categories.getTotalElements());
        responseListDto.setPageIndex(categories.getNumber());
        responseListDto.setPageSize(categories.getSize());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve category list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<CategoryAdminDto> retrieve(Long id) {
        ApiMessageDto<CategoryAdminDto> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", id)));
        CategoryAdminDto categoryAdminDto = categoryMapper.fromEntityToCategoryAdminDtoForGet(category);

        apiMessageDto.setData(categoryAdminDto);
        apiMessageDto.setMessage("Retrieve category successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateCategoryForm createCategoryForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean existCategoryName = categoryRepository.existsByName(createCategoryForm.getName());
        if (existCategoryName){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Category name %s is existed", createCategoryForm.getName()));
            return apiMessageDto;
        }
        Category category = new Category();
        category.setName(createCategoryForm.getName());
        categoryRepository.save(category);

        apiMessageDto.setMessage("Create category successfully");
        return apiMessageDto;

    }

    @Override
    public ApiMessageDto<String> update(UpdateCategoryForm updateCategoryForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean existCategoryName = categoryRepository.existsByName(updateCategoryForm.getName());
        Category category = categoryRepository.findById(updateCategoryForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", updateCategoryForm.getId())));
        if (!category.getName().equalsIgnoreCase(updateCategoryForm.getName()) && existCategoryName){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Category name %s is existed", updateCategoryForm.getName()));
            return apiMessageDto;
        }
        category.setName(updateCategoryForm.getName());
        category.setStatus(updateCategoryForm.getStatus());
        categoryRepository.save(category);

        apiMessageDto.setMessage("Update category successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", id)));
        categoryRepository.delete(category);

        apiMessageDto.setMessage("Delete category successfully");
        return apiMessageDto;
    }
}
