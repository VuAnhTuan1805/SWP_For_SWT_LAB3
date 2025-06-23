package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.comment.CommentCreateRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentReplyRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentResponse;
import com.swpteam.smokingcessation.domain.dto.comment.CommentUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntityFromCreate(CommentCreateRequest request);

    Comment toEntityFromReply(CommentReplyRequest request);

    @Mapping(target = "commentChild", expression = "java(mapChildren(comment.getCommentChild()))")
    @Mapping(source = "blog.id", target = "blogId")
    @Mapping(source = "account.username", target = "username")
    CommentResponse toResponse(Comment comment);

    default List<CommentResponse> mapChildren(List<Comment> children) {
        if (children == null || children.isEmpty()) return new ArrayList<>();
        return children.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    void update(@MappingTarget Comment comment, CommentUpdateRequest request);
}
