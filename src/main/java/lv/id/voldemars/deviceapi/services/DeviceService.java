package lv.id.voldemars.deviceapi.services;

import lv.id.voldemars.deviceapi.exceptions.DeviceApiException;
import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.DeviceTopology;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;

import java.util.List;

public interface DeviceService {

    /**
     * Registers device
     * @param deviceType Device type
     * @param macAddress Mac address
     * @param uplinkMacAddress Up link device Mac address
     * @throws DeviceApiException if validation fails
     */
    void registerDevice(DeviceType deviceType, String macAddress, String uplinkMacAddress) throws DeviceApiException;

    /**
     * Returns Ordered Device list
     * @return Device List
     */
    List<Device> getDeviceList();

    /**
     * Returns specific network device
     * @param macAddress Mac address
     * @return Device
     * @throws DeviceApiException if validation fails
     */
    Device getDevice(String macAddress) throws DeviceApiException;

    /**
     * Returns Connected device topology for device
     * @param macAddress Mac address
     * @return Connected device topology
     * @throws DeviceApiException if validation fails
     */
    DeviceTopology getTopology(String macAddress) throws DeviceApiException;

    /**
     * Returns Connected device topology for all devices
     * @return Connected device topology
     */
    List<DeviceTopology> getAllTopologies();

    /**
     * Removes all registered devices from database
     */
    void removeAllDevices();
}
