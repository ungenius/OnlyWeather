package com.allen.onlyweather.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Allen.
 */

public class FileUtil {

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static String assetFile2String(String fileName, Context context) {
        StringBuilder result = new StringBuilder("");
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeIO(inputStreamReader, bufferedReader);
        return result.toString();
    }

    private static void closeIO(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
