package lv.id.voldemars.deviceapi.exceptions.handlers;

import lombok.extern.log4j.Log4j2;
import lv.id.voldemars.deviceapi.exceptions.DeviceApiException;
import lv.id.voldemars.deviceapi.models.ErrorResponse;
import lv.id.voldemars.deviceapi.models.enums.ResponseStatus;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Log4j2
@ControllerAdvice()
public class DeviceApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DeviceApiException.class})
    protected ResponseEntity<Object> handleException(DeviceApiException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        if(ex.getErrorMessages() != null){
            errorResponse.setResponseStatus(ResponseStatus.VALIDATION_ERROR);
            ex.getErrorMessages().forEach(message -> errorResponse.getErrorMessages().add(Pair.of(message.getCode(), message.getMessage())));
        }

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
