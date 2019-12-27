package com.haha.baidumap;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.lxj.easyadapter.CommonAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;

import java.util.List;

/**
 * Created by Zs on 2019/4/3.
 */

public class BottomComment extends BottomPopupView {
    private List<PoiInfo> list;
    private VerticalRecyclerView recyclerView;
    public CommonAdapter<PoiInfo> commonAdapter;
    public BottomComment(@NonNull Context context,List<PoiInfo> list) {
        super(context);
        this.list = list;
    }


    /**
     * 具体实现的类的布局
     *
     * @return
     */
    @Override
    protected int getImplLayoutId() {
        return R.layout.bottomcomment;
    }

    /**
     * do init.
     */
    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.recyclerView);
        commonAdapter = new CommonAdapter<PoiInfo>(R.layout.item_bottomcomment,list) {
            @Override
            protected void bind(@NonNull ViewHolder holder, @NonNull final PoiInfo poiDetailInfo, final int position) {
                holder.setText(R.id.name,poiDetailInfo.getName());
                holder.setText(R.id.address,String.valueOf(poiDetailInfo.getAddress()));
                holder.setOnClickListener(R.id.line, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),PoiDetailsActivity.class);
                        intent.putExtra("name",poiDetailInfo.getName());
                        intent.putExtra("latitude",poiDetailInfo.location.latitude);
                        intent.putExtra("longitude",poiDetailInfo.location.longitude);
                        Log.d("hahah",poiDetailInfo.getPoiDetailInfo().getDetailUrl());
                        intent.putExtra("url",poiDetailInfo.getPoiDetailInfo().getDetailUrl());
                        getContext().startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext())*.50f);

    }
}
