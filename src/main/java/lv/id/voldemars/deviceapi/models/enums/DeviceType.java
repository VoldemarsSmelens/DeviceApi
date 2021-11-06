package lv.id.voldemars.deviceapi.models.enums;

public enum DeviceType {
    GATEWAY(1),
    SWITCH(2),
    ACCESS_POINT(3);

    /**
     * Sorting order attribute
     */
    private final Integer order;

    DeviceType(Integer order){
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
}
