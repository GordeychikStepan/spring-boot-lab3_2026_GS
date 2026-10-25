package ru.gordeychik.lab3.service;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.gordeychik.lab3.model.Response;

@Slf4j
@Service
@Qualifier("ModifyOperationUidResponseService")
public class ModifyOperationUidResponseService
        implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        String previous = response.getOperationUid();
        UUID uuid = UUID.randomUUID();
        response.setOperationUid(uuid.toString());
        log.info("response.operationUid: {} -> {}", previous,
                response.getOperationUid());
        return response;
    }
}
