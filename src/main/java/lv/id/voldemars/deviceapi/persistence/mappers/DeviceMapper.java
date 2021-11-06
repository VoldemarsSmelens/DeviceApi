package lv.id.voldemars.deviceapi.persistence.mappers;

import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.persistence.DeviceModel;

import java.util.Objects;

public interface DeviceMapper {

    /**
     * Maps Device model to Device dto object
     * @param deviceModel Device model object
     * @return Device dto object
     */
    static Device toDto(DeviceModel deviceModel) {
        if (Objects.isNull(deviceModel)) return null;

        return new Device()
                .setDeviceType(deviceModel.getDeviceType())
                .setMacAddress(deviceModel.getMacAddress())
                .setUpLinkDeviceAddress(deviceModel.getUpLinkDeviceAddress());
    }
}
