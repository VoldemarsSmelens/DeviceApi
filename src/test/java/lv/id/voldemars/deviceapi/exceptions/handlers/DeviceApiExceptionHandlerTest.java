package lv.id.voldemars.deviceapi.exceptions.handlers;

import lv.id.voldemars.deviceapi.exceptions.DeviceApiException;
import lv.id.voldemars.deviceapi.models.ErrorResponse;
import lv.id.voldemars.deviceapi.models.enums.ErrorMessages;
import lv.id.voldemars.deviceapi.models.enums.ResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeviceApiExceptionHandlerTest {

    DeviceApiExceptionHandler deviceApiExceptionHandler = new DeviceApiExceptionHandler();

    @Nested
    @DisplayName("DeviceApiException handleException")
    class DeviceApiExceptionTest {

        @Test
        @DisplayName("When one error messages")
        void test1() {
            //given
            DeviceApiException deviceApiException = new DeviceApiException(ErrorMessages.NO_DEVICE_TYPE);

            //When
            ResponseEntity<Object> objectResponseEntity = deviceApiExceptionHandler.handleException(deviceApiException, null);

            //then
            assertAll(
                    () -> assertThat(objectResponseEntity).isNotNull(),
                    () -> assertThat(objectResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                    () -> assertThat(objectResponseEntity.getBody().getClass()).isEqualTo(ErrorResponse.class),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getResponseStatus()).isEqualTo(ResponseStatus.VALIDATION_ERROR),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages()).isNotNull(),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(0).getKey()).isEqualTo(ErrorMessages.NO_DEVICE_TYPE.getCode()),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(0).getValue()).isEqualTo(ErrorMessages.NO_DEVICE_TYPE.getMessage())
            );
        }

        @Test
        @DisplayName("When multiple error messages")
        void test2() {
            //given
            DeviceApiException deviceApiException = new DeviceApiException(List.of(ErrorMessages.NO_DEVICE_TYPE, ErrorMessages.NO_MAC_ADDRESS));

            //When
            ResponseEntity<Object> objectResponseEntity = deviceApiExceptionHandler.handleException(deviceApiException, null);

            //then
            assertAll(
                    () -> assertThat(objectResponseEntity).isNotNull(),
                    () -> assertThat(objectResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                    () -> assertThat(objectResponseEntity.getBody().getClass()).isEqualTo(ErrorResponse.class),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getResponseStatus()).isEqualTo(ResponseStatus.VALIDATION_ERROR),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages()).isNotNull(),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().size()).isEqualTo(2),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(0).getKey()).isEqualTo(ErrorMessages.NO_DEVICE_TYPE.getCode()),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(0).getValue()).isEqualTo(ErrorMessages.NO_DEVICE_TYPE.getMessage()),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(1).getKey()).isEqualTo(ErrorMessages.NO_MAC_ADDRESS.getCode()),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(1).getValue()).isEqualTo(ErrorMessages.NO_MAC_ADDRESS.getMessage())
            );
        }

        @Test
        @DisplayName("When error message and exception text provided")
        void test3() {
            //given
            DeviceApiException deviceApiException = new DeviceApiException(List.of(ErrorMessages.NO_DEVICE_TYPE), "Test exception message");

            //When
            ResponseEntity<Object> objectResponseEntity = deviceApiExceptionHandler.handleException(deviceApiException, null);

            //then
            assertAll(
                    () -> assertThat(objectResponseEntity).isNotNull(),
                    () -> assertThat(objectResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                    () -> assertThat(objectResponseEntity.getBody().getClass()).isEqualTo(ErrorResponse.class),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getResponseStatus()).isEqualTo(ResponseStatus.VALIDATION_ERROR),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages()).isNotNull(),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().size()).isEqualTo(2),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(0).getKey()).isEqualTo(ErrorMessages.NO_DEVICE_TYPE.getCode()),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(0).getValue()).isEqualTo(ErrorMessages.NO_DEVICE_TYPE.getMessage()),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(1).getKey()).isEqualTo(-1000),
                    () -> assertThat(((ErrorResponse)objectResponseEntity.getBody()).getErrorMessages().get(1).getValue()).isEqualTo("Test exception message")
            );
        }
    }
}