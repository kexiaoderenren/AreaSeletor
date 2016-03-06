package com.example.dgut.cheng.myapplication.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cheng on 2016/3/4.
 */
public class AssetsReaderUtils {

    public static String readFromAsset(Context context, String fileName) {
        String res = "";
        try {
            InputStream in = context.getAssets().open(fileName);
            //InputStream in = getResources().openRawResource(R.raw.area_select);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
