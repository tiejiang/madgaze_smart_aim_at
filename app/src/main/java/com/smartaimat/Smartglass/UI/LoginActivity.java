package com.smartaimat.Smartglass.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.smartaimat.Smartglass.R;
import com.smartaimat.Smartglass.WifiAutoConnectManager;

/**
 * Created by Administrator on 2018/4/20.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    private Button login, logout;
    private WifiManager mWifiManager = null;
    public WifiAutoConnectManager mWifiAutoConnectManager;
    public static Handler wifiStateHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(this);


        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiAutoConnectManager = new WifiAutoConnectManager(mWifiManager);
//        mWifiAutoConnectManager.connect("121121121121121121121121121", "tiejiang2617**--okc", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_WPA);
        // mWifiAutoConnectManager.connect("a.intchip:56:61", "", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS);
        handleWifiState();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
//                mWifiAutoConnectManager.connect("a.intchip:56:81", "", WifiAutoConnectManager.WifiCipherType.WIFICIPHER_NOPASS);
                Intent mIntent = new Intent(LoginActivity.this, ResultActivity.class);
                startActivity(mIntent);
                break;
            case R.id.logout:
                finish();
                break;
            default:

                break;
        }
    }

    public void handleWifiState(){

        wifiStateHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        Intent mIntent = new Intent(LoginActivity.this, ResultActivity.class);
                        startActivity(mIntent);
                        break;
                    default:

                        break;
                }
            }
        };
    }
}
