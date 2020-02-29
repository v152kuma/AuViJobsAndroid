package com.auvi.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.auvi.R;
import com.auvi.view.CSProgressBar;
import com.auvi.application.CSApplicationHelper;
import com.auvi.listener.CSResponseListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CSRequestUtil {

    private static CSRequestUtil requestUtilInstance;
    private final String TAG = "CSRequestUtil";

    public static CSRequestUtil getInstance(){
        if(requestUtilInstance == null){
            requestUtilInstance = new CSRequestUtil();
        }
        return requestUtilInstance;
    }

    private CSRequestUtil(){

    }

    public void doGetJsonRequest(final Context context,
                                 String url,
                                 final CSResponseListener responseListener,
                                 final int requestCode,
                                 final boolean isProgressShow){

        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            responseListener.onFail(requestCode);
            return;
        }

        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }
        CSTJSONObjectResponse cstjsonObjectResponse = new CSTJSONObjectResponse(cstProgressBar,
                responseListener,
                requestCode,
                isProgressShow);
        CSTErrorResponse cstErrorResponse = new CSTErrorResponse(cstProgressBar,
                isProgressShow);

        final JsonObjectRequest req = new JsonObjectRequest(url,
                null,
                cstjsonObjectResponse,
                cstErrorResponse);

        CSApplicationHelper.application().addToRequestQueue(req,url);
    }

    public void doGetStringRequest(final Context context,
                                   String url,
                                   final CSResponseListener responseListener,
                                   final int requestCode,
                                   final boolean isProgressShow){
        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            responseListener.onFail(requestCode);
            return;
        }

        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        CSTStringResponse cstStringResponse = new CSTStringResponse(cstProgressBar,
                responseListener,
                requestCode,
                isProgressShow);

        CSTErrorResponse cstErrorResponse = new CSTErrorResponse(cstProgressBar,
                isProgressShow);

        final StringRequest req = new StringRequest(url,cstStringResponse,cstErrorResponse);

        CSApplicationHelper.application().addToRequestQueue(req,url);
    }

    public void doPostGoogleJSONRequest(final Context context,
                                        final String url,
                                        final JSONObject params,
                                        final CSResponseListener responseListener,
                                        final int requestCode,
                                        final boolean isProgressShow){
        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            return;
        }
        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        CSTJSONGoogleObjectResponse cstjsonObjectResponse = new CSTJSONGoogleObjectResponse(cstProgressBar,
                responseListener,
                requestCode,
                isProgressShow);

        CSTErrorResponse cstErrorResponse = new CSTErrorResponse(cstProgressBar,
                isProgressShow);

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,url,
                params,
                cstjsonObjectResponse,
                cstErrorResponse) {
            // Notice no semi-colon here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        CSApplicationHelper.application().addToRequestQueue(req,url);
    }


    public void doPostJSONRequest(final Context context,
                                  final String url,
                                  final JSONObject params,
                                  final CSResponseListener responseListener,
                                  final int requestCode,
                                  final boolean isProgressShow){
        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            return;
        }
        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }

        CSTJSONObjectResponse cstjsonObjectResponse = new CSTJSONObjectResponse(cstProgressBar,
                responseListener,
                requestCode,
                isProgressShow);
        CSTErrorResponse cstErrorResponse = new CSTErrorResponse(cstProgressBar,
                isProgressShow);

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,url,
                params,
                cstjsonObjectResponse,
                cstErrorResponse) {
            // Notice no semi-colon here
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        CSApplicationHelper.application().addToRequestQueue(req,url);
    }



    public void doPostStringRequest(final Context context,
                                    final String url,
                                    final HashMap params,
                                    final CSResponseListener responseListener,
                                    final int requestCode,
                                    final boolean isProgressShow){
        if(CSAppUtil.isNetworkNotAvailable(context,true)){
            return;
        }
        final CSProgressBar cstProgressBar = new CSProgressBar(context);
        if(isProgressShow){
            if(!cstProgressBar.isShowing()){
                cstProgressBar.show();
            }
        }
        CSTStringResponse cstStringResponse = new CSTStringResponse(cstProgressBar,
                responseListener,
                requestCode,
                isProgressShow);

        CSTErrorResponse cstErrorResponse = new CSTErrorResponse(cstProgressBar,
                isProgressShow);

        final StringRequest req = new StringRequest(Request.Method.POST,url,cstStringResponse,cstErrorResponse)
        {
            @Override
            public Map<String, String> getParams () {
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        CSApplicationHelper.application().addToRequestQueue(req,url);
    }

    public class CSTJSONGoogleObjectResponse extends CSTJSONObjectResponse{

        public CSTJSONGoogleObjectResponse(CSProgressBar cstProgressBar, CSResponseListener cstResponseListener, int requestCode, boolean isProgressShow) {
            super(cstProgressBar, cstResponseListener, requestCode, isProgressShow);
        }

        @Override
        public void onResponse(JSONObject response) {
            if(!isProgressShow )
                cstProgressBar.dismiss();
            try {
                if(response!=null){
                    Log.d(TAG, "doGetJsonRequest: "+response);
                    cstResponseListener.onGetResponse(response,requestCode);
                }
                else {
                    CSAppUtil.showToast(R.string.error_unable_to_connect_server);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            finally {
                if(!isProgressShow )
                    cstProgressBar.dismiss();
            }
        }
    }

    public class CSTJSONObjectResponse implements  Response.Listener<JSONObject>{

        CSProgressBar cstProgressBar;
        CSResponseListener cstResponseListener;
        int requestCode;
        boolean isProgressShow;

        public CSTJSONObjectResponse(CSProgressBar cstProgressBar,CSResponseListener cstResponseListener,int requestCode,boolean isProgressShow){
            this.cstProgressBar = cstProgressBar;
            this.cstResponseListener = cstResponseListener;
            this.requestCode = requestCode;
            this.cstProgressBar = cstProgressBar;
        }

        @Override
        public void onResponse(JSONObject response) {
            if(!isProgressShow )
                cstProgressBar.dismiss();
            try {
                if(response!=null){
                    Log.d(TAG, "doGetJsonRequest: "+response);
                    if(response.has("errCode") && response.getString("errCode").equalsIgnoreCase("-1"))
                        cstResponseListener.onGetResponse(response,requestCode);
                    else {
                        if (response.has("message"))
                            CSAppUtil.showToast(response.getString("message"));
                        cstResponseListener.onFail(requestCode);
                    }
                }
                else {
                    CSAppUtil.showToast(R.string.error_unable_to_connect_server);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            finally {
                if(!isProgressShow )
                    cstProgressBar.dismiss();
            }
        }
    }

    public class CSTStringResponse implements  Response.Listener<String>{

        CSProgressBar cstProgressBar;
        CSResponseListener cstResponseListener;
        int requestCode;
        boolean isProgressShow;

        public CSTStringResponse(CSProgressBar cstProgressBar,
                                 CSResponseListener cstResponseListener,
                                 int requestCode,
                                 boolean isProgressShow){
            this.cstProgressBar = cstProgressBar;
            this.cstResponseListener = cstResponseListener;
            this.requestCode = requestCode;
            this.isProgressShow = isProgressShow;
        }

        @Override
        public void onResponse(String response) {
            try {
                if(CSStringUtil.isNonEmptyStr(response)){
                    JSONObject dataResponse = new JSONObject(response);
                    Log.d(TAG, "onResponse: "+dataResponse);
                    if (dataResponse.has("errCode") && dataResponse.getString("errCode").equalsIgnoreCase("-1"))
                        cstResponseListener.onGetResponse(dataResponse, requestCode);
                    else {
                        if (dataResponse.has("message"))
                            CSAppUtil.showToast(dataResponse.getString("message"));
                        else {
                            cstResponseListener.onFail(requestCode);
                        }

                    }
                }
                else {
                    CSAppUtil.showToast(R.string.error_unable_to_connect_server);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            finally {
                if(isProgressShow )
                    cstProgressBar.dismiss();
            }
        }

    }

    public class CSTErrorResponse implements  Response.ErrorListener {

        CSProgressBar cstProgressBar;
        boolean isProgressShow;

        public CSTErrorResponse(CSProgressBar cstProgressBar,boolean isProgressShow){
            this.cstProgressBar = cstProgressBar;
            this.isProgressShow = isProgressShow;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            if(isProgressShow)
                cstProgressBar.dismiss();
            CSAppUtil.showToast(R.string.error_unable_to_connect_server);
        }

    }
}