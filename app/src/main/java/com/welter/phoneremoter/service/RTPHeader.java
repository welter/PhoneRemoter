package com.welter.phoneremoter.service;

import java.io.UnsupportedEncodingException;

/**
 * Created by welter on 15-4-13.
 */
public class RTPHeader {
    final static String RTPHeaderStr="RTPHWELTER";
    private String _method="";
    private String _mousebutton="";
    private int _Xcoordinate1=0;
    private int _Ycoordinate1=0;
    private int _Xcoordinate2=0;
    private int _Ycoordinate2=0;
    private String _key="" ;
    public void RTPHeader(String headerStr){
        analy(headerStr);
    }

    private void analy(String headerStr) {
        if (headerStr == null || headerStr.length() <= 0) {
            return;
        }
        int headeridx = 0 ;
        headeridx=headerStr.indexOf(RTPHeaderStr);
            if (headeridx>=0) {
                _method = headerStr.substring(headeridx + 1, headeridx + 5);
                switch (_method){
                    case "KEYD":
                        _key=headerStr.substring(headeridx+5,headeridx+6);
                        break;
                    case "KEYU":
                        _key=headerStr.substring(headeridx+5,headeridx+6);
                        break;
                    case "KEY ":
                        _key=headerStr.substring(headeridx+5,headeridx+6);
                        break;
                    case "MOSC":
                        _mousebutton=headerStr.substring(headeridx + 5, headeridx + 6);
                        _Xcoordinate1=Integer.getInteger(headerStr.substring(headeridx + 6, headeridx + 9),-1);
                        _Ycoordinate1=Integer.getInteger(headerStr.substring(headeridx + 9, headeridx + 12),-1);
                        break;
                    case "MOSD":
                        _mousebutton=headerStr.substring(headeridx + 5, headeridx + 6);
                        _Xcoordinate1=Integer.getInteger(headerStr.substring(headeridx + 6, headeridx + 9),-1);
                        _Ycoordinate1=Integer.getInteger(headerStr.substring(headeridx + 9, headeridx + 12),-1);
                        _Xcoordinate2=Integer.getInteger(headerStr.substring(headeridx + 12, headeridx + 15),-1);
                        _Ycoordinate2=Integer.getInteger(headerStr.substring(headeridx + 15, headeridx + 18),-1);
                };

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

    public String get_key() {
        return _key;
    }

    public String get_mousebutton() {
        return _mousebutton;
    }

    public String get_method() {
        return _method;
    }
}
