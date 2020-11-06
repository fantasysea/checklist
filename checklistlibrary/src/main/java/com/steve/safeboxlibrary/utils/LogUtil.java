package com.steve.safeboxlibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtil {
    private static String TAG = "LogUtil";

    private static final boolean DEBUG = true;

    private static final int mFileSize = 10;  //10K

    private static final char VERBOSE = 'v';
    private static final char DEBUGL = 'd';
    private static final char INFO = 'i';
    private static final char WARN = 'w';
    private static final char ERROR = 'e';

    public static boolean LEVEL_V = false;
    public static boolean LEVEL_D = false;
    public static boolean LEVEL_I = false;
    public static boolean LEVEL_W = false;
    public static boolean LEVEL_E = false;

    private static String logPath = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);//日期格式;
    private static Date date = new Date();

    public static void init(Context context) {
        logPath = getFilePath(context);
    }

    private static String getFilePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            return context.getExternalFilesDir(null).getPath();   //获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/a8.log
        } else {
            return context.getFilesDir().getPath();  //直接存在/data/data里，非root手机是看不到的
        }
    }

    private LogUtil() {
    }

    public static void setLogLevel(boolean v, boolean d, boolean i, boolean w, boolean e) {
        LEVEL_V = v;
        LEVEL_D = d;
        LEVEL_I = i;
        LEVEL_W = w;
        LEVEL_E = e;
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
        if (LEVEL_V) {
            writeToFile(VERBOSE, tag, msg);
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            Log.v(getClassName(2), msg);
        }
        if (LEVEL_V) {
            writeToFile(VERBOSE, getClassName(2), msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
        if (LEVEL_D) {
            writeToFile(DEBUGL, tag, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(getClassName(2), msg);
        }
        if (LEVEL_D) {
            writeToFile(DEBUGL, getClassName(2), msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
        if (LEVEL_I) {
            writeToFile(INFO, tag, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(getClassName(2), msg);
        }
        if (LEVEL_I) {
            writeToFile(INFO, getClassName(2), msg);
        }
    }

    public static void w(String tag, String msg, IllegalArgumentException ex) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
        if (LEVEL_W) {
            writeToFile(WARN, tag, msg);
        }
    }

    public static void w(String TAG, String msg, IOException ex) {
        if (DEBUG) {
            Log.w(getClassName(2), msg);
        }
        if (LEVEL_W) {
            writeToFile(WARN, getClassName(2), msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
        if (LEVEL_E) {
            writeToFile(ERROR, tag, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(getClassName(2), msg);
        }
        if (LEVEL_E) {
            writeToFile(ERROR, getClassName(2), msg);
        }
    }

    private static String getClassName(int frame) {
        StackTraceElement[] frames = (new Throwable()).getStackTrace();
        return parseClassName(frames[frame].getClassName());
    }

    private static String parseClassName(String fullName) {
        int lastDot = fullName.lastIndexOf('.');
        String simpleName = fullName;
        if (lastDot != -1) {
            simpleName = fullName.substring(lastDot + 1);
        }
        int lastDollar = simpleName.lastIndexOf('$');
        if (lastDollar != -1) {
            simpleName = simpleName.substring(0, lastDollar);
        }
        return simpleName;
    }

    private static void writeToFile(char type, String tag, String msg) {

        if (null == logPath) {
            Log.e(TAG, "logPath == null ，not init logutil");
            return;
        }

        String fileName = logPath + "/doll.log";
        String log = dateFormat.format(date) + " : type  " + type + " ---  tag : " + tag + " --- msg : " + msg + "\n"; //log日志内容，可以自行定制

        File file = new File(logPath);
        try {
            if (file.exists() && getFileSizes(file) / 1000 > mFileSize) {
                deleteFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {
            try {
                fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
                bw = new BufferedWriter(new OutputStreamWriter(fos));
                bw.write(log);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static long getFileSize(File file) {
        if (file.isFile()) {
            return file.length();
        } else {
            return -1;
        }
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
//            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

}
