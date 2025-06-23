package com.swpteam.smokingcessation.controller.v1.profile;

import com.swpteam.smokingcessation.domain.dto.member.MemberRequest;
import com.swpteam.smokingcessation.domain.dto.member.MemberResponse;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.service.interfaces.profile.IMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Member", description = "Manage member-related operations")
public class MemberController {

    IMemberService memberService;

    @PostMapping("/{accountId}")
    ResponseEntity<ApiResponse<MemberResponse>> createMember(@PathVariable String accountId, @RequestBody @Valid MemberRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MemberResponse>builder()
                        .code(SuccessCode.MEMBER_CREATED.getCode())
                        .message(SuccessCode.MEMBER_CREATED.getMessage())
                        .result(memberService.createMember(accountId, request))
                        .build());
    }

    @GetMapping
    ResponseEntity<ApiResponse<Page<MemberResponse>>> getUsers(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<MemberResponse>>builder()
                        .result(memberService.getMembers(request))
                        .build());
    }

    @GetMapping("/{accountId}")
    ResponseEntity<ApiResponse<MemberResponse>> getMemberById(@PathVariable String accountId) {
        return ResponseEntity.ok(
                ApiResponse.<MemberResponse>builder()
                        .result(memberService.getMemberById(accountId))
                        .build());
    }

    @PutMapping("/{accountId}")
    ResponseEntity<ApiResponse<MemberResponse>> updateAccount(@PathVariable String accountId, @RequestBody MemberRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<MemberResponse>builder()
                        .code(SuccessCode.ACCOUNT_UPDATED.getCode())
                        .message(SuccessCode.ACCOUNT_UPDATED.getMessage())
                        .result(memberService.updateMember(accountId, request))
                        .build());
    }
}
