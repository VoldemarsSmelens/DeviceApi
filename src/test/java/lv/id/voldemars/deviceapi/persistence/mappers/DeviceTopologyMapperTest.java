package lv.id.voldemars.deviceapi.persistence.mappers;

import lv.id.voldemars.deviceapi.models.DeviceTopology;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;
import lv.id.voldemars.deviceapi.persistence.DeviceModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeviceTopologyMapperTest {

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
            DeviceTopology deviceTopology = DeviceTopologyMapper.toDto(deviceModel);

            //then
            assertAll(
                    () -> assertThat(deviceTopology).isNotNull(),
                    () -> assertThat(deviceTopology.getDeviceType()).isEqualTo(deviceModel.getDeviceType()),
                    () -> assertThat(deviceTopology.getMacAddress()).isEqualTo(deviceModel.getMacAddress()),
                    () -> assertThat(deviceTopology.getUpLinkDeviceAddress()).isEqualTo(deviceModel.getUpLinkDeviceAddress())

            );
        }
    }
}