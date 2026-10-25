package ru.gordeychik.lab3.service;

import org.springframework.validation.BindingResult;

public interface ValidationService {
    void isValid(BindingResult bindingResult);
}
