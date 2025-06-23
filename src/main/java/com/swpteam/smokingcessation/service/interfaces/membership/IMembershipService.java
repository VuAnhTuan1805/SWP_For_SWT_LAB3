package com.swpteam.smokingcessation.service.interfaces.membership;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipCreateRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipCurrencyUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipResponse;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Membership;
import org.springframework.data.domain.Page;

public interface IMembershipService {

    Page<MembershipResponse> getMembershipPage(PageableRequest request);

    MembershipResponse getMembershipById(String id);

    MembershipResponse getMembershipByName(String name);

    MembershipResponse createMembership(MembershipCreateRequest request);

    MembershipResponse updateMembership(String id, MembershipUpdateRequest request);

    MembershipResponse updateMembershipCurrency(String id, MembershipCurrencyUpdateRequest request);

    Membership findMembershipById(String id);

    Membership findMembershipByName(String name);

    void softDeleteMembershipById(String id);
}
