package ru.gordeychik.lab3.service;

import org.springframework.stereotype.Service;
import ru.gordeychik.lab3.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
