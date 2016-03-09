package com.example.dgut.cheng.myapplication.utils;

import com.example.dgut.cheng.myapplication.bean.SortModel;

import java.util.Comparator;


/**
 * 拼音比较类
 */
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@") || o1.getSortLetters().equals("#")) {
			return -1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
