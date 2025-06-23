package com.swpteam.smokingcessation.service.interfaces.identity;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.account.AccountRequest;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.dto.account.AccountUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.account.ChangePasswordRequest;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.enums.Role;
import org.springframework.data.domain.Page;

public interface IAccountService {

    Page<AccountResponse> getAccounts(PageableRequest request);

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccountById(String id);

    AccountResponse updateAccountRole(String id, Role role);

    AccountResponse updateAccountWithoutRole(String id, AccountUpdateRequest request);

    AccountResponse changePassword(ChangePasswordRequest request);

    AccountResponse getCurrentAccount();

    Account findAccountById(String id);

    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);

    void deleteAccount(String id);

    void banAccount(String id);
}
