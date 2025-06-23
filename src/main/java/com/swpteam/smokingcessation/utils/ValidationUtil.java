package com.swpteam.smokingcessation.utils;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.exception.AppException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ValidationUtil {

    public void checkFieldExist(Class<?> clazz, String fieldName) {
        if (!isFieldExist(Account.class, fieldName))
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
    }

    private boolean isFieldExist(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                try {
                    Field superField = superClass.getDeclaredField(fieldName);
                    return true;
                } catch (NoSuchFieldException ex) {
                    return false;
                }
            }
            return false;
        }
    }
}
