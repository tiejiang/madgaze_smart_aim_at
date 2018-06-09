package com.smartaimat.Smartglass.UI;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.intchip.media.utils.Constant;
import com.smartaimat.Smartglass.R;
import com.smartaimat.Smartglass.StreamMadiaPlayer;
import com.smartaimat.Smartglass.UDP.DistanceUDPRequire;
import com.smartaimat.Smartglass.UDP.DistanceUDPRequireWeiRuiTechModule;

import java.io.File;

public class ResultActivity extends Activity {

    private Button detect;
    private Button exit;
    private ImageView imageView;
    private TextView mLaserMeasureDis;
//    private WifiManager mWifiManager = null;
//    public WifiAutoConnectManager mWifiAutoConnectManager;
    public StreamMadiaPlayer mStreamMadiaPlayer;
    public static ResultActivity mResActivityInstance;
    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static   String storagePath = "";
    private DistanceUDPRequire mDistanceUDPRequire;
    private DistanceUDPRequireWeiRuiTechModule mDistanceUDPRequireWeiRuiTechModule;
    private boolean isOnLine;
    public static Handler mDataHandler;
    private static final String DST_FOLDER_NAME = "CameraPic";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mResActivityInstance = this;
        mStreamMadiaPlayer = (StreamMadiaPlayer)findViewById(com.smartaimat.Smartglass.R.id.main_surface);
        initPath();
//        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        mWifiAutoConnectManager = new WifiAutoConnectManager(mWifiManager);
//        mWifiAutoConnectManager.connect("121121121121121121121121121", "tiejiang2617**--okc", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
        // mWifiAutoConnectManager.connect("a.intchip:56:61", "", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS);

        mLaserMeasureDis = (TextView)findViewById(R.id.laser_measure_dis);
        exit = (Button)findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        mDistanceUDPRequire = new DistanceUDPRequire();
        mDistanceUDPRequireWeiRuiTechModule = new DistanceUDPRequireWeiRuiTechModule();
        isOnLine = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOnLine){
                    try{
//                        Thread.sleep(3000);
                        Thread.sleep(500);
//                        mDistanceUDPRequire.distanceUDP();
                        mDistanceUDPRequireWeiRuiTechModule.distanceUDP();
//                        Log.d("TIEJIANG", "MainActivity---onCreate"+" get distance");
                    }catch (InterruptedException i){
                        i.printStackTrace();
                        Log.d("TIEJIANG", "MainActivity---onCreate InterruptedException= "+i.getMessage());
                    }
                }
            }
        }).start();
        handleData();
    }

    public static ResultActivity getResActivityInstance(){

        return mResActivityInstance;
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
       isOnLine = false;
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


    public static String initPath(){
        if(storagePath.equals("")){
            storagePath = parentPath.getAbsolutePath()+"/";
            File f = new File(storagePath);
            if(!f.exists()){
                f.mkdir();
            }
        }
        return storagePath;
    }

    private void handleData(){

        mDataHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case Constant.LASER_DISTANCE:
                        String distance = (String)msg.obj;
                        mLaserMeasureDis.setTextColor(Color.RED);
                        mLaserMeasureDis.setText(distance + "m");
                        break;
                }
            }
        };
    }
}







