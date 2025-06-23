package com.swpteam.smokingcessation.service.impl.identity;

import com.swpteam.smokingcessation.domain.dto.account.AccountRequest;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.dto.account.AccountUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.account.ChangePasswordRequest;
import com.swpteam.smokingcessation.domain.enums.AccountStatus;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.domain.mapper.AccountMapper;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements IAccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    AuthUtil authUtil;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "ACCOUNT_CACHE", key = "#result.getId()")
    public AccountResponse createAccount(AccountRequest request) {
        checkExistByEmail(request.getEmail());
        checkExistByPhoneNumber(request.getPhoneNumber());

        Account account = accountMapper.toEntity(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setSetting(Setting.getDefaultSetting(account));

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AccountResponse> getAccounts(PageableRequest request) {
        ValidationUtil.checkFieldExist(Account.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Account> accounts = accountRepository.findAllByIsDeletedFalse(pageable);

        return accounts.map(accountMapper::toResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponse getAccountById(String id) {
        return accountMapper.toResponse(findAccountById(id));
    }

    @Override
    public AccountResponse getCurrentAccount() {
        Account account = authUtil.getCurrentAccount()
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "ACCOUNT_CACHE", key = "#result.getId()")
    public AccountResponse updateAccountRole(String id, Role role) {
        Account account = findAccountById(id);
        account.setRole(role);

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponse updateAccountWithoutRole(String id, AccountUpdateRequest request) {
        Account account = findAccountById(id);

        accountMapper.updateWithoutRole(account, request);
        if (request.getPassword() != null) {
            account.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "ACCOUNT_CACHE", key = "#id")
    public void deleteAccount(String id) {
        Account account = findAccountById(id);

        account.setDeleted(true);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountResponse changePassword(ChangePasswordRequest request) {
        Account account = authUtil.getCurrentAccount()
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);

        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void banAccount(String id) {
        Account account = findAccountById(id);
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        if (account == currentAccount)
            throw new AppException(ErrorCode.SELF_BAN);

        account.setStatus(AccountStatus.BANNED);
        accountRepository.save(account);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    @Cacheable(value = "ACCOUNT_CACHE", key = "#id")
    public Account findAccountById(String id) {
        return accountRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    @Cacheable(value = "ACCOUNT_CACHE", key = "#username")
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    private void checkExistByEmail(String email) {
        if (accountRepository.existsByEmail(email))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
    }

    private void checkExistByPhoneNumber(String phoneNumber) {
        if (accountRepository.existsByEmail(phoneNumber))
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
    }
}
