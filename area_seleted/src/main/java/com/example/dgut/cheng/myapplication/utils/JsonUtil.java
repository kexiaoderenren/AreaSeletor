package com.example.dgut.cheng.myapplication.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2016/3/4.
 */
public class JsonUtil {

    public JsonUtil() {}

    public static <T> T json2Bean(JSONObject jObject, Class<T> cls) throws Exception {
        Object t = cls.newInstance();
        Field[] fields = cls.getDeclaredFields();
        Field[] var7 = fields;
        int var6 = fields.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field field = var7[var5];
            Class typeClass = field.getType();
            String fieldName = field.getName();
            field.setAccessible(true);
            if(!jObject.isNull(fieldName)) {
                if(typeClass == String.class) {
                    String var17 = jObject.optString(fieldName);
                    field.set(t, var17);
                } else if(typeClass != Integer.TYPE && typeClass != Integer.class) {
                    if(typeClass != Long.TYPE && typeClass != Long.class) {
                        if(typeClass != Float.TYPE && typeClass != Float.class) {
                            if(typeClass != Double.TYPE && typeClass != Double.class) {
                                Object var15;
                                if(typeClass != Date.class && typeClass != java.sql.Date.class) {
                                    if(typeClass != Byte.TYPE && typeClass != Byte.class) {
                                        if(typeClass != List.class) {
                                            var15 = jObject.opt(fieldName);
                                            field.set(t, var15);
                                        }
                                    } else {
                                        byte var16 = (byte)jObject.optInt(fieldName);
                                        field.set(t, Byte.valueOf(var16));
                                    }
                                } else {
                                    var15 = jObject.opt(fieldName);
                                    field.set(t, formatDate(var15));
                                }
                            } else {
                                double var14 = jObject.optDouble(fieldName);
                                Log.e("fieldName", fieldName);
                                field.setDouble(t, var14);
                            }
                        } else {
                            Float var13 = (Float)jObject.opt(fieldName);
                            field.setFloat(t, var13.floatValue());
                        }
                    } else {
                        long var12 = jObject.optLong(fieldName);
                        field.setLong(t, var12);
                    }
                } else {
                    int value = jObject.optInt(fieldName);
                    field.setInt(t, value);
                }
            }
        }

        return (T) t;
    }

    public static <T> T json2Bean(String json, Class<T> cls) throws Exception {
        return json2Bean(new JSONObject(json), cls);
    }

    public static <T> List<T> json2Beans(String jsons, Class<T> cls) throws Exception {
        return jsonArray2Beans(new JSONArray(jsons), cls);
    }

    public static <T> Map<String, Object> bean2Map(T t) throws Exception {
        HashMap map = new HashMap();
        Field[] fields = t.getClass().getDeclaredFields();
        Field[] var6 = fields;
        int var5 = fields.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            Field field = var6[var4];
            Class typeClass = field.getType();
            String fieldName = field.getName();
            field.setAccessible(true);
            Object value = field.get(t);
            if(value != null) {
                if(typeClass != Date.class && typeClass != java.sql.Date.class) {
                    if(typeClass != List.class) {
                        map.put(fieldName, value);
                    }
                } else {
                    map.put(fieldName, formatDate((Date)value));
                }
            }
        }

        return map;
    }

    public static <T> List<T> jsonArray2Beans(JSONArray jArray, Class<T> cls) throws Exception {
        int arrayLen = jArray.length();
        ArrayList jsonList = new ArrayList(arrayLen);

        for(int i = 0; i < arrayLen; ++i) {
            JSONObject jObject = jArray.getJSONObject(i);
            Object bean = json2Bean(jObject, cls);
            jsonList.add(bean);
        }

        return jsonList;
    }

    public static <T> List<T> jsonArray2Beans(String json, Class<T> cls) throws Exception {
        return jsonArray2Beans(new JSONArray(json), cls);
    }

    public static <T> List<Map<String, Object>> jsonArray2List(String json) {
        try {
            return jsonArray2List(new JSONArray(json));
        } catch (Exception var2) {
            var2.printStackTrace();
            return new ArrayList();
        }
    }

    public static List<Map<String, Object>> jsonArray2List(JSONArray jArray) {
        int arrayLen = jArray.length();
        ArrayList mapList = new ArrayList(arrayLen);

        for(int i = 0; i < arrayLen; ++i) {
            JSONObject jObject = jArray.optJSONObject(i);
            new HashMap();
            Map map = json2Map(jObject);
            mapList.add(map);
        }

        return mapList;
    }

    public static <T> List<Map<String, Object>> beanMap2List(List<T> list) throws Exception {
        int len = list.size();
        ArrayList mapList = new ArrayList(len);
        Map map = null;

        for(int i = 0; i < len; ++i) {
            map = bean2Map(list.get(i));
            mapList.add(map);
        }

        return mapList;
    }

    public static Map<String, Object> json2Map(JSONObject jObject) {
        if(jObject == null) {
            return new HashMap();
        } else {
            HashMap map = new HashMap();

            String field;
            Object value;
            for(Iterator it = jObject.keys(); it.hasNext(); map.put(field, value)) {
                field = (String)it.next();
                value = jObject.opt(field);
                if(jObject.isNull(field)) {
                    value = "";
                }
            }

            return map;
        }
    }

    public static Map<String, Object> json2Map(String json) {
        try {
            return json2Map(new JSONObject(json));
        } catch (JSONException var2) {
            var2.printStackTrace();
            return new HashMap();
        }
    }

    public static String mapString(Map<String, Object> map) {
        return (new JSONObject(map)).toString();
    }

    private static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private static Date formatDate(Object obj) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date dDate = new Date();
        String sDate = obj.toString();
        int len = sDate.length();
        switch(len) {
            case 10:
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                break;
            case 16:
                simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm");
                break;
            case 19:
                simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                break;
            case 21:
                simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                break;
            default:
                return null;
        }

        try {
            dDate = simpleDateFormat.parse(sDate);
        } catch (ParseException var6) {
            var6.printStackTrace();
        }

        return dDate;
    }
}
