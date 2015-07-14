package com.yanxw.hearttranslation.util;

import android.util.Log;

/**
 * Created by yanxw on 15-7-7.
 */
public class L {

    private L(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;
    private static final String TAG = "tag";

    // 下面四个是默认tag的函数
    public static void i(String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.i(TAG, msg);
        }

    }

    public static void d(String msg, Object ... args)
    {
        if (isDebug) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.e(TAG, msg);
        }
    }

    public static void v(String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.v(TAG, msg);
        }

    }
    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.i(tag, msg);
        }

    }

    public static void d(String tag, String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.d(tag, msg);
        }

    }

    public static void e(String tag, String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.e(tag, msg);
        }

    }

    public static void v(String tag, String msg, Object ... args)
    {
        if (isDebug){
            if (args.length > 0){
                msg = String.format(msg,args);
            }
            Log.v(tag, msg);
        }

    }
}
