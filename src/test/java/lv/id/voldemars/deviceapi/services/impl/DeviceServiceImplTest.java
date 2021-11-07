package lv.id.voldemars.deviceapi.services.impl;

import lv.id.voldemars.deviceapi.exceptions.DeviceApiException;
import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.DeviceTopology;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;
import lv.id.voldemars.deviceapi.persistence.DeviceModel;
import lv.id.voldemars.deviceapi.persistence.repositories.DeviceRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @Mock
    private DeviceRepositoryImpl deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Nested
    @DisplayName("GetDeviceList")
    class GetDeviceListTest {

        @Test
        @DisplayName("When request all device list returned in correct order")
        void test1() {
            //given
            DeviceModel d1 = new DeviceModel().setMacAddress("11:11:11:11:11:12").setDeviceType(DeviceType.SWITCH);
            DeviceModel d2 = new DeviceModel().setMacAddress("11:11:11:11:11:11").setDeviceType(DeviceType.SWITCH);
            DeviceModel d3 = new DeviceModel().setMacAddress("11:11:11:11:11:13").setDeviceType(DeviceType.GATEWAY);
            given(deviceRepository.findAll()).willReturn(List.of(d1, d2, d3));

            //When
            List<Device> deviceList = deviceService.getDeviceList();

            //then
            assertAll(
                    () -> assertThat(deviceList).isNotNull(),
                    () -> assertThat(deviceList).isNotEmpty(),
                    () -> assertThat(deviceList.get(0).getMacAddress()).isEqualTo(d3.getMacAddress()),
                    () -> assertThat(deviceList.get(1).getMacAddress()).isEqualTo(d2.getMacAddress()),
                    () -> assertThat(deviceList.get(2).getMacAddress()).isEqualTo(d1.getMacAddress())
            );
        }
    }

    @Nested
    @DisplayName("GetDevice")
    class GetDeviceTest {

        @Test
        @DisplayName("When device exists")
        void test1() {
            //given
            DeviceModel d1 = new DeviceModel().setMacAddress("11:11:11:11:11:11").setDeviceType(DeviceType.SWITCH);
            given(deviceRepository.findAll()).willReturn(List.of(d1));

            //When
            Device device = null;
            try {
                device = deviceService.getDevice(d1.getMacAddress());
            } catch (DeviceApiException ex) {
                fail("Exception should not be returned");
            }

            //then
            Device finalDevice = device;
            assertAll(
                    () -> assertThat(finalDevice).isNotNull(),
                    () -> assertThat(finalDevice.getMacAddress()).isEqualTo(d1.getMacAddress()),
                    () -> assertThat(finalDevice.getDeviceType()).isEqualTo(d1.getDeviceType())
            );
        }

        @Test
        @DisplayName("When incorrect mac address")
        void test2() {
            //given

            //When
            Assertions.assertThrows(DeviceApiException.class , () -> deviceService.getDevice("123"));
            //then
        }

        @Test
        @DisplayName("When device does not exist")
        void test3() {
            //given
            DeviceModel d1 = new DeviceModel().setMacAddress("11:11:11:11:11:11").setDeviceType(DeviceType.SWITCH);
            given(deviceRepository.findAll()).willReturn(List.of(d1));

            //When
            Device device = null;
            try {
                device = deviceService.getDevice("11:11:11:11:11:12");
            } catch (DeviceApiException ex) {
                fail("Exception should not be returned");
            }

            //then
            Device finalDevice = device;
            assertAll(
                    () -> assertThat(finalDevice).isNull()
            );
        }

    }

    @Nested
    @DisplayName("getAllTopologies")
    class GetAllTopologiesTest {

        @Test
        @DisplayName("When one root")
        void test1() {
            //given
            DeviceModel d1 = new DeviceModel().setMacAddress("11:11:11:11:11:11")
                    .setDeviceType(DeviceType.SWITCH);
            DeviceModel d2 = new DeviceModel().setMacAddress("11:11:11:11:11:12")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d1.getMacAddress());
            DeviceModel d3 = new DeviceModel().setMacAddress("11:11:11:11:11:13")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d1.getMacAddress());

            given(deviceRepository.findAll()).willReturn(List.of(d1, d2, d3));

            //When
            List<DeviceTopology> allTopologies = deviceService.getAllTopologies();

            //then
            assertAll(
                    () -> assertThat(allTopologies).isNotNull(),
                    () -> assertThat(allTopologies.size()).isEqualTo(1),
                    () -> assertThat(allTopologies.get(0).getMacAddress()).isEqualTo(d1.getMacAddress()),
                    () -> assertThat(allTopologies.get(0).getConnectedDevices().size()).isEqualTo(2),
                    () -> assertThat(allTopologies.get(0).getConnectedDevices()
                            .stream().map(DeviceTopology::getMacAddress)).contains(d2.getMacAddress(), d3.getMacAddress())
            );
        }

        @Test
        @DisplayName("When multiple roots")
        void test2() {
            //given
            DeviceModel d1 = new DeviceModel().setMacAddress("11:11:11:11:11:11")
                    .setDeviceType(DeviceType.SWITCH);
            DeviceModel d2 = new DeviceModel().setMacAddress("11:11:11:11:11:12")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d1.getMacAddress());
            DeviceModel d3 = new DeviceModel().setMacAddress("11:11:11:11:11:13")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d1.getMacAddress());

            DeviceModel d11 = new DeviceModel().setMacAddress("11:11:11:11:11:21")
                    .setDeviceType(DeviceType.SWITCH);
            DeviceModel d12 = new DeviceModel().setMacAddress("11:11:11:11:11:22")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d11.getMacAddress());

            given(deviceRepository.findAll()).willReturn(List.of(d1, d2, d3, d11, d12));

            //When
            List<DeviceTopology> allTopologies = deviceService.getAllTopologies();

            //then
            assertAll(
                    () -> assertThat(allTopologies).isNotNull(),
                    () -> assertThat(allTopologies.size()).isEqualTo(2),
                    () -> assertThat(allTopologies.get(0).getMacAddress()).isEqualTo(d1.getMacAddress()),
                    () -> assertThat(allTopologies.get(0).getConnectedDevices().size()).isEqualTo(2),
                    () -> assertThat(allTopologies.get(0).getConnectedDevices()
                            .stream().map(DeviceTopology::getMacAddress)).contains(d2.getMacAddress(), d3.getMacAddress()),
                    () -> assertThat(allTopologies.get(1).getMacAddress()).isEqualTo(d11.getMacAddress()),
                    () -> assertThat(allTopologies.get(1).getConnectedDevices().size()).isEqualTo(1),
                    () -> assertThat(allTopologies.get(1).getConnectedDevices()
                            .stream().map(DeviceTopology::getMacAddress)).contains(d12.getMacAddress())
            );
        }
    }

    @Nested
    @DisplayName("GetTopology")
    class GetTopologyTest {

        @Test
        @DisplayName("When specific root")
        void test1() {
            //given
            DeviceModel d1 = new DeviceModel().setMacAddress("11:11:11:11:11:11")
                    .setDeviceType(DeviceType.SWITCH);
            DeviceModel d2 = new DeviceModel().setMacAddress("11:11:11:11:11:12")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d1.getMacAddress());
            DeviceModel d3 = new DeviceModel().setMacAddress("11:11:11:11:11:13")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d1.getMacAddress());
            DeviceModel d4 = new DeviceModel().setMacAddress("11:11:11:11:11:14")
                    .setDeviceType(DeviceType.SWITCH)
                    .setUpLinkDeviceAddress(d3.getMacAddress());

            given(deviceRepository.findAll()).willReturn(List.of(d1, d2, d3, d4));

            //When
            DeviceTopology topology = null;
            try {
                topology = deviceService.getTopology(d3.getMacAddress());
            } catch (DeviceApiException e) {
                fail("Exception should not be returned");
            }

            //then
            DeviceTopology finalTopology = topology;
            assertAll(
                    () -> assertThat(finalTopology).isNotNull(),
                    () -> assertThat(finalTopology.getMacAddress()).isEqualTo(d3.getMacAddress()),
                    () -> assertThat(finalTopology.getConnectedDevices().size()).isEqualTo(1),
                    () -> assertThat(finalTopology.getConnectedDevices()
                            .stream().map(DeviceTopology::getMacAddress)).contains(d4.getMacAddress())
            );
        }
    }

}