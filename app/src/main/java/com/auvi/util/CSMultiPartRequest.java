package com.auvi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.auvi.view.CSProgressBar;
import com.auvi.application.CSApplicationHelper;
import com.auvi.listener.CSResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CSMultiPartRequest {

    public static void updateProfile(Context context,
                                             String url,
                                             CSResponseListener csResponseListener,
                                             HashMap params,
                                             Uri selectedURI,
                                             int requestCode,
                                             boolean isProgressShow) {

        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            return;
        }
        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse result) {
                cstProgressBar.dismiss();
                String resultResponse = new String(result.data);
                try {
                    if(CSStringUtil.isNonEmptyStr(resultResponse)) {
                        JSONObject response = new JSONObject(resultResponse);
//                        if(response.has("errCode") && response.getString("errCode").equalsIgnoreCase("-1"))
                            csResponseListener.onGetResponse(response,requestCode);
/*                        else {
                            if (response.has("message"))
                                CSAppUtil.showToast(response.getString("message"));
                            csResponseListener.onFail(requestCode);
                        }*/
                        Log.i("Messsage", result.toString());
                    }
                    else {
                        csResponseListener.onFail(requestCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cstProgressBar.dismiss();
                csResponseListener.onFail(requestCode);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    if(selectedURI != null)
                        params.put("profile_pic", new DataPart("temp.png", getBytes(context,selectedURI), "image/*"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        CSApplicationHelper.application().addToRequestQueue(multipartRequest,"mutlti");
    }

    public static byte[] getBytes(Context context, Uri uri) throws IOException {
        InputStream iStream = context.getContentResolver().openInputStream(uri);
        try {
            return getBytesImage(iStream);
        } finally {
            // close the stream
            try {
                iStream.close();
            } catch (IOException ignored) { /* do nothing */ }
        }
    }

    public  static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    /**
     * get bytes from input stream.
     *
     * @param inputStream inputStream.
     * @return byte array read from the inputStream.
     * @throws IOException
     */
    public static byte[] getBytesImage(InputStream inputStream) throws IOException {

        Bitmap bmp = BitmapFactory.decodeStream(inputStream);

        bmp = getResizedBitmap(bmp,512);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
            stream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        byte[] bytesResult = null;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        try {
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            bytesResult = byteBuffer.toByteArray();
        } finally {
            // close the stream
            try{ byteBuffer.close(); } catch (IOException ignored){ *//* do nothing *//* }
        }*/
        return byteArray;
    }

    public static void audioUploadSaveSearch(Context context,
                                             String url,
                                             CSResponseListener csResponseListener,
                                             HashMap params,
                                             Uri uri,
                                             int requestCode,
                                             boolean isProgressShow) {

        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            return;
        }
        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                cstProgressBar.dismiss();
                String resultResponse = new String(response.data);
                try {
                    if(CSStringUtil.isNonEmptyStr(resultResponse)) {
                        JSONObject result = new JSONObject(resultResponse);
                        csResponseListener.onGetResponse(result, requestCode);
                        Log.i("Messsage", result.toString());
                    }
                    else {
                        csResponseListener.onFail(requestCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cstProgressBar.dismiss();
                csResponseListener.onFail(requestCode);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    if(/*CSStringUtil.isNonEmptyStr(audioPath)*/uri!=null){
                        InputStream iStream =   context.getContentResolver().openInputStream(uri);
                        byte[] inputData = getBytes(iStream);
                        params.put("audio", new DataPart("temp", inputData/*toByteArray(audioPath)*/, "audio/x-aac"));
                    }
//                        params.put("audio", new DataPart("temp", toByteArray(audioPath), "audio/x-aac"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        CSApplicationHelper.application().addToRequestQueue(multipartRequest,"mutlti");
    }


/*    public static void analysisVoice(Context context,
                                     CSResponseListener csResponseListener,
                                     String audioPath,
                                     int requestCode,
                                     boolean isProgressShow) {

        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            return;
        }
        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                ConstantURL.BaseAnalysUrl, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                cstProgressBar.dismiss();
                String resultResponse = new String(response.data);
                try {
                    if(CSStringUtil.isNonEmptyStr(resultResponse)) {
                        JSONObject result = new JSONObject(resultResponse);
                        csResponseListener.onGetResponse(result, requestCode);
                        Log.i("Messsage", result.toString());
                    }
                    else {
                        csResponseListener.onFail(requestCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cstProgressBar.dismiss();
                csResponseListener.onFail(requestCode);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();
                params.put("user_id",CSApplicationHelper.application().getCurrentLoggedInUser().getUserId());
                params.put("rquest","analyzeVoice");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    if(CSStringUtil.isNonEmptyStr(audioPath))
                        params.put("audio", new DataPart("temp", toByteArray(audioPath), "audio/x-aac"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        multipartRequest.setShouldCache(false);
        CSApplicationHelper.application().addToRequestQueue(multipartRequest,"mutlti");
    }*/


    public static void downloadAudio(Context context,
                                     String url,
                                     CSResponseListener csResponseListener,
                                     int requestCode,
                                     boolean isProgressShow) {


        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.GET,
                url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                cstProgressBar.dismiss();
                String resultResponse = new String(response.data);
                if(CSStringUtil.isNonEmptyStr(resultResponse)) {

                    try {
                        byte[] bytes = resultResponse.getBytes();
                        //below is the different part
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + new Date().getTime() +".mp3";
                        File someFile = new File(path);
                        FileOutputStream fos = new FileOutputStream(someFile);
                        fos.write(bytes);
                        fos.flush();
                        fos.close();

                        Log.d("TAG", "onResponse: " + someFile.getPath());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else {
                    csResponseListener.onFail(requestCode);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cstProgressBar.dismiss();
                csResponseListener.onFail(requestCode);
                error.printStackTrace();
            }
        });

        CSApplicationHelper.application().addToRequestQueue(multipartRequest,"mutlti");
    }


    private static byte[] toByteArray(String audioPath) throws IOException {

        File file = new File(audioPath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bos.toByteArray();
    }
}