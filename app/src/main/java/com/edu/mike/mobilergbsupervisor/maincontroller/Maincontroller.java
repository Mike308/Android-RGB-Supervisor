package com.edu.mike.mobilergbsupervisor.maincontroller;

import android.content.Context;

import com.edu.mike.mobilergbsupervisor.btcontroller.BTController;
import com.edu.mike.mobilergbsupervisor.btcontroller.BTDeviceModel;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Mike on 2017-05-27.
 */

public class Maincontroller extends BTController {


    public Maincontroller(Context context){

        super(context);

    }


    public void setRGB(int r, int g, int b){

        sendBT("AT+RGB="+Integer.toString(r)+","+Integer.toString(g)+","+Integer.toString(b)+"\r\n");


    }

    public void setHSV(int h, int s, int v){

        sendBT("AT+HSV"+Integer.toString(h)+","+Integer.toString(s)+","+Integer.toString(v));
    }

    public Set<BTDeviceModel> getControllerAddress(){

        return getBTDevices();

    }

    public void  connectToController(String deviceAddress){

        connectBt(deviceAddress);


    }

    public void disconnectFromController() throws IOException {

        disconnectBt();
    }

    public boolean getStatus(){

        return getConnectionStatus();
    }


































}
