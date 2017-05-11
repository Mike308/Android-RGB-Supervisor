package com.edu.mike.mobilergbsupervisor.btcontroller;

/**
 * Created by Mike on 2017-05-10.
 */

public class BTDeviceModel {

    private String deviceName;
    private String deviceAddres;

    public BTDeviceModel(String deviceName, String deviceAddress) {
        this.deviceName = deviceName;
        this.deviceAddres = deviceAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceAddres() {
        return deviceAddres;
    }


}
