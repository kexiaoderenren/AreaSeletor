package com.example.dgut.cheng.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dgut.cheng.myapplication.adapter.SortAdapter;
import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;
import com.example.dgut.cheng.myapplication.bean.SortModel;
import com.example.dgut.cheng.myapplication.utils.AssetsReaderUtils;
import com.example.dgut.cheng.myapplication.utils.CharacterParser;
import com.example.dgut.cheng.myapplication.utils.Passer;
import com.example.dgut.cheng.myapplication.utils.PinyinComparator;
import com.example.dgut.cheng.myapplication.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 省——市选择器
 *      模仿高德地图切换城市效果
 * Created by cheng on 2016/3/9.
 */
public class CitySeletorActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String AREA = "area_select.txt";

    private ListView listview;
    private TextView tvDialog;
    private SideBar sideBar;     //字母导航条

    private TextView tvLocateCity;   //定位城市
    private TextView tvAll;   //全国
    private SortAdapter adapter;

    private CharacterParser characterParser;
    private List<SortModel> sourceDateList;
    private PinyinComparator pinyinComparator;
    private ArrayList<AreaDictionaryVo> provinceList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        showProgressBar();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }, 100);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                dismissProgressBar();
            }
        }
    };

    private void showProgressBar() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = ProgressDialog.show(this,"", "请稍候..");
        }
    }

    private void dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void init() {
        provinceList = Passer.paserAllAreaAndHotCity(AssetsReaderUtils.readFromAsset(this, AREA));
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(tvDialog);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1) {
                    listview.setSelection(position);
                }
            }
        });

        sourceDateList = filledData();
        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, sourceDateList, provinceList);

        View locationCity = LayoutInflater.from(this).inflate(R.layout.list_header_location_city, null);
        tvLocateCity = (TextView) locationCity.findViewById(R.id.tv_header_location_city);
        tvLocateCity.setText("广州");
        tvLocateCity.setOnClickListener(this);

        View hotCity = LayoutInflater.from(this).inflate(R.layout.list_header_hot_city, null);
        GridView gridView = (GridView) hotCity.findViewById(R.id.gridview_header);
        gridView.setAdapter(new ArrayAdapter(this, R.layout.grid_item,
                getResources().getStringArray(R.array.hot_city_list)));
        tvAll = (TextView) hotCity.findViewById(R.id.tv_all);
        tvAll.setOnClickListener(this);

        listview.addHeaderView(locationCity);
        listview.addHeaderView(hotCity);
        listview.setAdapter(adapter);
    }


    /**
     * 根据省份名称转为拼音首字母，并填充数据
     * @return
     */
    private List<SortModel> filledData(){
        List<String> provinceNameLists = new ArrayList<String>();
        for (int i = 0; i < provinceList.size(); i++) {  //获取各个省份名称
            provinceNameLists.add(provinceList.get(i).getAreaName());
        }
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for(int i=0; i < provinceNameLists.size(); i++){
            SortModel sortModel = new SortModel();
            sortModel.setProvince(provinceNameLists.get(i));
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(provinceNameLists.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]"))
                sortModel.setSortLetters(sortString.toUpperCase());
            else
                sortModel.setSortLetters("#");
            mSortList.add(sortModel);
        }
        return mSortList;
    }


    private void initViews() {
        listview = (ListView) findViewById(R.id.country_lvcountry);
        tvDialog = (TextView) findViewById(R.id.dialog);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_header_location_city:
                Toast.makeText(CitySeletorActivity.this, ((TextView)v).getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_all:
                Toast.makeText(CitySeletorActivity.this, ((TextView)v).getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
