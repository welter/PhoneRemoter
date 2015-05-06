package com.welter.phoneremoter.service;

import java.io.IOException;
import com.welter.phoneremoter.kits.ExecCmd;

/**
 * Created by welter on 15-4-14.
 */
public class RTPDataHandle extends DataHandle{
    private RTPHeader _RTPHeader;
    private Process proc;
    private String cmd="";
    public RTPDataHandle(byte[] recieveData, String sourceIP) {
        super(recieveData, sourceIP);
        _RTPHeader=new RTPHeader(recieveData.toString());
    }
    public byte[] fetchContent() {
        try {

              proc = Runtime.getRuntime().exec("su");  // 设备需要拥有su权限
//            proc.getOutputStream().write(cmd.getBytes());
//            proc.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (_RTPHeader.get_method()){
            case "KEYD":
                cmd="sendevent("+ _RTPHeader.get_key()+")";
                try{
                    proc.getOutputStream().write(cmd.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "KEYU":
                break;
            case "KEY ":
                break;
            case "MOSC":
                break;
            case "MOSD":
        }
        return null;
    }
}
