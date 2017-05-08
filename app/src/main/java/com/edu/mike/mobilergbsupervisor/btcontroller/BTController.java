package com.edu.mike.mobilergbsupervisor.btcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Mike on 2017-05-08.
 */

public class BTController {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private boolean isConnected;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public void sendBT(String str){

        if (bluetoothSocket != null){

            try{

                bluetoothSocket.getOutputStream().write(str.getBytes());


            }catch (IOException e){


            }

        }

    }







}
