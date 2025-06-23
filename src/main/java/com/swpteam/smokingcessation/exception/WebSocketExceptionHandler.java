package com.swpteam.smokingcessation.exception;


import com.swpteam.smokingcessation.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser("/queue/errors")
    public String handleValidationException(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getBindingResult().getAllErrors().getFirst().getDefaultMessage());

        ErrorCode errorCode = ErrorCode.INVALID_MESSAGE_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid enum key: {}", enumKey, e);
        }
        return errorCode.getMessage();
    }

    @MessageExceptionHandler(AppException.class)
    @SendToUser("/queue/errors")
    public String handleAppException(AppException exception) {
        return exception.getMessage();
    }
}
