package com.welter.phoneremoter.service;

/**
 * Created by welter on 15-4-13.
 */
public class DataHandle  {
    private String _sourceIP="";

    DataHandle(byte[] recieveData, String sourceIP) {
        this._sourceIP=sourceIP;
    }
    
    public byte[] fentchContent() {
        return new byte[0];
    }
}
