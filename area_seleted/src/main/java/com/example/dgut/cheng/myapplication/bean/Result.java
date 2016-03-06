package com.example.dgut.cheng.myapplication.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng on 2016/3/4.
 */
public class Result implements Serializable{

    private int totalPage = 0;              //默认0页面
    private int responseCode;           //初始化随便赋值
    private boolean isSuccess;          //默认false
    private String desc;                //初始化为空
    private Object data;                //返回数据源
    private String responseJson;        //返回的json总数据
    private Map<String,Object> other = new HashMap<String,Object>();   //其他数据

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    public void setOther(Map<String, Object> other) {
        this.other = other;
    }

    public <T> T getDataFormat() {
        return (T)data;
    }
}
