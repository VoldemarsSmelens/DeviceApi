package lv.id.voldemars.deviceapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import lv.id.voldemars.deviceapi.exceptions.DeviceApiException;
import lv.id.voldemars.deviceapi.models.Device;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;
import lv.id.voldemars.deviceapi.services.DeviceService;
import lv.id.voldemars.deviceapi.services.impl.DeviceServiceImpl;
import lv.id.voldemars.deviceapi.utils.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Tag(name = "Device API controller")
@Log4j2
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/registerDevice")
    @Operation(summary = "Registering a device to a network deployment")
    @Parameter(name = "deviceType", description = "Type of device")
    @Parameter(name = "macAddress", description = "Devices mac address")
    @Parameter(name = "uplinkMacAddress", description = "Up link connected device")
    public void registerDevice(@RequestParam DeviceType deviceType,
                               @RequestParam String macAddress,
                               @RequestParam(required = false) String uplinkMacAddress) throws DeviceApiException {
        log.debug("registerDevice");
        deviceService.registerDevice(deviceType, macAddress, uplinkMacAddress);
    }

    public DeviceController(DeviceServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/getAllDevices")
    @Operation(summary = "Retrieving all registered devices")
    public List<Device> getAllDevices() {
        log.debug("getAllDevices");
        return deviceService.getDeviceList();
    }

    @GetMapping("/getDevice")
    @Operation(summary = "Retrieving network deployment device")
    @Parameter(name = "macAddress", description = "Devices mac address")
    public Device getAllDevices(String macAddress) throws DeviceApiException {
        log.debug("getDevice");
        return deviceService.getDevice(macAddress);
    }

    @GetMapping("/getAllDeviceTopology")
    @Operation(summary = "Retrieving all registered network device topology")
    public String getAllDeviceTopology() {
        log.debug("getAllDeviceTopology");
        return Gson.getGson().toJson(deviceService.getAllTopologies());
    }

    @GetMapping("/getDeviceTopologyByRoot")
    @Operation(summary = "Retrieving network device topology starting from a specific device")
    @Parameter(name = "macAddress", description = "Devices mac address")
    public String getDeviceTopologyByRoot(String macAddress) throws DeviceApiException {
        log.debug("getDeviceTopologyByRoot");
        return Gson.getGson().toJson(deviceService.getTopology(macAddress));
    }

    @GetMapping("/removeAllDevices")
    @Operation(summary = "Removes all registered devices")
    public void removeAllDevices() throws DeviceApiException {
        log.debug("removeAllDevices");
        deviceService.removeAllDevices();
    }

}
