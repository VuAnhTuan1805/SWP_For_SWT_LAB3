package com.swpteam.smokingcessation.service.interfaces.profile;

import com.swpteam.smokingcessation.domain.dto.setting.SettingResponse;
import com.swpteam.smokingcessation.domain.dto.setting.SettingRequest;

public interface ISettingService {

    SettingResponse getSettingByAccountId(String accountId);

    SettingResponse updateSetting(String accountId, SettingRequest request);
}
