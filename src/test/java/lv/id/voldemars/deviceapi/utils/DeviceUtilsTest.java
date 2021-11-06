package lv.id.voldemars.deviceapi.utils;

import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.DeviceTopology;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeviceUtilsTest {

    @Nested
    @DisplayName("getDeviceFromList")
    class DeviceFromList{


        @Test
        @DisplayName("When device is in list")
        void test1(){
            //given
            List<Device> deviceList = List.of(
                    new Device().setMacAddress("1"),
                    new Device().setMacAddress("2"),
                    new Device().setMacAddress("3")
            );

            //When
            Device device = DeviceUtils.getDeviceFromList("2", deviceList);

            //then
            assertAll(
                    () -> assertThat(device).isNotNull(),
                    () -> assertThat(device.getMacAddress()).isEqualTo("2")
            );
        }

        @Test
        @DisplayName("When device is not in list")
        void test2(){
            //given
            List<Device> deviceList = List.of(
                    new Device().setMacAddress("1"),
                    new Device().setMacAddress("2"),
                    new Device().setMacAddress("3")
            );

            //When
            Device device = DeviceUtils.getDeviceFromList("4", deviceList);

            //then
            assertAll(
                    () -> assertThat(device).isNull()
            );
        }
    }

    @Nested
    @DisplayName("getRoots")
    class Roots{

        @Test
        @DisplayName("When one root with no child's")
        void test1(){
            //given
            List<DeviceTopology> deviceList = List.of(
                    new DeviceTopology().setMacAddress("1")
            );

            //When
            List<DeviceTopology> roots = DeviceUtils.getRoots(deviceList);

            //then
            assertAll(
                    () -> assertThat(roots).isNotNull(),
                    () -> assertThat(roots.size()).isEqualTo(1),
                    () -> assertThat(roots.get(0).getMacAddress()).isEqualTo("1")
            );
        }

        @Test
        @DisplayName("When one root with child's")
        void test2(){
            //given
            DeviceTopology rootDevice = new DeviceTopology().setMacAddress("1");
            DeviceTopology child1 = new DeviceTopology().setMacAddress("2").setUpLinkDeviceAddress(rootDevice.getMacAddress());
            DeviceTopology child2 = new DeviceTopology().setMacAddress("3").setUpLinkDeviceAddress(rootDevice.getMacAddress());

            List<DeviceTopology> deviceList = List.of(
                    rootDevice, child1, child2
            );

            //When
            List<DeviceTopology> roots = DeviceUtils.getRoots(deviceList);

            //then
            assertAll(
                    () -> assertThat(roots).isNotNull(),
                    () -> assertThat(roots.size()).isEqualTo(1),
                    () -> assertThat(roots.get(0)).isEqualTo(rootDevice)
            );
        }

        @Test
        @DisplayName("When multiple roots")
        void test3(){
            //given
            DeviceTopology rootDevice = new DeviceTopology().setMacAddress("1");
            DeviceTopology child1 = new DeviceTopology().setMacAddress("2").setUpLinkDeviceAddress(rootDevice.getMacAddress());
            DeviceTopology child2 = new DeviceTopology().setMacAddress("3").setUpLinkDeviceAddress(rootDevice.getMacAddress());
            DeviceTopology rootDevice2 = new DeviceTopology().setMacAddress("4");

            List<DeviceTopology> deviceList = List.of(
                    rootDevice, child1, child2, rootDevice2
            );

            //When
            List<DeviceTopology> roots = DeviceUtils.getRoots(deviceList);

            //then
            assertAll(
                    () -> assertThat(roots).isNotNull(),
                    () -> assertThat(roots.size()).isEqualTo(2),
                    () -> assertThat(roots).contains(rootDevice),
                    () -> assertThat(roots).contains(rootDevice2)
            );
        }

        @Test
        @DisplayName("When no roots")
        void test4(){
            //given
            List<DeviceTopology> deviceList = new ArrayList<>();

            //When
            List<DeviceTopology> roots = DeviceUtils.getRoots(deviceList);

            //then
            assertAll(
                    () -> assertThat(roots).isNotNull(),
                    () -> assertThat(roots).isEmpty()
            );
        }
    }

    @Nested
    @DisplayName("buildDeviceTopologies")
    class BuildDeviceTopologies{

        @Test
        @DisplayName("One root with one level child's")
        void test1(){
            //given
            DeviceTopology rootDevice = new DeviceTopology().setMacAddress("1");
            DeviceTopology child1 = new DeviceTopology().setMacAddress("2").setUpLinkDeviceAddress(rootDevice.getMacAddress());
            DeviceTopology child2 = new DeviceTopology().setMacAddress("3").setUpLinkDeviceAddress(rootDevice.getMacAddress());

            List<DeviceTopology> deviceList = List.of(rootDevice, child1, child2);

            //When
            List<DeviceTopology> devices = DeviceUtils.buildDeviceTopologies(deviceList);

            //then
            assertAll(
                    () -> assertThat(devices).isNotNull(),
                    () -> assertThat(rootDevice.getConnectedDevices()).isNotNull(),
                    () -> assertThat(rootDevice.getConnectedDevices().size()).isEqualTo(2),
                    () -> assertThat(rootDevice.getConnectedDevices()).contains(child1, child2),
                    () -> assertThat(child1.getConnectedDevices()).isEmpty(),
                    () -> assertThat(child2.getConnectedDevices()).isEmpty()
            );
        }

        @Test
        @DisplayName("One root with multiple level child's")
        void test2(){
            //given
            DeviceTopology rootDevice = new DeviceTopology().setMacAddress("1");
            DeviceTopology child1 = new DeviceTopology().setMacAddress("2").setUpLinkDeviceAddress(rootDevice.getMacAddress());
            DeviceTopology child2 = new DeviceTopology().setMacAddress("3").setUpLinkDeviceAddress(rootDevice.getMacAddress());
            DeviceTopology child3 = new DeviceTopology().setMacAddress("4").setUpLinkDeviceAddress(child2.getMacAddress());
            DeviceTopology child4 = new DeviceTopology().setMacAddress("5").setUpLinkDeviceAddress(child3.getMacAddress());

            List<DeviceTopology> deviceList = List.of(rootDevice, child1, child2, child3, child4);

            //When
            List<DeviceTopology> devices = DeviceUtils.buildDeviceTopologies(deviceList);

            //then
            assertAll(
                    () -> assertThat(devices).isNotNull(),
                    () -> assertThat(rootDevice.getConnectedDevices()).isNotNull(),
                    () -> assertThat(rootDevice.getConnectedDevices().size()).isEqualTo(2),
                    () -> assertThat(rootDevice.getConnectedDevices()).contains(child1, child2),
                    () -> assertThat(child1.getConnectedDevices()).isEmpty(),
                    () -> assertThat(child2.getConnectedDevices().size()).isEqualTo(1),
                    () -> assertThat(child2.getConnectedDevices()).contains(child3),
                    () -> assertThat(child3.getConnectedDevices().size()).isEqualTo(1),
                    () -> assertThat(child3.getConnectedDevices()).contains(child4),
                    () -> assertThat(child4.getConnectedDevices()).isEmpty()
            );
        }

        @Test
        @DisplayName("Roots root with multiple level child's")
        void test3(){
            //given
            DeviceTopology rootDevice1 = new DeviceTopology().setMacAddress("1");
            DeviceTopology child1 = new DeviceTopology().setMacAddress("2").setUpLinkDeviceAddress(rootDevice1.getMacAddress());
            DeviceTopology child2 = new DeviceTopology().setMacAddress("3").setUpLinkDeviceAddress(rootDevice1.getMacAddress());

            DeviceTopology rootDevice2 = new DeviceTopology().setMacAddress("4");
            DeviceTopology child3 = new DeviceTopology().setMacAddress("5").setUpLinkDeviceAddress(rootDevice2.getMacAddress());
            DeviceTopology child4 = new DeviceTopology().setMacAddress("6").setUpLinkDeviceAddress(child3.getMacAddress());

            List<DeviceTopology> deviceList = List.of(rootDevice1, child1, child2, rootDevice2, child3, child4);

            //When
            List<DeviceTopology> devices = DeviceUtils.buildDeviceTopologies(deviceList);

            //then
            assertAll(
                    () -> assertThat(devices).isNotNull(),
                    () -> assertThat(rootDevice1.getConnectedDevices()).isNotNull(),
                    () -> assertThat(rootDevice1.getConnectedDevices().size()).isEqualTo(2),
                    () -> assertThat(rootDevice1.getConnectedDevices()).contains(child1, child2),
                    () -> assertThat(child1.getConnectedDevices()).isEmpty(),
                    () -> assertThat(child2.getConnectedDevices()).isEmpty(),

                    () -> assertThat(rootDevice2.getConnectedDevices()).isNotNull(),
                    () -> assertThat(rootDevice2.getConnectedDevices().size()).isEqualTo(1),
                    () -> assertThat(rootDevice2.getConnectedDevices()).contains(child3),
                    () -> assertThat(child3.getConnectedDevices().size()).isEqualTo(1),
                    () -> assertThat(child3.getConnectedDevices()).contains(child4),
                    () -> assertThat(child4.getConnectedDevices()).isEmpty()
            );
        }
    }

    @Nested
    @DisplayName("sortDevices")
    class SortDevices {

        @Test
        @DisplayName("Device type sort")
        void test1() {
            //given
            Device ap = new Device().setDeviceType(DeviceType.ACCESS_POINT);
            Device sw = new Device().setDeviceType(DeviceType.SWITCH);
            Device gw = new Device().setDeviceType(DeviceType.GATEWAY);

            List<Device> deviceList = List.of(ap, sw, gw);

            //When
            List<Device> devices = DeviceUtils.sortDevices(deviceList);

            //then
            assertAll(
                    () -> assertThat(devices).isNotNull(),
                    () -> assertThat(devices.size()).isEqualTo(3),
                    () -> assertThat(devices.get(0)).isEqualTo(gw),
                    () -> assertThat(devices.get(1)).isEqualTo(sw),
                    () -> assertThat(devices.get(2)).isEqualTo(ap)
            );
        }

        @Test
        @DisplayName("Mac address sort")
        void test2() {
            //given
            Device ap = new Device().setDeviceType(DeviceType.ACCESS_POINT).setMacAddress("3");
            Device ap1 = new Device().setDeviceType(DeviceType.ACCESS_POINT).setMacAddress("1");
            Device ap2 = new Device().setDeviceType(DeviceType.ACCESS_POINT).setMacAddress("2");

            List<Device> deviceList = List.of(ap, ap1, ap2);

            //When
            List<Device> devices = DeviceUtils.sortDevices(deviceList);

            //then
            assertAll(
                    () -> assertThat(devices).isNotNull(),
                    () -> assertThat(devices.size()).isEqualTo(3),
                    () -> assertThat(devices.get(0)).isEqualTo(ap1),
                    () -> assertThat(devices.get(1)).isEqualTo(ap2),
                    () -> assertThat(devices.get(2)).isEqualTo(ap)
            );
        }

        @Test
        @DisplayName("Mac address and device type sort")
        void test3() {
            //given
            Device ap = new Device().setDeviceType(DeviceType.ACCESS_POINT).setMacAddress("7");
            Device gw = new Device().setDeviceType(DeviceType.GATEWAY).setMacAddress("3");
            Device sw = new Device().setDeviceType(DeviceType.SWITCH).setMacAddress("2");
            Device ap1 = new Device().setDeviceType(DeviceType.ACCESS_POINT).setMacAddress("1");
            Device sw1 = new Device().setDeviceType(DeviceType.SWITCH).setMacAddress("5");
            Device gw1 = new Device().setDeviceType(DeviceType.GATEWAY).setMacAddress("9");
            Device ap2 = new Device().setDeviceType(DeviceType.ACCESS_POINT).setMacAddress("4");
            Device sw2 = new Device().setDeviceType(DeviceType.SWITCH).setMacAddress("8");
            Device gw2 = new Device().setDeviceType(DeviceType.GATEWAY).setMacAddress("6");

            List<Device> deviceList = List.of(ap, gw, sw, ap1, gw1, sw1, ap2, gw2, sw2);

            //When
            List<Device> devices = DeviceUtils.sortDevices(deviceList);

            //then
            assertAll(
                    () -> assertThat(devices).isNotNull(),
                    () -> assertThat(devices.size()).isEqualTo(9),
                    () -> assertThat(devices.get(0)).isEqualTo(gw),
                    () -> assertThat(devices.get(1)).isEqualTo(gw2),
                    () -> assertThat(devices.get(2)).isEqualTo(gw1),
                    () -> assertThat(devices.get(3)).isEqualTo(sw),
                    () -> assertThat(devices.get(4)).isEqualTo(sw1),
                    () -> assertThat(devices.get(5)).isEqualTo(sw2),
                    () -> assertThat(devices.get(6)).isEqualTo(ap1),
                    () -> assertThat(devices.get(7)).isEqualTo(ap2),
                    () -> assertThat(devices.get(8)).isEqualTo(ap)
            );
        }
    }
}