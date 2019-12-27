package com.haha.baidumap;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBRouteGuidanceListener;
import com.baidu.mapapi.bikenavi.adapter.IBTTSPlayer;
import com.baidu.mapapi.bikenavi.model.BikeRouteDetailInfo;
import com.baidu.mapapi.walknavi.model.RouteGuideKind;

public class NavigationActivity extends AppCompatActivity {
private BikeNavigateHelper bikeNavigateHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bikeNavigateHelper = BikeNavigateHelper.getInstance();
        View view =bikeNavigateHelper.onCreate(NavigationActivity.this);
        if (view!=null){
            setContentView(view);
        }
        bikeNavigateHelper.setTTsPlayer(new IBTTSPlayer() {
            @Override
            public int playTTSText(String s, boolean b) {
                Log.d("语音播报：",s);
                return 0;
            }
        });
        bikeNavigateHelper.startBikeNavi(NavigationActivity.this);
        bikeNavigateHelper.setRouteGuidanceListener(NavigationActivity.this,guidanceListener);

    }
    @Override
    protected void onResume() {
        super.onResume();
        bikeNavigateHelper.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bikeNavigateHelper.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bikeNavigateHelper.quit();
    }
    private IBRouteGuidanceListener guidanceListener = new IBRouteGuidanceListener() {
        @Override
        public void onRouteGuideIconUpdate(Drawable drawable) {
            //诱导图标更新
        }

        @Override
        public void onRouteGuideKind(RouteGuideKind routeGuideKind) {
            //诱导类型枚举
        }

        @Override
        public void onRoadGuideTextUpdate(CharSequence charSequence, CharSequence charSequence1) {
            Log.d("诱导信息：",charSequence.toString());
            //诱导信息
        }

        @Override
        public void onRemainDistanceUpdate(CharSequence charSequence) {
            //总的剩余距离
            Log.d("剩余距离",charSequence.toString());
        }

        @Override
        public void onRemainTimeUpdate(CharSequence charSequence) {
            //总的剩余时间
        }

        @Override
        public void onGpsStatusChange(CharSequence charSequence, Drawable drawable) {
            //GPS状态发生变化，来自诱导引擎的消息
        }

        @Override
        public void onRouteFarAway(CharSequence charSequence, Drawable drawable) {
            //已经开始偏航
        }

        @Override
        public void onRoutePlanYawing(CharSequence charSequence, Drawable drawable) {
            //偏航规划中
        }

        @Override
        public void onReRouteComplete() {
            //重新算路成功
        }

        @Override
        public void onArriveDest() {
            //到达目的地
        }

        @Override
        public void onVibrate() {
            //震动
        }

        @Override
        public void onGetRouteDetailInfo(BikeRouteDetailInfo bikeRouteDetailInfo) {
            //获取骑行导航路线详细信息类
        }
    };
}
