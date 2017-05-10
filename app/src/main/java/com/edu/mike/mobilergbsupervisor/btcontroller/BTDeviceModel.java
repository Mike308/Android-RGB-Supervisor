package com.edu.mike.mobilergbsupervisor.btcontroller;

/**
 * Created by Mike on 2017-05-10.
 */

public class BTDeviceModel {

    private String deviceName;
    private String deviceAdress;

    public BTDeviceModel(String deviceName, String deviceAdress) {
        this.deviceName = deviceName;
        this.deviceAdress = deviceAdress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceAdress() {
        return deviceAdress;
    }


}
