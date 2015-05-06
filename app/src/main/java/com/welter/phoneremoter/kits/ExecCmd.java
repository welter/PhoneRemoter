package com.welter.phoneremoter.kits;

import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 * Created by welter on 15-5-6.
 */
public class ExecCmd {
    public ExecCmd() {
    }

    /**
     * 执行shell命令
     *
     * @param cmd
     */
    static public void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
