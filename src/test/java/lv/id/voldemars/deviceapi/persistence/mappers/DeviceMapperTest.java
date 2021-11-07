package lv.id.voldemars.deviceapi.persistence.mappers;

import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;
import lv.id.voldemars.deviceapi.persistence.DeviceModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeviceMapperTest {

    @Nested
    @DisplayName("toDto")
    class DtoTest {

        @Test
        @DisplayName("When all fields mapped")
        void test1() {
            //given
            DeviceModel deviceModel = new DeviceModel()
                    .setId(1L)
                    .setDeviceType(DeviceType.SWITCH)
                    .setMacAddress("11:11:11:11:11:11")
                    .setUpLinkDeviceAddress("11:11:11:11:11:12");

            //When
            Device device = DeviceMapper.toDto(deviceModel);

            //then
            assertAll(
                    () -> assertThat(device).isNotNull(),
                    () -> assertThat(device.getDeviceType()).isEqualTo(deviceModel.getDeviceType()),
                    () -> assertThat(device.getMacAddress()).isEqualTo(deviceModel.getMacAddress()),
                    () -> assertThat(device.getUpLinkDeviceAddress()).isEqualTo(deviceModel.getUpLinkDeviceAddress())

            );
        }
    }
}