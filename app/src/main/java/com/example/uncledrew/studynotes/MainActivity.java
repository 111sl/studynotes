package com.example.uncledrew.studynotes;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uncledrew.aidltestservice.ICallbackInterface;
import com.example.uncledrew.aidltestservice.aidlTest;
import com.example.uncledrew.studynotes.service.LocalService;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int UPDATE_TEXT = 1;
    public static aidlTest mAidlTest;
    private TextView state_textview;
    private TextView time_textview;
    private static int time = 0;

    //设定状态的变量，true为播放，false为暂停
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start_button = findViewById(R.id.start_button);
        Button bind_button = findViewById(R.id.bind_button);
        Button stop_button = findViewById(R.id.stop_button);
        Button unbind_button = findViewById(R.id.unbind_button);
        Button first_button = findViewById(R.id.first_button);
        Button second_button = findViewById(R.id.second_button);
        state_textview = findViewById(R.id.state);
        time_textview = findViewById(R.id.time);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LocalService.class);
                startService(intent);
            }
        });

        bind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.uncle.serviceTest");
                intent.setPackage("com.example.uncledrew.aidltestservice");
                bindService(intent,connection,BIND_AUTO_CREATE);
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LocalService.class);
                stopService(intent);
            }
        });

        unbind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LocalService.class);
                unbindService(connection);
            }
        });

        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAidlTest.play(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAidlTest.pause(2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mAidlTest = aidlTest.Stub.asInterface(service);
            //实现回调函数中方法
            try {
                mAidlTest.addPlayCallback(new ICallbackInterface.Stub() {
                    @Override
                    public void showState(int state) throws RemoteException {
                        Log.d(TAG, "showState "+state);
                        if(state==0){
                            state_textview.setText("暂停");
                        }else if(state==1){
                            state_textview.setText("正在播放");
                        }
                    }
                    @Override
                    public void showTime(int state) throws RemoteException {
                        Log.d(TAG, "showTime");
                        if(state==0){
                            flag = false;
                        }else if(state==1){
                            flag = true;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Message message = new Message();
                                    message.what = UPDATE_TEXT;
                                    handler.sendMessage(message);
                                }
                            }).start();
                        }

                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //绑定后获取权限
            if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },1);
            }else{
                try {
                    mAidlTest.initMediaPlayer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    try {
                        mAidlTest.initMediaPlayer();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this,"ddddd",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //new一个hanler处理发送来的消息执行UI在子线程的更新
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    time_textview.setText(time+" s");
                    time++;
                    removeMessages(UPDATE_TEXT);
                    if(flag){
                        sendEmptyMessageDelayed(UPDATE_TEXT, 1000);//这里想几秒刷新一次就写几秒
                    }
                    break;
                default:
                    break;
            }
        }
    };


}
