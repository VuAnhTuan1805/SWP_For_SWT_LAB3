package com.swpteam.smokingcessation.controller.v1.blog;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.blog.BlogCreateRequest;
import com.swpteam.smokingcessation.domain.dto.blog.BlogResponse;
import com.swpteam.smokingcessation.domain.dto.blog.BlogUpdateRequest;
import com.swpteam.smokingcessation.service.interfaces.blog.IBlogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Blog", description = "Manage blog-related operations")
public class BlogController {

    IBlogService blogService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<BlogResponse>>> getAllBlogsPage(@Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<BlogResponse>>builder()
                        .code(SuccessCode.BLOG_GET_ALL.getCode())
                        .message(SuccessCode.BLOG_GET_ALL.getMessage())
                        .result(blogService.getAllBlogsPage(request))
                        .build()
        );
    }

    @GetMapping("/my-blogs")
    ResponseEntity<ApiResponse<Page<BlogResponse>>> getMyBlogsPage(@Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<BlogResponse>>builder()
                        .code(SuccessCode.MY_BLOG.getCode())
                        .message(SuccessCode.MY_BLOG.getMessage())
                        .result(blogService.getMyBlogsPage(request))
                        .build()
        );
    }

    @GetMapping("/category/{name}")
    ResponseEntity<ApiResponse<Page<BlogResponse>>> getBlogsPageByCategory(@PathVariable String name, @Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<BlogResponse>>builder()
                        .code(SuccessCode.BLOG_GET_BY_CATEGORY.getCode())
                        .message(SuccessCode.BLOG_GET_BY_CATEGORY.getMessage())
                        .result(blogService.getBlogsPageByCategory(name, request))
                        .build()
        );
    }

    @GetMapping("/slug/{slugName}")
    ResponseEntity<ApiResponse<BlogResponse>> getBlogBySlug(@PathVariable String slugName) {
        return ResponseEntity.ok().body(
                ApiResponse.<BlogResponse>builder()
                        .code(SuccessCode.BLOG_GET_BY_SLUG.getCode())
                        .message(SuccessCode.BLOG_GET_BY_SLUG.getMessage())
                        .result(blogService.getBlogBySlug(slugName))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<BlogResponse>> getBlogById(@PathVariable String id) {
        return ResponseEntity.ok().body(
                ApiResponse.<BlogResponse>builder()
                        .code(SuccessCode.BLOG_GET_BY_ID.getCode())
                        .message(SuccessCode.BLOG_GET_BY_ID.getMessage())
                        .result(blogService.getBlogById(id))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<BlogResponse>> createBlog(@RequestBody @Valid BlogCreateRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<BlogResponse>builder()
                        .code(SuccessCode.BLOG_CREATED.getCode())
                        .message(SuccessCode.BLOG_CREATED.getMessage())
                        .result(blogService.createBlog(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<BlogResponse>> updateBlog(@PathVariable String id, @RequestBody @Valid BlogUpdateRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<BlogResponse>builder()
                        .code(SuccessCode.BLOG_UPDATED.getCode())
                        .message(SuccessCode.BLOG_UPDATED.getMessage())
                        .result(blogService.updateBlog(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteBlogById(@PathVariable String id) {
        blogService.deleteBlogById(id);
        return ResponseEntity.ok().body(
                ApiResponse.<String>builder()
                        .code(SuccessCode.BLOG_DELETED.getCode())
                        .message(SuccessCode.BLOG_DELETED.getMessage())
                        .build()
        );
    }
}
