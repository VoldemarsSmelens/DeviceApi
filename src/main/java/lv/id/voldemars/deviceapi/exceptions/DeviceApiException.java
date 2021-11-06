package lv.id.voldemars.deviceapi.exceptions;

import lombok.Getter;
import lv.id.voldemars.deviceapi.models.enums.ErrorMessages;

import java.util.List;

@Getter
public class DeviceApiException extends Exception{

    private final List<ErrorMessages> errorMessages;

    public DeviceApiException(List<ErrorMessages> errorMessages){
        this.errorMessages = errorMessages;
    }

    public DeviceApiException(ErrorMessages errorMessage){
        this.errorMessages = List.of(errorMessage);
    }
}
