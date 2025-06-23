package com.swpteam.smokingcessation.service.interfaces.blog;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentCreateRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentReplyRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentResponse;
import com.swpteam.smokingcessation.domain.dto.comment.CommentUpdateRequest;
import org.springframework.data.domain.Page;

public interface ICommentService {

    Page<CommentResponse> getCommentsByBlogId(String blogId, PageableRequest request);

    Page<CommentResponse> getCommentPage(PageableRequest request);

    CommentResponse getCommentById(String id);

    CommentResponse createComment(CommentCreateRequest request);

    CommentResponse replyComment(CommentReplyRequest request);

    CommentResponse updateComment(String id, CommentUpdateRequest request);

    void deleteCommentById(String id);
}
