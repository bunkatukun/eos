package jp.bunkatusoft.explorersofsettlement.util;

import android.util.Log;

import java.util.Arrays;

public class LogUtil {

    public static final String TAG = "TownBuilder";

    private static final int ID = 4;

    private static String format(String log) {
        StringBuffer sb = new StringBuffer("");
        sb.append("[");
        sb.append(Thread.currentThread().getStackTrace()[ID].getMethodName());
        sb.append("(");
        sb.append(Thread.currentThread().getStackTrace()[ID].getFileName());
        sb.append(":");
        sb.append(Thread.currentThread().getStackTrace()[ID].getLineNumber());
        sb.append(")] ");
        sb.append(log);
        return sb.substring(0);
    }

    public static void v(String text){
        Log.v(TAG,format(text));
    }

    public static void d(String text){
        Log.d(TAG,format(text));
    }

    public static void i(String text){
        Log.i(TAG,format(text));
    }

    public static void w(String text){
        Log.w(TAG,format(text));
    }

    public static void e(String text){
        Log.e(TAG,format(text));
    }

    public static void e(Exception e) {
        e(Arrays.toString(e.getStackTrace()));
    }
}
