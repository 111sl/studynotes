package com.example.uncledrew.aidltestservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.io.File;
import java.io.IOException;


public class MyService extends Service {
    private static final String TAG = "MyService";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    RemoteCallbackList<ICallbackInterface> remoteCallbackList = new RemoteCallbackList<>();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }

    private final aidlTest.Stub mBind = new aidlTest.Stub() {
        @Override
        public void first(int a) throws RemoteException {
            Log.d(TAG, "first");
            mediaPlayer.start();
            playCallback(1);
        }

        @Override
        public void second(int b) throws RemoteException {
            Log.d(TAG, "second");
            mediaPlayer.pause();
            playCallback(0);
        }

        @Override
        public void initMediaPlayer() throws RemoteException {
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            try { Log.d(TAG, "initMediaPlayer: "+file.getPath());
                mediaPlayer.setDataSource(file.getPath());

                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void addPlayCallback(ICallbackInterface iCallbackInterface) throws RemoteException {
            if(iCallbackInterface!=null){
                remoteCallbackList.register(iCallbackInterface);
            }
        }

        @Override
        public void removePlayCallback(ICallbackInterface iCallbackInterface) throws RemoteException {
            if(iCallbackInterface!=null){
                remoteCallbackList.unregister(iCallbackInterface);
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }



    private void playCallback(int state){
        int n = remoteCallbackList.beginBroadcast();
        for(int i=0;i<n;i++){
            try {
                remoteCallbackList.getBroadcastItem(i).showState(state);
                remoteCallbackList.getBroadcastItem(i).showTime(state);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

}
