package ru.gordeychik.lab3.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank(message = "uid обязателен")
    @Size(max = 32, message = "uid не более 32 символов")
    private String uid;

    @NotBlank(message = "operationUid обязателен")
    @Size(max = 32, message = "operationUid не более 32 символов")
    private String operationUid;

    private Systems systemName;

    @NotBlank(message = "systemTime обязателен")
    private String systemTime;

    private String source;

    @Min(value = 1, message = "communicationId не меньше 1")
    @Max(value = 100000, message = "communicationId не больше 100000")
    private int communicationId;

    private int templateId;
    private int productCode;
    private int smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", source='" + source + '\'' +
                ", communicationId=" + communicationId +
                ", templateId=" + templateId +
                ", productCode=" + productCode +
                ", smsCode=" + smsCode +
                '}';
    }
}
