package com.intchip.media;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainActivity extends Activity {

    public StreamMadiaPlayer mStreamMadiaPlayer;
    public SurfaceView mVideoSurfaceView;
    public static MainActivity mMainActivityInstance;
//    private DistanceUDPRequire mDistanceUDPRequire;
//    private boolean isOnLine;
//    private TextView mLaserMeasureDis;
    public static Handler mDataHandler;
//    private final String distanceTitle = "dis: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.layout);

//        mLaserMeasureDis = (TextView)findViewById(R.id.laser_measure_dis);
        Log.d("TIEJIANG", "MainActivity---onCreate");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mMainActivityInstance = this;
        mStreamMadiaPlayer = (StreamMadiaPlayer)findViewById(R.id.main_surface);
    }

    public static MainActivity getMainActivityInstance(){

        return mMainActivityInstance;
    }

    public StreamMadiaPlayer getStreamMadiaPlayerInstance(){

        return mStreamMadiaPlayer;
    }
    @Override
    protected void onStart() {
        super.onStart();
//        Log.d("TIEJIANG", "mStreamMadiaPlayer= "+mStreamMadiaPlayer);
        mStreamMadiaPlayer.onNetworkConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStreamMadiaPlayer.onDestory();
//        isOnLine = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStreamMadiaPlayer.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStreamMadiaPlayer.onResume();
    }
}
