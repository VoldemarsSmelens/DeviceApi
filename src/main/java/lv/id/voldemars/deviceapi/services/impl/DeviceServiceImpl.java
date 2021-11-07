package lv.id.voldemars.deviceapi.services.impl;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.MACAddressString;
import lv.id.voldemars.deviceapi.exceptions.DeviceApiException;
import lv.id.voldemars.deviceapi.models.enums.ErrorMessages;
import lv.id.voldemars.deviceapi.persistence.mappers.DeviceMapper;
import lv.id.voldemars.deviceapi.persistence.mappers.DeviceTopologyMapper;
import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.DeviceTopology;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;
import lv.id.voldemars.deviceapi.persistence.DeviceModel;
import lv.id.voldemars.deviceapi.services.DeviceService;
import lv.id.voldemars.deviceapi.persistence.repositories.DeviceRepositoryImpl;
import lv.id.voldemars.deviceapi.utils.DeviceUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepositoryImpl deviceRepository;

    public DeviceServiceImpl(DeviceRepositoryImpl deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void registerDevice(DeviceType deviceType, String macAddress, String upLinkMacAddress) throws DeviceApiException {
        if(deviceType == null) throw new DeviceApiException(ErrorMessages.NO_DEVICE_TYPE);
        if(macAddress == null) throw new DeviceApiException(ErrorMessages.NO_MAC_ADDRESS);
        validateMacAddress(macAddress);

        checkIfDeviceNotRegisteredAlready(macAddress);
        if(upLinkMacAddress != null)checkIfUpLinkDeviceIsRegistered(upLinkMacAddress);

        DeviceModel deviceModel = new DeviceModel()
                .setDeviceType(deviceType)
                .setMacAddress(macAddress)
                .setUpLinkDeviceAddress(upLinkMacAddress);
        deviceRepository.save(deviceModel);
    }

    public List<Device> getDeviceList() {
        return DeviceUtils.sortDevices(getAllDevices());
    }

    public Device getDevice(String macAddress) throws DeviceApiException {
        if(macAddress == null) throw new DeviceApiException(ErrorMessages.NO_MAC_ADDRESS);
        validateMacAddress(macAddress);

        return DeviceUtils.getDeviceFromList(macAddress, getAllDevices());
    }

    public DeviceTopology getTopology(String macAddress) throws DeviceApiException {
        if(macAddress == null) throw new DeviceApiException(ErrorMessages.NO_MAC_ADDRESS);
        validateMacAddress(macAddress);

        return DeviceUtils.getDeviceFromList(macAddress, getAllDeviceTopologies());
    }

    public List<DeviceTopology> getAllTopologies() {
        return DeviceUtils.getRoots(getAllDeviceTopologies());
    }

    public void removeAllDevices() {
        deviceRepository.deleteAll();
    }


    private void validateMacAddress(String macAddress) throws DeviceApiException {
        try {
            new MACAddressString(macAddress).toAddress();
        } catch(AddressStringException e) {
            throw new DeviceApiException(List.of(ErrorMessages.INCORRECT_MAC_ADDRESS), e.getMessage());
        }
    }

    /**
     * Checks if device not registered already
     * @param macAddress Mac address
     * @throws DeviceApiException if validation fails
     */
    private void checkIfDeviceNotRegisteredAlready(String macAddress) throws DeviceApiException{
        if(getDevice(macAddress) != null) throw new DeviceApiException(ErrorMessages.DEVICE_ALREADY_REGISTERED);
    }

    /**
     * Verifies if up link device exist in network
     * @param upLinkMacAddress Up link device Mac address
     * @throws DeviceApiException if validation fails
     */
    private void checkIfUpLinkDeviceIsRegistered(String upLinkMacAddress) throws DeviceApiException{
        if(getDevice(upLinkMacAddress) == null) throw new DeviceApiException(ErrorMessages.UP_LINK_DEVICE_DOES_NOT_EXIST);
    }

    /**
     * Returns all network device list
     * @return all network devices
     */
    private List<Device> getAllDevices(){
        return deviceRepository.findAll().stream().map(DeviceMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Return all connected network device topologies
     * @return all network devices
     */
    private List<DeviceTopology> getAllDeviceTopologies() {
        return DeviceUtils.buildDeviceTopologies(deviceRepository.findAll().stream().map(DeviceTopologyMapper::toDto).collect(Collectors.toList()));
    }


}
