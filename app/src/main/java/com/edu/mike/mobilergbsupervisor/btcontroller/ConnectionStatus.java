package com.edu.mike.mobilergbsupervisor.btcontroller;

/**
 * Created by Mike on 2017-07-20.
 */

public class ConnectionStatus {

    private boolean isConnected;

    public ConnectionStatus (boolean isConnected) {

        this.isConnected = isConnected;


    }

    public boolean getConnectionStatus(){

        return isConnected;

    }

}
