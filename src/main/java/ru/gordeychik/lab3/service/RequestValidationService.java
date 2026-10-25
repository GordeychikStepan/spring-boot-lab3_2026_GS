package ru.gordeychik.lab3.service;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.gordeychik.lab3.exception.ValidationFailedException;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    log.error("bindingResult: поле {}: {}",
                            error.getField(), error.getDefaultMessage()));
            String details = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .sorted()
                    .collect(Collectors.joining("; "));
            log.error("Выбрасывается ValidationFailedException: {}",
                    details);
            throw new ValidationFailedException(details);
        }
        log.info("Валидация request пройдена");
    }
}
