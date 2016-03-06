package com.example.dgut.cheng.myapplication.utils;

import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2016/3/4.
 */
public class Passer {

    /**
     * 解析 省市区域列表 为树结构,
     * @param result
     */
    public static ArrayList<AreaDictionaryVo> paserAllAreaAndHotCity(String result) {

        try {
            JSONObject dataJson = new JSONObject(result).getJSONObject("data");
            JSONObject areaJson = dataJson.getJSONObject("area");

            //解析所有省市区
            List<AreaDictionaryVo> areaList = JsonUtil.jsonArray2Beans(areaJson.getJSONArray("allArea"), AreaDictionaryVo.class);
            ArrayList<AreaDictionaryVo> provinceList = new ArrayList<>();
            Map<String, List<AreaDictionaryVo>> cityMap = new HashMap<>();//城市
            Map<String, List<AreaDictionaryVo>> areaMap = new HashMap<>();//区域
            for (AreaDictionaryVo vo : areaList) {
                if ("0".equals(vo.getAreaLevel())) {//省份
                    provinceList.add(vo);
                } else if ("1".equals(vo.getAreaLevel())) {//城市
                    if (null == cityMap.get(vo.getPCode())) {
                        List<AreaDictionaryVo> list = new ArrayList<>();
                        list.add(vo);
                        cityMap.put(vo.getPCode(), list);
                    } else {
                        cityMap.get(vo.getPCode()).add(vo);
                    }
                } else {//区域
                    if (null == areaMap.get(vo.getPCode())) {
                        List<AreaDictionaryVo> list = new ArrayList<>();
                        list.add(vo);
                        areaMap.put(vo.getPCode(), list);
                    } else {
                        areaMap.get(vo.getPCode()).add(vo);
                    }
                }
            }
            for (AreaDictionaryVo vo : provinceList) {
                List<AreaDictionaryVo> list = cityMap.get(vo.getAreaCode());
                if (null != list) {
                    for (AreaDictionaryVo co : list) {
                        co.setChildList(areaMap.get(co.getAreaCode()));
                    }
                }
                vo.setChildList(list);
            }
            return provinceList;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
