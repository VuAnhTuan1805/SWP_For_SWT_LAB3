package com.swpteam.smokingcessation.service.interfaces.membership;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionResponse;
import com.swpteam.smokingcessation.domain.entity.Subscription;
import org.springframework.data.domain.Page;

public interface ISubscriptionService {

    Page<SubscriptionResponse> getSubscriptionPage(PageableRequest request);

    SubscriptionResponse getSubscriptionById(String id);

    Page<SubscriptionResponse> getSubscriptionPageByAccountId(String accountId, PageableRequest request);

    Subscription createSubscription(String accountId, String membershipName);

    SubscriptionResponse createSubscription(SubscriptionRequest request);

    SubscriptionResponse updateSubscription(String id, SubscriptionRequest request);

    void softDeleteSubscription(String id);
}
