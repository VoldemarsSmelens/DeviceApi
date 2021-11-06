package lv.id.voldemars.deviceapi.utils;

import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.DeviceInterface;
import lv.id.voldemars.deviceapi.models.DeviceTopology;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface DeviceUtils {
    /**
     * Returns network device based on mac address
     * @param macAddress Mac address
     * @param deviceList List of network devices
     * @param <D> Network device object
     * @return Network device
     */
    static <D extends DeviceInterface> D getDeviceFromList(String macAddress, List<D> deviceList) {
        Optional<D> deviceOpt = deviceList.stream()
                .filter(device -> device.getMacAddress().equals(macAddress)).findFirst();

        return deviceOpt.orElse(null);
    }

    /**
     * Returns all network root devices
     * @param connectedDevices List of network devices
     * @return List of network root devices
     */
    static List<DeviceTopology> getRoots(List<DeviceTopology> connectedDevices) {
        return connectedDevices.stream()
                .filter(device -> device.getUpLinkDeviceAddress() == null)
                .collect(Collectors.toList());
    }

    /**
     * Connects network topology devices
     * @param allDevices List of network devices
     * @return Return list all connected network device topologies
     */
    static List<DeviceTopology> buildDeviceTopologies(List<DeviceTopology> allDevices) {
        allDevices.forEach(device -> {
            DeviceTopology upLinkDevice = getDeviceFromList(device.getUpLinkDeviceAddress(), allDevices);
            if (upLinkDevice != null) {
                device.setUpLinkDevice(upLinkDevice);
            }
        });

        return allDevices;
    }

    /**
     * Sorts network devices based on Device type order and mac address
     * @param allDevices List of network devices
     * @return Sorted device list
     */
    static List<Device> sortDevices(List<Device> allDevices) {
        return allDevices.stream().sorted((o1, o2) -> {
            if (o1.getDeviceType().equals(o2.getDeviceType())) {
                return o1.getMacAddress().compareTo(o2.getMacAddress());
            } else {
                return o1.getDeviceType().getOrder().compareTo(o2.getDeviceType().getOrder());
            }
        }).collect(Collectors.toList());

    }
}
