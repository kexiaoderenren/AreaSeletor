package com.example.dgut.cheng.myapplication.view.lib;

import android.content.Context;
import android.view.View;


import com.example.dgut.cheng.myapplication.R;
import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;
import com.example.dgut.cheng.myapplication.utils.AssetsReaderUtils;
import com.example.dgut.cheng.myapplication.utils.Passer;
import com.example.dgut.cheng.myapplication.view.TimePopupWindow;

import java.util.ArrayList;
import java.util.List;



public class WheelAreaDict {
	private View view;
	private WheelView wv_province;
	private WheelView wv_city;
	private WheelView wv_area;
	public int screenheight;
	private TimePopupWindow.Type type;
	private ArrayList<AreaDictionaryVo> provinceList;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public WheelAreaDict(Context context,View view) {
		super();
		this.view = view;
		type = TimePopupWindow.Type.ALL;
		setView(view);
		provinceList = Passer.paserAllAreaAndHotCity(AssetsReaderUtils.readFromAsset(context, "area_select.txt"));
	}
	
	/**
	 * @Description: TODO 弹出城市选择器
	 */
	public void setPicker(String province ,String city ,String area) {
		Context context = view.getContext();
		int provinceItem = 0;
		int cityItem = 0;
		int areaItem = 0;
		for (int i=0; i<provinceList.size(); i++){
			AreaDictionaryVo vo = provinceList.get(i);
			if (province.equals(vo.getAreaCode())){
				provinceItem = i;
				if (null != vo.getChildList()){
					for (int j=0; j<vo.getChildList().size(); j++){
						AreaDictionaryVo cityVo = vo.getChildList().get(j);
						if (city.equals(cityVo.getAreaCode())){
							cityItem = j;
							for (int k=0; k<cityVo.getChildList().size(); k++){
								AreaDictionaryVo areaVo = cityVo.getChildList().get(k);
								if (area.equals(areaVo.getAreaCode())){
									areaItem = k;
								}
							}
						}
					}
				}
			}
		}

		// 省
		wv_province = (WheelView) view.findViewById(R.id.year);
		wv_province.setAdapter(new AreaWheelAdapter(provinceList));// 设置"省"的显示数据
		wv_province.setCurrentItem(provinceItem);// 初始化时显示的数据

		// 市
		wv_city = (WheelView) view.findViewById(R.id.month);
		wv_city.setAdapter(new AreaWheelAdapter(provinceList.get(0).getChildList()));
		wv_city.setCurrentItem(cityItem);

		// 区
		wv_area = (WheelView) view.findViewById(R.id.day);
		wv_area.setAdapter(new AreaWheelAdapter(provinceList.get(0).getChildList().get(0).getChildList()));
		wv_area.setCurrentItem(areaItem);
		
		// 添加"省"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue;
				List<AreaDictionaryVo> cityList = provinceList.get(newValue).getChildList();
				wv_city.setAdapter(new AreaWheelAdapter(cityList));
				if (null == cityList){
					wv_area.setAdapter(new AreaWheelAdapter(null));
				} else {
					wv_area.setAdapter(new AreaWheelAdapter(cityList.get(0).getChildList()));
				}
				wv_city.setCurrentItem(0);
				wv_area.setCurrentItem(0);
			}
		};
		// 添加"市"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue;
				wv_area.setAdapter(new AreaWheelAdapter(provinceList.get(wv_province.getCurrentItem()).getChildList().get(month_num).getChildList()));
				wv_area.setCurrentItem(0);
			}
		};
		wv_province.addChangingListener(wheelListener_year);
		wv_city.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		switch(type){
		case ALL:
			textSize = (screenheight / 100) * 3;
			break;
		}
			
		wv_area.TEXT_SIZE = textSize;
		wv_city.TEXT_SIZE = textSize;
		wv_province.TEXT_SIZE = textSize;
	}

	/**
	 * 设置是否循环滚动
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic){
		wv_province.setCyclic(cyclic);
		wv_city.setCyclic(cyclic);
		wv_area.setCyclic(cyclic);
	}
	public AreaDictionaryVo getProvince() {
		return ((AreaWheelAdapter)wv_province.getAdapter()).getItemVo(wv_province.getCurrentItem());
	}

	public AreaDictionaryVo getCity() {
		return ((AreaWheelAdapter)wv_city.getAdapter()).getItemVo(wv_city.getCurrentItem());
	}

	public AreaDictionaryVo getArea() {
		return ((AreaWheelAdapter)wv_area.getAdapter()).getItemVo(wv_area.getCurrentItem());
	}
}
