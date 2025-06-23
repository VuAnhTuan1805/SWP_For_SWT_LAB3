package com.swpteam.smokingcessation.service.interfaces.blog;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.blog.BlogCreateRequest;
import com.swpteam.smokingcessation.domain.dto.blog.BlogResponse;
import com.swpteam.smokingcessation.domain.dto.blog.BlogUpdateRequest;
import org.springframework.data.domain.Page;

public interface IBlogService {

    Page<BlogResponse> getAllBlogsPage(PageableRequest request);

    Page<BlogResponse> getMyBlogsPage(PageableRequest request);

    Page<BlogResponse> getBlogsPageByCategory(String categoryName, PageableRequest request);

    BlogResponse getBlogBySlug(String slug);

    BlogResponse getBlogById(String id);

    BlogResponse createBlog(BlogCreateRequest request);

    BlogResponse updateBlog(String id, BlogUpdateRequest request);

    void deleteBlogById(String id);
}
