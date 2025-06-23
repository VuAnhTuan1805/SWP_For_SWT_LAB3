package com.swpteam.smokingcessation.service.interfaces.blog;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.category.CategoryRequest;
import com.swpteam.smokingcessation.domain.dto.category.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponse> getCategoryList();

    Page<CategoryResponse> getCategoryPage(PageableRequest request);

    CategoryResponse getCategoryById(String id);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(String id, CategoryRequest request);

    void deleteCategoryById(String id);
}
