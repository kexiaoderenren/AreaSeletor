package com.example.dgut.cheng.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dgut.cheng.myapplication.R;
import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;
import com.example.dgut.cheng.myapplication.view.lib.ScreenInfo;
import com.example.dgut.cheng.myapplication.view.lib.WheelAreaDict;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * 省市区选择器
 *      仿IOS效果地区选择器
 * Created by cheng on 2016/3/9.
 */
public class AreaDialog implements View.OnClickListener {

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private DialogPlus dialogPlus;
    private LayoutInflater mInflater;
    private View viewHolder;
    private OnAreaSelectListener areaSelectListener;
    private View btnSubmit, btnCancel;
    private WheelAreaDict wheelTime;
    private Context context;


    public AreaDialog(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        viewHolder = mInflater.inflate(R.layout.pw_area, null);

        // ----时间转轮
        final View timepickerview = viewHolder.findViewById(R.id.timepicker);
        ScreenInfo screenInfo = new ScreenInfo((Activity) context);
        wheelTime = new WheelAreaDict(context, timepickerview);

        wheelTime.screenheight = screenInfo.getHeight();

        //默认选中当前城市
//		wheelTime.setPicker(0, 0, 0);
        dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(viewHolder))
                .setGravity(Gravity.BOTTOM)
                .create();

        btnSubmit = dialogPlus.getHolderView().findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = dialogPlus.getHolderView().findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }


    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    public void show() {
        wheelTime.setPicker("0", "0", "0");
        dialogPlus.show();
    }

    public void dismiss(){
        dialogPlus.dismiss();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (areaSelectListener != null && wheelTime.getProvince() != null && wheelTime.getCity() != null && wheelTime.getArea() != null) {
                areaSelectListener.onAreaSelect(wheelTime.getProvince(), wheelTime.getCity(), wheelTime.getArea());
            }
            dismiss();
            return;
        }
    }

    public void setOnAreaSelectListener(OnAreaSelectListener areaSelectListener) {
        this.areaSelectListener = areaSelectListener;
    }

    public interface OnAreaSelectListener {
        public void onAreaSelect(AreaDictionaryVo province, AreaDictionaryVo city, AreaDictionaryVo area);
    }

}
