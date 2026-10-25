package ru.gordeychik.lab3.controller;

import java.util.Date;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gordeychik.lab3.exception.UnsupportedCodeException;
import ru.gordeychik.lab3.exception.ValidationFailedException;
import ru.gordeychik.lab3.model.*;
import ru.gordeychik.lab3.service.ModifyResponseService;
import ru.gordeychik.lab3.service.ValidationService;
import ru.gordeychik.lab3.util.DateTimeUtil;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
            @Qualifier("ModifySystemTimeResponseService")
            ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(
            @Valid @RequestBody Request request,
            BindingResult bindingResult) {
        log.info("Получен request: {}", request);
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat()
                        .format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("Создан response: {}", response);

        try {
            validationService.isValid(bindingResult);
            if ("123".equals(request.getUid())) {
                log.error("Выбрасывается UnsupportedCodeException: uid=123");
                throw new UnsupportedCodeException("uid=123 не поддерживается");
            }
            response = modifyResponseService.modify(response);
        } catch (ValidationFailedException e) {
            log.error("Ошибка валидации: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Изменён response после валидации: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Неподдерживаемый uid: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Изменён response для uid=123: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Непредвиденная ошибка обработки request", e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Изменён response после исключения: {}", response);
            return new ResponseEntity<>(response,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Возвращается response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
