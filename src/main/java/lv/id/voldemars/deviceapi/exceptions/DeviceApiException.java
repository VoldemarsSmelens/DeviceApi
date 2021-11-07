package lv.id.voldemars.deviceapi.exceptions;

import lombok.Getter;
import lv.id.voldemars.deviceapi.models.enums.ErrorMessages;

import java.util.List;

@Getter
public class DeviceApiException extends Exception{

    private final List<ErrorMessages> errorMessages;
    private final String exceptionMessage;

    public DeviceApiException(List<ErrorMessages> errorMessages, String exceptionMessage){
        this.errorMessages = errorMessages;
        this.exceptionMessage = exceptionMessage;
    }

    public DeviceApiException(List<ErrorMessages> errorMessages){
        this(errorMessages, null);
    }

    public DeviceApiException(ErrorMessages errorMessage){
        this(List.of(errorMessage));
    }
}
