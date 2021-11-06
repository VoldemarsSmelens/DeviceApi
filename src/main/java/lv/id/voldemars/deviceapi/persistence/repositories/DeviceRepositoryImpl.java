package lv.id.voldemars.deviceapi.persistence.repositories;


import lv.id.voldemars.deviceapi.persistence.DeviceModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeviceRepositoryImpl extends CrudRepository<DeviceModel, Long> {
    List<DeviceModel> findAll();
}
