package com.steve.safeboxlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ToolsUtil {
    private static String TAG = "ToolsUtil";
    private static String data = "";

    public static int getVersionCode(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            LogUtil.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static String  exeCmd(String cmd) {
        Process proc = null;
        String result = "";
        LogUtil.i(TAG, " cmd : " + cmd);
        try {
            proc = Runtime.getRuntime().exec(cmd);
            if (proc == null) {
                return result;
            }
            InputStream inputStream = proc.getInputStream();
            StringBuffer stringBuffer = new StringBuffer("");
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) > 0)
                stringBuffer.append(new String(buffer, 0, length));
            result  = stringBuffer.toString();
            inputStream.close();
            LogUtil.i(TAG, "result :" + result);
            proc.getOutputStream().close();
            proc.getErrorStream().close();
            int exitVal = proc.waitFor();
            return  result;
//            LogUtil.i(TAG, "Process exitValue: : " + exitVal);
        } catch (IOException e) {
            LogUtil.i(TAG, "IOException : " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            LogUtil.i(TAG, "InterruptedException : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (proc != null && proc.exitValue() != 0) {
                proc.destroy();
            }
            return result;
        }
    }
}
