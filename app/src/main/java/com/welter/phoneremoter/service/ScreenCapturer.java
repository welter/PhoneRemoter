package com.welter.phoneremoter.service;


/**
 * Created by welter on 17年11月7日.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.androidyuan.lib.screenshot.Shotter;

public class ScreenCapturer extends Thread {
    private MyLog _myLog = new MyLog(getClass().getName());
    private Context _ctx;
    private Intent _resultIntent;
    private Shotter _shotter;
    public ScreenCapturer(Intent intent, Service service) {

        _ctx=service.getBaseContext();
        _resultIntent=intent;
        if (_shotter==null) _shotter=new Shotter(_ctx,_resultIntent);
    }

    public void quit() {
        try {
        } catch (Exception e) {
            _myLog.l(Log.DEBUG, "Exception closing shotter");
        }
    }

    public void run() {
        Looper.prepare();

        try {
            while (true) {

                _shotter.startScreenShot(new Shotter.OnShotListener() {
                    @Override
                    public void onFinish() {
//                toast("shot finish!");

                    }
                },Shotter.ResultType.RTNet);
                this.sleep(300);

                //postDelayed(this, 300);
            }
        } catch (Exception e) {
            _myLog.l(Log.DEBUG, e.getLocalizedMessage()+"Exception in Scapturer");
        }
        Looper.loop();
    }
}
