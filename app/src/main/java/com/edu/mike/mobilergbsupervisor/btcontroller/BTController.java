package com.edu.mike.mobilergbsupervisor.btcontroller;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Mike on 2017-05-08.
 */

public class BTController {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private boolean isConnected;
    private  Context context;
    private String deviceAddres = " ";

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    public BTController(Context context){

        this.context = context;

    }

    public void connectBt(String deviceAddres){

        this.deviceAddres = deviceAddres;
        new ConnectBt().execute();


    }

    public void sendBT(String str){

        if (bluetoothSocket != null){

            try{

                bluetoothSocket.getOutputStream().write(str.getBytes());


            }catch (IOException e){


            }

        }

    }



    public Set<BTDeviceModel> getBTDevices(){

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> btDeviceSet = bluetoothAdapter.getBondedDevices();
        Set<BTDeviceModel> btDeviceModelSet = new HashSet<>();

        for (BluetoothDevice btDevice: btDeviceSet){

            btDeviceModelSet.add(new BTDeviceModel(btDevice.getName(),btDevice.getAddress()));


        }

        return btDeviceModelSet;


    }

    private class ConnectBt extends AsyncTask<Void,Void,Void>{

        private boolean isConnectedSuccess = true;
        private ProgressDialog connectProgress;


        @Override
        protected void onPreExecute() {

            connectProgress = ProgressDialog.show(context,"Connecting...","Please wait...");


        }

        @Override
        protected Void doInBackground(Void... devices) {

            try{

                if (bluetoothSocket == null || !isConnected){


                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddres);
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();





                }
            }catch (IOException e){


            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!isConnectedSuccess){

                Toast.makeText(context,"Connection Failed",Toast.LENGTH_LONG).show();


            }else {

                Toast.makeText(context,"Connected!",Toast.LENGTH_LONG).show();
                isConnected = true;

            }

            connectProgress.dismiss();

        }
    }


















}
