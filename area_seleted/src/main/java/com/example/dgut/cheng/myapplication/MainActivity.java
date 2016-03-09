package com.example.dgut.cheng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dgut.cheng.myapplication.bean.AreaDictionaryVo;
import com.example.dgut.cheng.myapplication.view.AreaDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AreaDialog pwArea;  //省市区
    private Button btnCity,btnArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnCity = (Button) findViewById(R.id.btn_city);
        btnArea = (Button) findViewById(R.id.btn_area);
        pwArea = new AreaDialog(this);
        pwArea.setOnAreaSelectListener(new AreaDialog.OnAreaSelectListener() {
            @Override
            public void onAreaSelect(AreaDictionaryVo province, AreaDictionaryVo city, AreaDictionaryVo area) {

                Toast.makeText(MainActivity.this,
                        "province:" + province.getAreaName() + "---city:" + city.getAreaName() + "---area:" + area.getAreaName(),
                Toast.LENGTH_SHORT)
                        .show();
            }
        });
        btnArea.setOnClickListener(this);
        btnCity.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_city:
                Intent intent = new Intent(this, CitySeletorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_area:
                pwArea.show();
                break;
            default:
                break;
        }
    }
}
