package com.swpteam.smokingcessation.service.impl.membership;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.SubscriptionMapper;
import com.swpteam.smokingcessation.domain.entity.Membership;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Subscription;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.SubscriptionRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.membership.IMembershipService;
import com.swpteam.smokingcessation.service.interfaces.membership.ISubscriptionService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionServiceImpl implements ISubscriptionService {

    SubscriptionRepository subscriptionRepository;
    SubscriptionMapper subscriptionMapper;

    IAccountService accountService;
    IMembershipService membershipService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<SubscriptionResponse> getSubscriptionPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Subscription.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Subscription> subscriptions = subscriptionRepository.findAllByIsDeletedFalse(pageable);

        return subscriptions.map(subscriptionMapper::toResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<SubscriptionResponse> getSubscriptionPageByAccountId(String accountId, PageableRequest request) {
        ValidationUtil.checkFieldExist(Subscription.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Subscription> subscriptions = subscriptionRepository.findByAccountIdAndIsDeletedFalse(accountId, pageable);

        return subscriptions.map(subscriptionMapper::toResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SubscriptionResponse getSubscriptionById(String id) {
        return subscriptionMapper.toResponse(findSubscriptionById(id));
    }

    @Override
    @Transactional
    @CachePut(value = "SUBSCRIPTION_CACHE", key = "#result.getId()")
    public Subscription createSubscription(String accountId, String membershipName) {
        Account account = accountService.findAccountById(accountId);

        Membership membership = membershipService.findMembershipByName(membershipName);

        Subscription subscription = Subscription.builder()
                .account(account)
                .membership(membership)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(membership.getDurationDays()))
                .build();

        return subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "SUBSCRIPTION_CACHE", key = "#result.getId()")
    public SubscriptionResponse createSubscription(SubscriptionRequest request) {
        Subscription subscription = subscriptionMapper.toEntity(request);

        Account account = accountService.findAccountByEmail(request.getEmail());
        Membership membership = membershipService.findMembershipByName(request.getMembershipName());

        subscription.setAccount(account);
        subscription.setMembership(membership);

        return subscriptionMapper.toResponse(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "SUBSCRIPTION_CACHE", key = "#result.getId()")
    public SubscriptionResponse updateSubscription(String id, SubscriptionRequest request) {
        Subscription subscription = findSubscriptionById(id);

        subscriptionMapper.update(subscription, request);

        return subscriptionMapper.toResponse(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "SUBSCRIPTION_CACHE", key = "#id")
    public void softDeleteSubscription(String id) {
        Subscription subscription = findSubscriptionById(id);

        subscription.setDeleted(true);
        subscriptionRepository.save(subscription);
    }

    @Cacheable(value = "SUBSCRIPTION_CACHE", key = "#id")
    private Subscription findSubscriptionById(String id) {
        Subscription subscription = subscriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_FOUND));

        if (subscription.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }

        return subscription;
    }
}
