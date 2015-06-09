package com.welter.phoneremoter.service;

/**
 * Created by welter on 15-4-10.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Context;
import android.util.Log;
public class SessionThread extends Thread {
    private Socket _clientSocket = null;
    private final int BUFFER_MAX = 8192;
    private RTPDataHandle _RTP_dataHandle = null;
    private MyLog _myLog = new MyLog(getClass().getName());
    private Context _ctx;

    public SessionThread(Socket clientSocket,Context ctx) {
        this._clientSocket = clientSocket;
        this._ctx=ctx;
    }

    public void closeSocket() {
        if (_clientSocket == null) {
            return;
        }
        try {
            _clientSocket.close();
        } catch (IOException e) {
            _myLog.e(e.getMessage());
        }
    }

    public void run() {
        try {
            int data_l;
            InputStream socketInput = _clientSocket.getInputStream();
            byte[] buffer = new byte[BUFFER_MAX];
            data_l=socketInput.read(buffer);
            if (data_l>0) {
            //if (buffer.length>0) {
                _myLog.l(Log.DEBUG,"read bytes:"+Integer.toString(data_l)+"buffer:"+new String(buffer,0,data_l));
            _RTP_dataHandle = new RTPDataHandle(buffer,_clientSocket.getInetAddress().toString(),data_l,_ctx);
            byte[] content = _RTP_dataHandle.fetchContent();

//            sendResponse(_clientSocket, content);
            }

        } catch (Exception e) {
            _myLog.l(Log.DEBUG, e.getLocalizedMessage()+"Exception in TcpListener");
        }
    }

    private void sendResponse(Socket clientSocket, byte[] content) {
        try {
            OutputStream socketOut = clientSocket.getOutputStream();

            byte[] header = _RTP_dataHandle.fetchHeader(content.length);

            socketOut.write(header);
            socketOut.write(content);

            socketOut.close();
            clientSocket.close();
        } catch (Exception e) {
        }
    }

}
