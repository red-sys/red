package com.haha.baidumap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.mapapi.model.LatLng;

public class PanoramaActivity extends BaseActivity {
private ImageView back;
private PanoramaView panoramaView;
private Double longitude,latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication app =(BaseApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new BaseApplication.MyGeneralListener());
        }
        setContentView(R.layout.activity_panorama);
        longitude = getIntent().getDoubleExtra("longitude",0);
        latitude = getIntent().getDoubleExtra("latitude",0);
        back = findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        panoramaView = findViewById(R.id.panorama);
        panoramaView.setPanorama(longitude,latitude);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_panorama;
    }
}
