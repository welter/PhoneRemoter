package com.welter.phoneremoter.service;

import android.content.Context;

/**
 * Created by welter on 15-4-13.
 */
public class DataHandle  {
    protected String _sourceIP="";
    protected Context _ctx;

    DataHandle(byte[] recieveData, String sourceIP,Context ctx) {
        this._sourceIP=sourceIP;this._ctx=ctx;
    }

    public byte[] fentchContent() {
        return new byte[0];
    }
}
