package lv.id.voldemars.deviceapi.models;

import lombok.Data;
import lombok.experimental.Accessors;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class DeviceTopology implements DeviceInterface,  Serializable {

    private DeviceType deviceType;

    private String macAddress;

    private List<DeviceTopology> connectedDevices = new ArrayList<>();

    private transient String upLinkDeviceAddress;

    public void setUpLinkDevice(DeviceTopology upLinkDevice) {
        upLinkDevice.connectedDevices.add(this);
    }
}
