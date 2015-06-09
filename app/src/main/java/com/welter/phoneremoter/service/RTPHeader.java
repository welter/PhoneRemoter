package com.welter.phoneremoter.service;

import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by welter on 15-4-13.
 */
public class RTPHeader {
    final static String RTPHeaderStr="RTPHWELTER";
    private MyLog _mylog=new MyLog("RTPHeader");
    private String _method="";
    private String _mousebutton="";
    private int _Xcoordinate1=0;
    private int _Ycoordinate1=0;
    private int _Xcoordinate2=0;
    private int _Ycoordinate2=0;
    private int _key=0 ;
    public RTPHeader(String headerStr){
        analy(headerStr);
    }

    private void analy(String headerStr) {
        _mylog.l(Log.DEBUG,"get in RTPHeader");
        if (headerStr == null || headerStr.length() <= 0) {
            return;
        }
        int headeridx=-1;
        headeridx=headerStr.indexOf(RTPHeaderStr);
        _mylog.l(Log.DEBUG,"headerStr="+headerStr);
        _mylog.l(Log.DEBUG,"headeridx="+Integer.toString(headeridx));
        _mylog.l(Log.DEBUG,"RTPHeader length:" +
                RTPHeaderStr.length()+" headerStr length:"+headerStr.length());
            if (headeridx>=0) {
                headerStr=headerStr.substring(headeridx+RTPHeaderStr.length(),headerStr.length());
                _mylog.l(Log.DEBUG,"headerstr(cuted header):"+headerStr);
                _method = headerStr.substring( 0, 4);
                _mylog.l(Log.DEBUG,"method name:"+_method);
                switch (_method){
                    case "KEYD":
                        _key=Integer.parseInt(headerStr.substring(4, 8));
                        break;
                    case "KEYU":
                        _key=Integer.parseInt(headerStr.substring(4, 8));
                        break;
                    case "KEY ":
                        _key=Integer.parseInt(headerStr.substring(4, 8));
                        break;
                    case "TOH ":
                        _mylog.l(Log.DEBUG,"get TOH command:"+headerStr.substring(5, 9)+","+headerStr.substring(9, 13));
                        _mousebutton=headerStr.substring(4, 5);
                        _Xcoordinate1 = Integer.parseInt(headerStr.substring(5, 9));
                        _Ycoordinate1=Integer.parseInt(headerStr.substring(9, 13));
                        _mylog.l(Log.DEBUG,"analy TOH command finished");
                        break;
                    case "TOHD":
                        _mousebutton=headerStr.substring(4, 5);
                        _Xcoordinate1=Integer.parseInt(headerStr.substring(5, 9));
                        _Ycoordinate1=Integer.parseInt(headerStr.substring(9, 13));
                        break;
                    case "TOHU":
                        _mousebutton=headerStr.substring(4, 5);
                        _Xcoordinate1=Integer.parseInt(headerStr.substring(5, 9));
                        _Ycoordinate1=Integer.parseInt(headerStr.substring(9, 13));
                        break;
                    case "TOHS":
                        _mousebutton=headerStr.substring(4, 5);
                        _Xcoordinate1=Integer.parseInt(headerStr.substring(5, 9), -1);
                        _Ycoordinate1=Integer.parseInt(headerStr.substring(9, 13), -1);
                        _Xcoordinate2=Integer.parseInt(headerStr.substring(13, 17), -1);
                        _Ycoordinate2=Integer.parseInt(headerStr.substring(17, 21),-1);
                        break;
                    case "HOME":
                        _mylog.l(Log.WARN,"analy home command");
                    case "BACK":
                    case "MENU":
                    case "SCRN":
                    case "VOLU":
                    case "VOLD":
                        break;
                    default:
                        _method="UNKNOWN";
                }

            };
    }

    public int get_Xcoordinate1() {
        return _Xcoordinate1;
    }

    public int get_Ycoordinate1() {
        return _Ycoordinate1;
    }

    public int get_Xcoordinate2() {
        return _Xcoordinate2;
    }

    public int get_Ycoordinate2() {
        return _Ycoordinate2;
    }

    public int get_key() {
        return _key;
    }

    public String get_mousebutton() {
        return _mousebutton;
    }

    public String get_method() {
        return _method;
    }
}
