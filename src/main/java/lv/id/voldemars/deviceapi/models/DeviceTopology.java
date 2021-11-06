package lv.id.voldemars.deviceapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "Network device")
public class DeviceTopology implements DeviceInterface,  Serializable {

    @Schema(description = "Network device type", example = "SWITCH")
    private DeviceType deviceType;

    @Schema(description = "Device mac address", example = "90-AB-34-9E-01-B2")
    private String macAddress;

    @Schema(description = "List of connected devices")
    private List<DeviceTopology> connectedDevices = new ArrayList<>();

    @Schema(description = "Up link device mac address", example = "90-AB-34-9E-01-B2")
    private transient String upLinkDeviceAddress;

    public void setUpLinkDevice(DeviceTopology upLinkDevice) {
        upLinkDevice.connectedDevices.add(this);
    }
}
