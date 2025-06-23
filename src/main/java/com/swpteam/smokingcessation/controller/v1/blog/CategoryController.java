package com.swpteam.smokingcessation.controller.v1.blog;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.category.CategoryRequest;
import com.swpteam.smokingcessation.domain.dto.category.CategoryResponse;
import com.swpteam.smokingcessation.service.interfaces.blog.ICategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category", description = "Manage category-related operations")
public class CategoryController {

    ICategoryService categoryService;

    @GetMapping("/list-all")
    ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoryList(@Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<List<CategoryResponse>>builder()
                        .code(SuccessCode.CATEGORY_LIST_ALL.getCode())
                        .message(SuccessCode.CATEGORY_LIST_ALL.getMessage())
                        .result(categoryService.getCategoryList())
                        .build()
        );
    }

    @GetMapping
    ResponseEntity<ApiResponse<Page<CategoryResponse>>> getCategoryPage(@Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<CategoryResponse>>builder()
                        .code(SuccessCode.CATEGORY_GET_ALL.getCode())
                        .message(SuccessCode.CATEGORY_GET_ALL.getMessage())
                        .result(categoryService.getCategoryPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok().body(
                ApiResponse.<CategoryResponse>builder()
                        .code(SuccessCode.CATEGORY_GET_BY_ID.getCode())
                        .message(SuccessCode.CATEGORY_GET_BY_ID.getMessage())
                        .result(categoryService.getCategoryById(id))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<CategoryResponse>builder()
                        .code(SuccessCode.CATEGORY_CREATED.getCode())
                        .message(SuccessCode.CATEGORY_CREATED.getMessage())
                        .result(categoryService.createCategory(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable String id, @RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<CategoryResponse>builder()
                        .code(SuccessCode.CATEGORY_UPDATED.getCode())
                        .message(SuccessCode.CATEGORY_UPDATED.getMessage())
                        .result(categoryService.updateCategory(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteCategoryById(@PathVariable String id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().body(
                ApiResponse.<String>builder()
                        .code(SuccessCode.CATEGORY_DELETED.getCode())
                        .message(SuccessCode.CATEGORY_DELETED.getMessage())
                        .build()
        );
    }
}
