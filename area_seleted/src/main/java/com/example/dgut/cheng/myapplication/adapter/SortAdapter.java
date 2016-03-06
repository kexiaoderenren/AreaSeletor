package com.example.dgut.cheng.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dgut.cheng.myapplication.R;
import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;
import com.example.dgut.cheng.myapplication.bean.SortModel;

import java.util.ArrayList;
import java.util.List;


/**
 * 省-市 适配器
 * Created by kuncheng on 2016/1/28.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer{

    private final static int RESULT_CODE = 99;
    private List<SortModel> lists;
    private Context context;
    private ArrayList<AreaDictionaryVo> provinceList;

    public SortAdapter(Context context, List<SortModel> lists, ArrayList<AreaDictionaryVo> provinceList) {
        this.context = context;
        this.lists = lists;
        this.provinceList = provinceList;
    }

    @Override
    public int getCount() {
        return this.lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        final SortModel mContent = lists.get(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            holder.tvProvince = (TextView) view.findViewById(R.id.content);
            holder.gridCity = (GridView) view.findViewById(R.id.gridview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(mContent.getSortLetters());
        } else {
            holder.tvLetter.setVisibility(View.GONE);
        }
        holder.tvProvince.setText(mContent.getProvince());

        final AreaDictionaryVo vo = provinceList.get(getProvinceInfo(mContent.getProvince()));  //该item省份

        holder.tvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putExtra("areaName", vo.getAreaName());
//                intent.putExtra("areaCode", vo.getAreaCode());
//                ((Activity)context).setResult(RESULT_CODE,intent);
//                ((Activity) context).finish();
                Toast.makeText(context, ((TextView)view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        List<String> cityNameLists = new ArrayList<String>();

        final List<AreaDictionaryVo> cityLists = vo.getChildList();
        if (cityLists != null && cityLists.size() > 0) {
            for (int j = 0; j < cityLists.size(); j++) {
                cityNameLists.add(cityLists.get(j).getAreaName());
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.grid_item, cityNameLists);
        holder.gridCity.setAdapter(adapter);
        holder.gridCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent();
//                intent.putExtra("areaName", cityLists.get(i).getAreaName());
//                intent.putExtra("areaCode", cityLists.get(i).getAreaCode());
//                intent.putExtra("pName", cityLists.get(i).getPName());
//                intent.putExtra("pCode", cityLists.get(i).getPCode());
//                ((Activity) context).setResult(RESULT_CODE, intent);
//                ((Activity) context).finish();
                Toast.makeText(context, ((TextView)view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * 通过省份名称获取该省份在provincelist的下标位置
     * @param provinceName 省份名称
     * @return
     */
    private int getProvinceInfo(String provinceName) {
        for (int i=0; i<provinceList.size(); i++) {
            if (provinceName.trim().equals(provinceList.get(i).getAreaName().trim())) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i=0; i<getCount(); i++) {
            String sortStr = lists.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return lists.get(position).getSortLetters().charAt(0);
    }

    class ViewHolder{
        TextView tvLetter;
        TextView tvProvince;
        GridView gridCity;
    }
}
