package ru.gordeychik.lab3.controller;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gordeychik.lab3.model.*;
import ru.gordeychik.lab3.util.DateTimeUtil;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleUnreadable(
            HttpMessageNotReadableException exception) {
        log.error("Ошибка чтения request: {}",
                exception.getMostSpecificCause().getMessage());
        Response response = Response.builder()
                .uid("")
                .operationUid("")
                .systemTime(DateTimeUtil.getCustomFormat()
                        .format(new Date()))
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.VALIDATION_EXCEPTION)
                .errorMessage(ErrorMessages.VALIDATION)
                .build();
        log.info("Создан response для непрочитанного request: {}",
                response);
        return ResponseEntity.badRequest().body(response);
    }
}
