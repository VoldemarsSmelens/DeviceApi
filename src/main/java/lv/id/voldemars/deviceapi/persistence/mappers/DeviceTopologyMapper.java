package lv.id.voldemars.deviceapi.persistence.mappers;

import lv.id.voldemars.deviceapi.models.DeviceTopology;
import lv.id.voldemars.deviceapi.persistence.DeviceModel;

import java.util.Objects;

public interface DeviceTopologyMapper {

    /**
     * Maps Device model to Device topology dto object
     * @param deviceModel Device model object
     * @return Device topology dto object
     */
    static DeviceTopology toDto(DeviceModel deviceModel) {
        if (Objects.isNull(deviceModel)) return null;

        return new DeviceTopology()
                .setDeviceType(deviceModel.getDeviceType())
                .setMacAddress(deviceModel.getMacAddress())
                .setUpLinkDeviceAddress(deviceModel.getUpLinkDeviceAddress());

    }
}
