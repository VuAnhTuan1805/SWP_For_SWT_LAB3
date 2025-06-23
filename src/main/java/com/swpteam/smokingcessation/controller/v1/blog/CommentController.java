package com.swpteam.smokingcessation.controller.v1.blog;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.comment.CommentCreateRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentReplyRequest;
import com.swpteam.smokingcessation.domain.dto.comment.CommentResponse;
import com.swpteam.smokingcessation.domain.dto.comment.CommentUpdateRequest;
import com.swpteam.smokingcessation.service.interfaces.blog.ICommentService;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Comment", description = "Manage comment-related operations")
public class CommentController {

    ICommentService commentService;

    @GetMapping("/blog/{id}")
    ResponseEntity<ApiResponse<Page<CommentResponse>>> getCommentsByBlogId(@PathVariable String id, @Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<CommentResponse>>builder()
                        .code(SuccessCode.COMMENT_GET_BY_BLOG.getCode())
                        .message(SuccessCode.COMMENT_GET_BY_BLOG.getMessage())
                        .result(commentService.getCommentsByBlogId(id, request))
                        .build()
        );
    }

    @GetMapping
    ResponseEntity<ApiResponse<Page<CommentResponse>>> getCommentPage(@Valid PageableRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<Page<CommentResponse>>builder()
                        .code(SuccessCode.COMMENT_LIST_ALL.getCode())
                        .message(SuccessCode.COMMENT_LIST_ALL.getMessage())
                        .result(commentService.getCommentPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<CommentResponse>> getCommentById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.<CommentResponse>builder()
                        .code(SuccessCode.COMMENT_GET_BY_ID.getCode())
                        .message(SuccessCode.COMMENT_GET_BY_ID.getMessage())
                        .result(commentService.getCommentById(id))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<CommentResponse>> createSubscription(@RequestBody @Valid CommentCreateRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<CommentResponse>builder()
                        .code(SuccessCode.COMMENT_CREATED.getCode())
                        .message(SuccessCode.COMMENT_CREATED.getMessage())
                        .result(commentService.createComment(request))
                        .build()
        );
    }

    @PostMapping("/reply")
    ResponseEntity<ApiResponse<CommentResponse>> createSubscription(@RequestBody @Valid CommentReplyRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<CommentResponse>builder()
                        .code(SuccessCode.COMMENT_CREATED.getCode())
                        .message(SuccessCode.COMMENT_CREATED.getMessage())
                        .result(commentService.replyComment(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<CommentResponse>> updateComment(@PathVariable String id, @RequestBody @Valid CommentUpdateRequest request) {
        return ResponseEntity.ok().body(
                ApiResponse.<CommentResponse>builder()
                        .code(SuccessCode.COMMENT_UPDATED.getCode())
                        .message(SuccessCode.COMMENT_UPDATED.getMessage())
                        .result(commentService.updateComment(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteCommentById(@PathVariable String id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().body(
                ApiResponse.<String>builder()
                        .code(SuccessCode.COMMENT_DELETED.getCode())
                        .message(SuccessCode.COMMENT_DELETED.getMessage())
                        .build()
        );
    }
}
