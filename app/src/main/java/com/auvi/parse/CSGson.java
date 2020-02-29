package com.auvi.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import com.auvi.application.CSApplicationHelper;
import com.auvi.view.CSProgressBar;
import com.auvi.util.CSUtil;

import java.util.ArrayList;
import java.util.Map;

public class CSGson {

    public static String TAG = "CSGson";
    static CSGson instance;
    Gson gson;
    Gson gsonExcAnnotation;

    private CSGson() {
        this.init();
    }

    public static CSGson gson() {
        if (instance == null) {
            instance = new CSGson();
        }
        return instance;
    }

    private void init() {
        this.gson = new GsonBuilder().create();
    }

    public String toJson(Object obj) {
        return this.gson.toJson(obj);
    }

    public <T> T getObject(Class<T> entity, JSONObject response) {
        if (response == null) {
            return null;
        }
        try {
            String jsonString = response.toString();
            return CSUtil.isNonEmptyStr(jsonString) ? this.gson.fromJson(jsonString, entity) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T getObject(Class<T> entity, Object response) {
        if (response == null) {
            return null;
        }
        try {
            String jsonString = response instanceof Map ? this.toJson(response) : CSJSonHelper.toJSON(response).toString();
            return CSUtil.isNonEmptyStr(jsonString) ? this.gson.fromJson(jsonString, entity) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public <T> ArrayList<T> getList(Class<T> entity, Object response) {
        if (response == null) {
            return new ArrayList<>();
        }
        CSProgressBar csProgressBar = new CSProgressBar(CSApplicationHelper.application().getActivity());
        csProgressBar.show();
        ArrayList<T> returnList = new ArrayList<>();
        try {
            String jsonsString = response instanceof Iterable ? this.toJson(response) : CSJSonHelper.toJSON(response).toString();
            ArrayList<Object> list = this.gson.fromJson(jsonsString, new TypeToken<ArrayList<T>>() {
            }.getType());
            for (Object obj : list) {
                returnList.add(this.getObject(entity, obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(csProgressBar.isShowing()){
                csProgressBar.dismiss();
            }
        }
        return returnList;
    }

    public <T> ArrayList<T> getList(Class<T> entity, JSONArray response) {
        if (response == null) {
            return new ArrayList<>();
        }
        CSProgressBar csProgressBar = new CSProgressBar(CSApplicationHelper.application().getActivity());
        csProgressBar.show();
        ArrayList<T> returnList = new ArrayList<>();
        try {
            for (int i=0;i<response.length();i++) {
                returnList.add(this.getObject(entity, response.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(csProgressBar.isShowing()){
                csProgressBar.dismiss();
            }
        }
        return returnList;
    }
}
