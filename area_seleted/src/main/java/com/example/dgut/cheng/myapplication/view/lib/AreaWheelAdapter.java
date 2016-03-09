package com.example.dgut.cheng.myapplication.view.lib;

import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;

import java.util.List;
;

/**
 * @author kaiping
 * @date 2016/1/27 16:53
 */
public class AreaWheelAdapter implements WheelAdapter {
    private List<AreaDictionaryVo> list;

    public AreaWheelAdapter(List<AreaDictionaryVo> list){
        this.list = list;
    }

    @Override
    public int getItemsCount() {
        if (null == list) return 0;
        return list.size();
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            String name = list.get(index).getAreaName();
            if (name.length() > 5) return name.substring(0, 5);
            return name;
        }
        return null;
    }

    public AreaDictionaryVo getItemVo(int index){
        return list.get(index);
    }

    @Override
    public int getMaximumLength() {
        return 0;
    }
}
