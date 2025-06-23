package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.chat.ChatResponse;
import com.swpteam.smokingcessation.domain.dto.chat.ChatRestResponse;
import com.swpteam.smokingcessation.domain.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "createdAt", target = "sentAt")
    ChatResponse toResponse(Chat entity);

    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "createdAt", target = "sentAt")
    ChatRestResponse toRestResponse(Chat entity);
}
