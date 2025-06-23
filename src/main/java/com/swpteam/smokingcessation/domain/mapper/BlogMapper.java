package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.blog.BlogCreateRequest;
import com.swpteam.smokingcessation.domain.dto.blog.BlogResponse;
import com.swpteam.smokingcessation.domain.dto.blog.BlogUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    Blog toEntity(BlogCreateRequest request);

    @Mapping(source = "account.username", target = "authorName")
    @Mapping(source = "category.name", target = "categoryName")
    BlogResponse toResponse(Blog blog);

    void update(@MappingTarget Blog blog, BlogUpdateRequest request);
}
