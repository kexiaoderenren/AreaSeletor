package com.example.dgut.cheng.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2016/1/26 19:59
 */
public class AreaDictionaryVo implements Serializable {
    private String id ;
    private String gbtCode ;
    private String PName ;
    private String areaCode ;
    private String areaName ;
    private String PCode ;
    private String GCode ;
    private String areaLevel ;
    private String state ;

    private List<AreaDictionaryVo> childList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGbtCode() {
        return gbtCode;
    }

    public void setGbtCode(String gbtCode) {
        this.gbtCode = gbtCode;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPCode() {
        return PCode;
    }

    public void setPCode(String PCode) {
        this.PCode = PCode;
    }

    public String getGCode() {
        return GCode;
    }

    public void setGCode(String GCode) {
        this.GCode = GCode;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<AreaDictionaryVo> getChildList() {
        return childList;
    }

    public void setChildList(List<AreaDictionaryVo> childList) {
        this.childList = childList;
    }
}
