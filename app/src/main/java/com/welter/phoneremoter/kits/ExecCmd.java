package com.welter.phoneremoter.kits;

import android.util.Log;

import com.welter.phoneremoter.service.MyLog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by welter on 15-5-6.
 */
public class ExecCmd {
    static private int _cmdInterval=5;
    private static MyLog _mylog=new MyLog("execcmd");
    public ExecCmd() {
    }

    /**
     * 执行shell命令
     *
     * @param cmd
     */
    static public void execShellCmd(List<String> cmd) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("su");
        // 获取输出流
        OutputStream outputStream = process.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(
                outputStream);
        for(String str:cmd){

            // 申请获取root权限，这一步很重要，不然会没有作用
            _mylog.l(Log.DEBUG,"execcmd=" +
                    str);
            dataOutputStream.writeBytes(str);
            dataOutputStream.flush();
            Thread thread = Thread.currentThread();
            thread.sleep(_cmdInterval);}

        dataOutputStream.close();
        outputStream.close();
    }
    static public void setCmdInterval(int Interval){
        _cmdInterval=Interval;
    }
    static public int get_cmdInterval(){
        return _cmdInterval;
    }
}
