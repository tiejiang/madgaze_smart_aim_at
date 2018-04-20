package com.smartaimat.Smartglass;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.intchip.media.SDLNative;
import java.io.File;

public class ResultActivity extends Activity {

    private Button voice;
    private Button takepic;
    private Button facepic;
    private Button detect;
    private Button button;
    private ImageView imageView;
    private TextView tv;
    private static Handler handler;
    private Context context = this;
    private WifiManager mWifiManager = null;
    public WifiAutoConnectManager mWifiAutoConnectManager;
    public StreamMadiaPlayer mStreamMadiaPlayer;
    public static ResultActivity mResActivityInstance;
    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static   String storagePath = "";
    private static final String DST_FOLDER_NAME = "CameraPic";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mResActivityInstance = this;
        mStreamMadiaPlayer = (StreamMadiaPlayer)findViewById(com.smartaimat.Smartglass.R.id.main_surface);
        initPath();
        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiAutoConnectManager = new WifiAutoConnectManager(mWifiManager);
//        mWifiAutoConnectManager.connect("121121121121121121121121121", "tiejiang2617**--okc", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
        // mWifiAutoConnectManager.connect("a.intchip:56:61", "", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS);

        imageView = (ImageView) findViewById(R.id.pic);
        button = (Button) findViewById(R.id.btn_take_pic);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDLNative.snapshot(ResultActivity.initPath()+"temp.jpeg");
//                Intent BTintent = new Intent(ResultActivity.this, ClientActivity.class);
//                startActivity(BTintent);
            }
        });
        detect = (Button) findViewById(R.id.detect);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResultActivity.this, CameraActivity.class);
//                startActivityForResult(intent, 0);
            }
        });
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
//       isOnLine = false;
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
}







