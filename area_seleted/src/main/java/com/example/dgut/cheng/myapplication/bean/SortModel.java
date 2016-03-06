package com.example.dgut.cheng.myapplication.bean;

/**
 * 省份首字母实体类
 */
public class SortModel {

	private String province;   //显示的数据（省份）
	private String sortLetters;  //显示数据拼音的首字母（省份首字母）

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
