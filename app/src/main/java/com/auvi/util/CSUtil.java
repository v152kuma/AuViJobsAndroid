package com.auvi.util;

import android.util.Base64;

import com.auvi.application.CSApplicationHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public class CSUtil {

    public static String getFileContent(String fileName) {
        return streamToString(getFileFromAsset(fileName));
    }

    public static InputStream getFileFromAsset(String fileName) {
        try {
            if (isNonEmptyStr(fileName)) {
                return CSApplicationHelper.application().getActivity().getAssets().open(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNonEmptyStr(String fileName){
        return !isEmptyStr(fileName);
    }

    public static boolean isEmptyStr(String _v) {
        return _v == null || _v.trim().length() == 0 || _v.equalsIgnoreCase("null");
    }
    public static String streamToString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            StringBuilder fileContent = new StringBuilder();
            String str;
            while ((str = bufferReader.readLine()) != null) {
                fileContent.append(str.trim());
            }
            return fileContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encodeBase64(String data) {
        byte[] bytes = null;
        bytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.encode(bytes, Base64.DEFAULT);
        return new String(encodedBytes);
    }

}

