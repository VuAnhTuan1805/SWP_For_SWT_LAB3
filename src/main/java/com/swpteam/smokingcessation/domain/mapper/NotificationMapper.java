package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.account.AccountRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toEntity(NotificationRequest request);

    @Mapping(target = "accountId", source = "account", qualifiedByName = "accountToId")
    NotificationResponse toResponse(Notification notification);

    @Named("accountToId")
    static String mapAccountToId(Account account) {
        return account != null ? account.getId() : null;
    }
}
