package lv.id.voldemars.deviceapi.persistence;

import lombok.Data;
import lombok.experimental.Accessors;
import lv.id.voldemars.deviceapi.models.enums.DeviceType;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "DEVICES")
public class DeviceModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_type")
    private DeviceType deviceType;

    @Column(name = "mac_address", length=250)
    private String macAddress;

    @Column(name = "up_link_device_address", length=250)
    private String upLinkDeviceAddress;
}
