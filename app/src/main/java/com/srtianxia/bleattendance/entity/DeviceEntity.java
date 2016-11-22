package com.srtianxia.bleattendance.entity;

/**
 * Created by srtianxia on 2016/7/31.
 */
public class DeviceEntity {
    public String name;
    public String address;


    public DeviceEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }


    @Override public boolean equals(Object obj) {
        DeviceEntity entity = (DeviceEntity) obj;
        return entity.address.equals(address);
    }
}
