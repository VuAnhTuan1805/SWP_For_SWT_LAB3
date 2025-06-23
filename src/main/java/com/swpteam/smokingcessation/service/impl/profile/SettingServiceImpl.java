package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.domain.dto.setting.SettingRequest;
import com.swpteam.smokingcessation.domain.dto.setting.SettingResponse;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.domain.mapper.SettingMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.SettingRepository;
import com.swpteam.smokingcessation.service.interfaces.profile.ISettingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingServiceImpl implements ISettingService {

    SettingRepository settingRepository;
    SettingMapper settingMapper;

    @Override
    public SettingResponse getSettingByAccountId(String accountId) {
        return settingMapper.toResponse(findSettingById(accountId));
    }
    
    @Override
    @Transactional
    @CachePut(value = "SETTING_CACHE", key = "#result.getId()")
    public SettingResponse updateSetting(String accountId, SettingRequest request) {
        Setting setting = findSettingById(accountId);

        settingMapper.update(setting, request);

        return settingMapper.toResponse(settingRepository.save(setting));
    }

    @Cacheable(value = "SETTING_CACHE", key = "#id")
    private Setting findSettingById(String accountId) {
        Setting setting = settingRepository.findByIdAndIsDeletedFalse(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (setting.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }
        return setting;
    }
}
