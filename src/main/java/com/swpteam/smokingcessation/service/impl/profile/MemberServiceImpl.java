package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.domain.dto.member.MemberRequest;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.MemberMapper;
import com.swpteam.smokingcessation.domain.dto.member.MemberResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.MemberRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.profile.IMemberService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberServiceImpl implements IMemberService {

    MemberMapper memberMapper;
    MemberRepository memberRepository;

    IAccountService accountService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<MemberResponse> getMembers(PageableRequest request) {
        ValidationUtil.checkFieldExist(Member.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Member> members = memberRepository.findAllByIsDeletedFalse(pageable);

        return members.map(memberMapper::toResponse);
    }

    @Override
    public MemberResponse getMemberById(String accountId) {
        return memberMapper.toResponse(findMemberById(accountId));
    }

    @Override
    @Transactional
    @CachePut(value = "MEMBER_CACHE", key = "#result.getId()")
    public MemberResponse createMember(String accountId, MemberRequest request) {
        Account account = accountService.findAccountById(accountId);

        if (memberRepository.existsByFullName(request.getFullName())) {
            throw new AppException(ErrorCode.MEMBER_EXISTED);
        }

        Member member = memberMapper.toEntity(request);
        member.setAccount(account);

        return memberMapper.toResponse(memberRepository.save(member));
    }

    @Override
    @Transactional
    @CachePut(value = "MEMBER_CACHE", key = "#result.getId()")
    public MemberResponse updateMember(String accountId, MemberRequest request) {
        Member member = findMemberById(accountId);

        memberMapper.updateMember(member, request);

        return memberMapper.toResponse(memberRepository.save(member));
    }

    @Cacheable(value = "MEMBER_CACHE", key = "#accountId")
    private Member findMemberById(String accountId) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }
        return member;
    }

}
