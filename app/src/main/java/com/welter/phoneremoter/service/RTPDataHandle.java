package com.welter.phoneremoter.service;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.welter.phoneremoter.R;
import com.welter.phoneremoter.kits.ExecCmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by welter on 15-4-14.
 */
public class RTPDataHandle extends DataHandle{
    private RTPHeader _RTPHeader;
    private List<String> _cmd =new ArrayList<String>();
    private MyLog _mylog=new MyLog(getClass().getName());
    public RTPDataHandle(byte[] recieveData, String sourceIP,int readbytes,Context ctx) {
        super(recieveData, sourceIP,ctx);
        _mylog.l(Log.DEBUG, "recivedata:" + new String(recieveData, 0,readbytes));
        _RTPHeader=new RTPHeader(new String(recieveData, 0,readbytes));
    }
    public byte[] fetchContent() throws IOException, InterruptedException {
        _cmd.clear();
        Resources res =  _ctx.getResources();
        String[] temp;
        switch (_RTPHeader.get_method()){
            case "KEYD":


//                _cmd.add("input text" + _RTPHeader.get_key() + ")");
                break;
            case "KEYU":

//                _cmd.add("input text" + _RTPHeader.get_key() + ")");

                break;
            case "KEY ":
                temp=res.getStringArray(R.array.key);
                temp[0].replace("%k",Integer.toString(_RTPHeader.get_key()));
                Collections.addAll(_cmd, temp);
                ExecCmd.execShellCmd(_cmd);
//                _cmd.add("input text" + _RTPHeader.get_key() + ")");
                break;
            case "TOH ":
//                _cmd.add("input text" + _RTPHeader.get_key() + ")");
                temp=res.getStringArray(R.array.touch);
                //_mylog.l(Log.DEBUG,"begin exec TOH command:"+temp[1]+","+temp[2]+","+temp[3]);
                //temp[1]=temp[1].replace("%x", Integer.toString(_RTPHeader.get_Xcoordinate1()));
                //temp[2]=temp[2].replace("%y",Integer.toString(_RTPHeader.get_Ycoordinate1()));
                temp[0]=temp[0].replace("%x", Integer.toString(_RTPHeader.get_Xcoordinate1()));
                temp[0]=temp[0].replace("%y", Integer.toString(_RTPHeader.get_Ycoordinate1()));
                //temp[3]=temp[3].replace("%p",Integer.toString(40));
                Collections.addAll(_cmd, temp);
                _mylog.l(Log.DEBUG,"exec TOH command");
                ExecCmd.execShellCmd(_cmd);
                break;
            case "TOHD":
                break;
            case "TOHU":
                break;
            case "TOHS":
                break;
            case "SCRN":
                break;
            case "BACK":
                Collections.addAll(_cmd, res.getStringArray(R.array.back));
                ExecCmd.execShellCmd(_cmd);
                break;
            case "HOME":
                _mylog.l(Log.WARN,"get home command");
                Collections.addAll(_cmd, res.getStringArray(R.array.home));
                ExecCmd.execShellCmd(_cmd);
                break;
            case "MENU":
                Collections.addAll(_cmd, res.getStringArray(R.array.menu));
                ExecCmd.execShellCmd(_cmd);
                break;
            case "VOLU":
                Collections.addAll(_cmd, res.getStringArray(R.array.volup));
                ExecCmd.execShellCmd(_cmd);
                break;
            case "VOLD":
                Collections.addAll(_cmd, res.getStringArray(R.array.voldown));
                ExecCmd.execShellCmd(_cmd);
                break;
        }
        return null;
    }

    public byte[] fetchHeader(int length) {
        return (byte[])RTPHeader.RTPHeaderStr.getBytes();
    }
}
