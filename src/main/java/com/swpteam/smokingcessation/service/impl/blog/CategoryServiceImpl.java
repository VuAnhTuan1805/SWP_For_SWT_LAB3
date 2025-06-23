package com.swpteam.smokingcessation.service.impl.blog;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.App;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.category.CategoryRequest;
import com.swpteam.smokingcessation.domain.dto.category.CategoryResponse;
import com.swpteam.smokingcessation.domain.entity.Blog;
import com.swpteam.smokingcessation.domain.entity.Category;
import com.swpteam.smokingcessation.domain.entity.Comment;
import com.swpteam.smokingcessation.domain.mapper.CategoryMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.BlogRepository;
import com.swpteam.smokingcessation.repository.CategoryRepository;
import com.swpteam.smokingcessation.service.interfaces.blog.ICategoryService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements ICategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    BlogRepository blogRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<CategoryResponse> getCategoryList() {
        return categoryRepository.findAll(Sort.by("name")).stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CategoryResponse> getCategoryPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Category.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(categoryMapper::toResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse getCategoryById(String id) {
        Category category = findCategoryById(id);
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "CATEGORY_CACHE", key = "#result.getId()")
    public CategoryResponse createCategory(CategoryRequest request) {
        findCategoryByName(request.getName());

        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "CATEGORY_CACHE", key = "#result.getId()")
    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        Category category = findCategoryById(id);

        categoryMapper.update(category, request);

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "CATEGORY_CACHE", key = "#id", allEntries = true)
    public void deleteCategoryById(String id) {
        Category category = findCategoryById(id);

        if (category.getName().equalsIgnoreCase(App.DEFAULT_CATEGORY)) {
            throw new AppException(ErrorCode.CATEGORY_CANNOT_BE_DELETED);
        }

        Category uncategorized = findCategoryByName(App.DEFAULT_CATEGORY);

        List<Blog> blogs = blogRepository.findByCategoryId(category.getId());
        blogs.forEach(blog -> blog.setCategory(uncategorized));

        blogRepository.saveAll(blogs);

        categoryRepository.deleteById(id);
    }

    @Cacheable(value = "CATEGORY_CACHE", key = "#name")
    private Category findCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Cacheable(value = "CATEGORY_CACHE", key = "#id")
    private Category findCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
