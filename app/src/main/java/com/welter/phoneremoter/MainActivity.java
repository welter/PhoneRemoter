package com.welter.phoneremoter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.welter.phoneremoter.service.MyLog;
import com.welter.phoneremoter.service.RemoterService;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    final static String RServiceName="com.welter.phoneremoter.service.RemoterService";
    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;
    boolean GetServiceStatus(String ServiceName ){
        int tag=0;
        ActivityManager manager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> mServiceList = manager.getRunningServices(100);
        for (int i = 0; i < mServiceList.size(); i++) {
            String clasString=mServiceList.get(i).service.getClassName();
            if (clasString.equals(ServiceName)) {
                tag=1;
            }
        }
        if (tag==0)
            return false;
            else
            return true;
    }
    View.OnClickListener OnStartClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!GetServiceStatus(RServiceName)){
                TextView T_ServiceStatus = (TextView) findViewById(R.id.textView);
                T_ServiceStatus.setTextColor(Color.GREEN);
                T_ServiceStatus.setText(getText(R.string.status_running));
                if (Build.VERSION.SDK_INT >= 21) {
                    startActivityForResult(
                            ((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(),
                            REQUEST_MEDIA_PROJECTION
                    );
                }
                else
                {
                    Log.i(this.getClass().getName(),"版本过低,无法截屏");
                }
//                Toast.makeText(this.getContext(),"",5);
//                startService(new Intent(MainActivity.this, RemoterService.class));
//                RemoterService.Start(MainActivity.this.getApplicationContext());
            }
        }
    };
    View.OnClickListener OnStopClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (GetServiceStatus(RServiceName)) {
                TextView T_ServiceStatus = (TextView) findViewById(R.id.textView);
                T_ServiceStatus.setTextColor(Color.RED);
                T_ServiceStatus.setText(getText(R.string.status_stop));
//                stopService(new Intent(MainActivity.this,RemoterService.class));
                RemoterService.Stop(MainActivity.this.getApplicationContext());
            }
        }
    };
    CompoundButton.OnCheckedChangeListener OnCheckBoxClick=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {

            EditText E_IRAL=(EditText) findViewById(R.id.editText);
            E_IRAL.setCursorVisible(isChecked);//隐藏光标
            E_IRAL.setFocusable(isChecked);//失去焦点
            E_IRAL.setFocusableInTouchMode(isChecked);//虚拟键盘隐藏
            if (isChecked) {
                E_IRAL.requestFocus();
                E_IRAL.requestFocusFromTouch();
            }



        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {
                if (resultCode == -1 && data != null) {
                    /*Shotter shotter=new Shotter(ScreenShotActivity.this,data);
                    shotter.startScreenShot(new Shotter.OnShotListener() {
                        @Override
                        public void onFinish() {
                            toast("shot finish!");

                        }
                    },Shotter.ResultType.RTNet);*/
//                    Intent intent=new Intent(this, RemoterService.class);
//                    intent.putExtra("ResultIntent", data);
//                    startService(intent);
                    RemoterService.Start(MainActivity.this.getApplicationContext(),data);
//                    startService(intent);

//                    finish(); // don't forget finish activity
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button B_start=(Button) findViewById(R.id.button);
        B_start.setOnClickListener(OnStartClick);
        Button B_stop=(Button) findViewById(R.id.button2);
        B_stop.setOnClickListener(OnStopClick);
        CheckBox C_IRAL=(CheckBox) findViewById(R.id.checkBox);
        C_IRAL.setOnCheckedChangeListener(OnCheckBoxClick);
        EditText E_IRAL=(EditText) findViewById(R.id.editText);
        E_IRAL.setCursorVisible(false);//隐藏光标
        E_IRAL.setFocusable(false);//失去焦点
        E_IRAL.setFocusableInTouchMode(false);//虚拟键盘隐藏

        TextView T_ServiceStatus=(TextView) findViewById(R.id.textView);
        if (!GetServiceStatus(RServiceName)) {
//服务未被开启
            T_ServiceStatus.setTextColor(Color.RED);
            T_ServiceStatus.setText(this.getText(R.string.status_stop));
        }else{
//服务在运行状态
            T_ServiceStatus.setTextColor(Color.GREEN);
            T_ServiceStatus.setText(this.getText(R.string.status_running));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
