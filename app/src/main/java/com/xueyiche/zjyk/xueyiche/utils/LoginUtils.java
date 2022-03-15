package com.xueyiche.zjyk.xueyiche.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xueyiche.zjyk.xueyiche.constants.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ZL on 2017/7/24.
 */
public class LoginUtils {
    public static String getId(final Activity activity) {
        String szImei = App.szImei;
        if (TextUtils.isEmpty(szImei)) {
//            TelephonyManager TelephonyMgr = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
//            String m_szImei = TelephonyMgr.getDeviceId();
//            String m_szDevIDShort = "35" +
//                    Build.BOARD.length() % 10 +
//                    Build.BRAND.length() % 10 +
//                    Build.CPU_ABI.length() % 10 +
//                    Build.DEVICE.length() % 10 +
//                    Build.DISPLAY.length() % 10 +
//                    Build.HOST.length() % 10 +
//                    Build.ID.length() % 10 +
//                    Build.MANUFACTURER.length() % 10 +
//                    Build.MODEL.length() % 10 +
//                    Build.PRODUCT.length() % 10 +
//                    Build.TAGS.length() % 10 +
//                    Build.TYPE.length() % 10 +
//                    Build.USER.length() % 10;
//            String m_szAndroidID = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
//            WifiManager wm = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
//            BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
//            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            String m_szBTMAC = m_BluetoothAdapter.getAddress();
//            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
//            String m_szLongID = m_szImei + m_szDevIDShort
//                    + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
//            MessageDigest m = null;
//            try {
//                m = MessageDigest.getInstance("MD5");
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
//            byte p_md5Data[] = m.digest();
//            String m_szUniqueID = new String();
//            for (int i = 0; i < p_md5Data.length; i++) {
//                int b = (0xFF & p_md5Data[i]);
//                if (b <= 0xF)
//                    m_szUniqueID += "0";
//                m_szUniqueID += Integer.toHexString(b);
//            }
//            m_szUniqueID = m_szUniqueID.toUpperCase();
            return "123456";
        }else {
            return szImei;
        }
    }
}
