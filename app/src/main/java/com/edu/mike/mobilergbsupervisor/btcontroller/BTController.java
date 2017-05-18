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
    private volatile boolean stopWorker = false;
    private Thread workerThread;
    private int readBufferPosition;
    private InputStream incomingStream;
    private EventBus eventBus;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    public BTController(Context context){

        this.context = context;

    }

    public void connectBt(String deviceAddres){

        this.deviceAddres = deviceAddres;
        new ConnectBt().execute();








    }

    public void disconnectBt() throws IOException {

        bluetoothSocket.close();
        this.isConnected = false;
        this.stopWorker = true;

    }

    public boolean getConnectionStatus(){

        return this.isConnected;
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
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();
                    incomingStream = bluetoothSocket.getInputStream();
                    //bluetoothDataListener();





                }
            }catch (IOException e){


            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (!isConnectedSuccess){

                Toast.makeText(context,"Connection Failed",Toast.LENGTH_SHORT).show();


            }else {

                Toast.makeText(context,"Connected!",Toast.LENGTH_SHORT).show();
                isConnected = true;
                bluetoothDataListener();


            }

            connectProgress.dismiss();

        }
    }


    public void bluetoothDataListener(){

        Log.d(TAG, "bluetoothDataListener: Test");

        final Handler handler = new Handler();
        final byte delimiter = 10;
        stopWorker = false;
        final byte[] readBuffer = new byte[1024];
        readBufferPosition = 0;

        workerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!Thread.currentThread().isInterrupted() && !stopWorker){

                   // Log.i(TAG, "run: Test");


                    try{


                        int availableBytes = incomingStream.available();

                        if (availableBytes > 0){

                            byte [] pocketBytes = new byte[availableBytes];
                            incomingStream.read(pocketBytes);

                            for (int i = 0; i < availableBytes; i++){

                                byte receivedByte = pocketBytes[i];

                                if (receivedByte == delimiter) {

                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            Toast.makeText(context,data,Toast.LENGTH_LONG).show();
                                            System.out.println("Data: "+data);
                                        }
                                    });

                                } else{

                                        readBuffer[readBufferPosition++] = receivedByte;

                                    }

                                }

                            }





                    }catch (IOException e){

                        Log.d(TAG, "run: "+e.toString());

                    }



                }

            }
        });

        workerThread.start();




    }




















}
