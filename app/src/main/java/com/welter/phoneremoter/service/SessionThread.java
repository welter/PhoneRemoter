package com.welter.phoneremoter.service;

/**
 * Created by welter on 15-4-10.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.util.Log;
public class SessionThread extends Thread {
    private Socket _clientSocket = null;
    private final int BUFFER_MAX = 8192;
    private HttpDataHandle _Http_dataHandle = null;
    private MyLog _myLog = new MyLog(getClass().getName());

    public SessionThread(Socket clientSocket) {
        this._clientSocket = clientSocket;
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

            InputStream socketInput = _clientSocket.getInputStream();
            byte[] buffer = new byte[BUFFER_MAX];
            socketInput.read(buffer);
            _Http_dataHandle = new HttpDataHandle(buffer,_clientSocket.getInetAddress().toString());
            byte[] content = _Http_dataHandle.fetchContent();

            sendResponse(_clientSocket, content);

        } catch (Exception e) {
            _myLog.l(Log.DEBUG, "Exception in TcpListener");
        }
    }

    private void sendResponse(Socket clientSocket, byte[] content) {
        try {
            OutputStream socketOut = clientSocket.getOutputStream();

            byte[] header = _Http_dataHandle.fetchHeader(content.length);

            socketOut.write(header);
            socketOut.write(content);

            socketOut.close();
            clientSocket.close();
        } catch (Exception e) {
        }
    }

}
