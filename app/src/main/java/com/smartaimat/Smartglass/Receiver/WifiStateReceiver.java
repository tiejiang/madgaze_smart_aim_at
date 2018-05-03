package com.smartaimat.Smartglass.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import static com.smartaimat.Smartglass.UI.LoginActivity.wifiStateHandler;
/**
 * Created by Administrator on 2018/4/20.
 */

public class WifiStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取manager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取wifi状态
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (state != NetworkInfo.State.CONNECTED){
            Toast.makeText(context,"WIFI已断开",Toast.LENGTH_SHORT).show();
        }else if (state == NetworkInfo.State.CONNECTED){
            Toast.makeText(context,"WIFI已连接",Toast.LENGTH_SHORT).show();
            if (wifiStateHandler != null){
                wifiStateHandler.obtainMessage(1, "wifi connected").sendToTarget();
            }

        }
    }
}
