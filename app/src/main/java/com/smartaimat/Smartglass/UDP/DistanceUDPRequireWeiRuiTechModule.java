package com.smartaimat.Smartglass.UDP;

import android.util.Log;

import com.intchip.media.utils.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static com.smartaimat.Smartglass.UI.ResultActivity.mDataHandler;

/**
 * Created by Administrator on 2018/06/07.
 * 适配“威睿晶科的测惧模块”
 */

public class DistanceUDPRequireWeiRuiTechModule {

    private boolean isWIFI702Start = false;
    private byte[] dataRequireCommand = {(byte)0X55, (byte)0XAA, (byte)0X88, (byte)0XFF, (byte)0XFF, (byte)0XFF, (byte)0XFF, (byte)0X84};
//    byte[] backbuf = new byte[9];
//    byte[] temp = backPacket.getData();

    public void distanceUDP(){

//        String msg = isChangeSendString(isWIFI702Start);
        byte[] buf = isChangeSendString(isWIFI702Start);
        try {
            InetAddress address = InetAddress.getByName("192.168.0.1");  //服务器地址
            int port = 8045;  //服务器的端口号
            //创建发送方的数据报信息
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);
            DatagramSocket socket = new DatagramSocket();  //创建套接字
            socket.send(dataGramPacket);  //通过套接字发送数据

            //接收服务器反馈数据
//            byte[] backbuf = new byte[1024];
            byte[] backbuf = new byte[9];
            DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);
//            socket.setSoTimeout(1000);
            socket.setSoTimeout(100);
            socket.receive(backPacket);  //接收返回数据
//            String backMsg = new String(backbuf, 0, backPacket.getLength());
            byte[] temp = backPacket.getData();
            String tempData = byteArrayToHexStr(temp);
//            String tempData2 = bytesToHexString(temp);
            if (tempData == null){
//                Log.d("TIEJIANG", "DistanceUDPRequire---distanceUDP receive data= null"+"restart send start wifi command");
                isWIFI702Start = false;
            }
            String dis = analysisData(tempData);
            Log.d("TIEJIANG", "DistanceUDPRequire---distanceUDP receive tempData= "
                    +tempData + " tempData lenght= "+ tempData.length());
//                    +" tempData2= "+tempData2);
            if (!dis.equals("0.0")){
                mDataHandler.obtainMessage(Constant.LASER_DISTANCE, dis).sendToTarget();
            }
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.d("TIEJIANG", "DistanceUDPRequire---distanceUDP UnknownHostException= "+e.getMessage());
        } catch (IOException e) {
//            isWIFI702Start = false;
            e.printStackTrace();
            Log.d("TIEJIANG", "DistanceUDPRequire---distanceUDP IOException= "+e.getMessage());
        }
    }

    private byte[] isChangeSendString(boolean is_change){

        String startWifi = "S";
//        String dataRequire = "-M1-";
        byte[] bufStartWifi = startWifi.getBytes();
        if (is_change){
            Log.d("TIEJIANG", "DistanceUDPRequire---isChangeSendString"+" return dataRequireCommand");
            return dataRequireCommand;
        }else {
            isWIFI702Start = true;
            Log.d("TIEJIANG", "DistanceUDPRequire---isChangeSendString"+" return bufStartWifi");
            return bufStartWifi;
        }
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private String analysisData(String temp_string){

//        String temp_string = "55AA8801FF00139B";  // test code

        String data = temp_string;
        String packageHead = data.substring(0,4);
        String packageTail = data.substring(14, 16);
        String distance = data.substring(12,14);
        int realDistance = 0;
        double dis = 0;
        int dec_dis = 0;
        Log.d("TIEJIANG", "DistanceUDPRequire---analysisData"
                +" packageHead= "+packageHead
                +" packageTail= "+packageTail
                +" distance = "+ distance);
        if (packageHead.equals("55AA") && !distance.equals("FFFF")){
            realDistance = Integer.parseInt(distance, 16);
            dis = realDistance / 10;
            dec_dis = realDistance % 10;
            Log.d("TIEJIANG", "DistanceUDPRequire---analysisData"+ " dis= "+dis+" dec_dis= "+String.valueOf(dec_dis));

        }

        return String.valueOf(dis);
    }
}
