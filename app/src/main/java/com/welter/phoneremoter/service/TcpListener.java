package com.welter.phoneremoter.service;

/**
 * Created by welter on 15-4-10.
 */
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Context;
import android.util.Log;
public class TcpListener extends Thread {
    private ServerSocket _listenSocket = null;
    private MyLog _myLog = new MyLog(getClass().getName());
    private Context _ctx;

    public TcpListener(ServerSocket listenSocket, Service service) {
        this._listenSocket = listenSocket;
        _ctx=service.getBaseContext();
    }

    public void quit() {
        try {
            _listenSocket.close(); // if the TcpListener thread is blocked on
            // accept,
            // closing the socket will raise an
            // exception
        } catch (Exception e) {
            _myLog.l(Log.DEBUG, "Exception closing TcpListener listenSocket");
        }
    }

    public void run() {
        try {
            while (true) {

                Socket clientSocket = _listenSocket.accept();
//                _myLog.l(Log.INFO, "New connection, spawned thread");
                SessionThread newSession = new SessionThread(clientSocket,_ctx);
                newSession.start();
            }
        } catch (Exception e) {
            _myLog.l(Log.DEBUG, e.getLocalizedMessage()+"Exception in TcpListener");
        }
    }
}
