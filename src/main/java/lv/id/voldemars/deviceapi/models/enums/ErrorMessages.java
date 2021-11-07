package lv.id.voldemars.deviceapi.models.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    NO_DEVICE_TYPE(-1, "Device Type must be provided!"),
    NO_MAC_ADDRESS(-2, "Mac Address must be provided!"),
    DEVICE_ALREADY_REGISTERED(-3, "Device already registered!"),
    UP_LINK_DEVICE_DOES_NOT_EXIST(-4, "Up link device for mac address does not exist!"),
    INCORRECT_MAC_ADDRESS(-5, "Incorrect mac address!")
    ;

    private final long code;
    private final String message;

    ErrorMessages(long code, String message){
        this.code = code;
        this.message = message;
    }
}
