package com.haha.baidumap;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.PermissionUtils;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.lxj.easyadapter.CommonAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;

public class MainActivity extends BaseActivity implements View.OnClickListener,PermissionsUtil.IPermissionsCallback{
private MapView mapView;
private TextView et_search;
private BaiduMap baiduMap;
private ImageView iv_switch,iv_refresh;
private boolean isHotMap;
private LocationClient locationClient;
private int a=0;
private int num = 0;
private boolean isLocation = true;
private Animation animation;
private LatLng latLng;
private LatLng OnclickLatlng;
private PoiSearch poiSearch;
private PoiSearch poiSearch1;
private String city;
private String cityId;
private String province;
private String provinceId;
private SearchFragment searchFragment;
public List<PoiInfo> poiDetailInfos = new ArrayList<>();
private PermissionsUtil permissionsUtil;
    public CommonAdapter<PoiDetailInfo> commonAdapter;
    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        requestPermission();
//        permissionsUtil = PermissionsUtil
//                .with(this)
//                .requestCode(0)
//                .isDebug(true)
//                .permissions(PermissionsUtil.Permission.Phone.READ_PHONE_STATE)
//                .permissions(PermissionsUtil.Permission.Location.ACCESS_COARSE_LOCATION,PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION)
//                .permissions(PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
//                .request();
        et_search = findViewById(R.id.et_search);
        searchFragment = SearchFragment.newInstance();
        animation = AnimationUtils.loadAnimation(this,R.anim.refresh);
        mapView = findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        iv_refresh = findViewById(R.id.iv_refresh);
        iv_switch = findViewById(R.id.iv_switch);
        iv_switch.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        baiduMap.setOnMapClickListener(mapClickListener);
        baiduMap.setOnMarkerClickListener(markerClickListener);

        poiSearch = poiSearch.newInstance();//初始化poi检索
        poiSearch1 = poiSearch1.newInstance();
        mapLongClick();
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.showFragment(getSupportFragmentManager(),"aaa");
            }
        });
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                Log.d("keyword",keyword);
                if (keyword!=null){
                    searchPoi(keyword);
                }
            }
        });

    }
//    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        //Android 6.0判断用户是否授予定位权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            //判断是否具有权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(MainActivity.this,"自Android 6.0开始需要打开位置权限",Toast.LENGTH_SHORT).show();
                }
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }else {
                LocationClient();
            }
        }else {
            LocationClient();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户允许改权限，0表示允许，-1表示拒绝 PERMISSION_GRANTED = 0， PERMISSION_DENIED = -1
                //permission was granted, yay! Do the contacts-related task you need to do.
                //这里进行授权被允许的处理
                LocationClient();
            } else {
                //permission denied, boo! Disable the functionality that depends on this permission.
                //这里进行权限被拒绝的处理
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void initData() {
        super.initData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        poiSearch.destroy();
        poiSearch1.destroy();
    }
    private void mapLongClick(){
        final XPopup.Builder builder = new XPopup.Builder(this)
                .watchView(baiduMap.getmGLMapView());
        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                builder.asAttachList(new String[]{"到这里去"}, null,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                BikeNavigateHelperInit(latLng);
                            }
                        }).show();

            }
        });
    }

    private void showMapSwitch(){
        new XPopup.Builder(this).asCenterList("请选择地图类型", new String[]{"普通地图","卫星地图","城市热力图"}, new OnSelectListener() {
            @Override
            public void onSelect(int position, String text) {
                switch (position){
                    case 0:
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        break;
                    case 2:
                        if (isHotMap){
                            baiduMap.setBaiduHeatMapEnabled(false);
                            isHotMap = false;
                        }else {
                            baiduMap.setBaiduHeatMapEnabled(true);
                            isHotMap = true;
                        }
                        break;
                }
            }
        }).show();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_switch:
                    showMapSwitch();
                break;
            case R.id.iv_refresh:
                iv_refresh.startAnimation(animation);
                refresh(true);
                break;
        }
    }
    private void LocationClient(){
        locationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(locationListener);
        locationClient.start();
    }
    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null ||mapView ==null){
                return;
            }

                city = bdLocation.getCity();
                province = bdLocation.getProvince();
                Log.d("城市+省",city+province);
            Log.d("经纬度：",bdLocation.getLatitude()+bdLocation.getLongitude()+"");
            Log.d("code码：",bdLocation.getLocType()+"");
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            poi(latLng);
            if (isLocation){
                baiduMap.setMyLocationData(locationData);
                refresh(false);
                isLocation = false;
                a++;
                Log.d("定位次数：",a+"");
            }
        }
    };
    private OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR){
                num++;
                Log.d("检索次数",num+"");
//            Log.d("字数",poiResult.getAllPoi().size()+"");
                List<OverlayOptions> options = new ArrayList<>();
                poiDetailInfos.clear();
                baiduMap.clear();
                for (int i=0;i<poiResult.getAllPoi().size();i++){
                    LatLng latLng = poiResult.getAllPoi().get(i).location;
                    OverlayOptions options1 = new MarkerOptions().position(latLng).title(poiResult.getAllPoi().get(i).getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_scenic));
                    options.add(options1);
                    Log.d("aaaaa",poiResult.getAllPoi().get(i).getAddress());
                    poiDetailInfos.add(poiResult.getAllPoi().get(i));
                }
                new XPopup.Builder(MainActivity.this).asCustom(new BottomComment(MainActivity.this,poiDetailInfos)).show();
                baiduMap.addOverlays(options);

                locationClient.unRegisterLocationListener(locationListener);
                locationClient.stop();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            for (int i=0;i<poiDetailSearchResult.getPoiDetailInfoList().size();i++){
                a++;
                Log.d("啊啊啊啊啊啊",poiDetailSearchResult.getPoiDetailInfoList().get(i).getDetailUrl()+a);
                Log.d("图片",poiDetailSearchResult.getPoiDetailInfoList().get(i).getArea());
            }

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
    //回到定位点
    private void refresh(boolean isOnclick){
        isLocation = true;
        if (isOnclick){
            LocationClient();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MapStatus mapStatus = new MapStatus.Builder()
                        .target(latLng)
                        .zoom(20)
                        .build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                baiduMap.setMapStatus(mapStatusUpdate);
            }
        }, 3000);//3秒后执行Runnable中的run方法
    }
    private void poi(LatLng latLng){
        poiSearch.searchNearby(new PoiNearbySearchOption()
                .location(latLng)
                .radius(10000)
                .keyword("景点")
                .scope(2)
                .pageCapacity(100));
        poiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);
    }

    private void poiDetails(LatLng latlng){

    }

    //地图点击
    private BaiduMap.OnMapClickListener mapClickListener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {
            poi(point);
            OnclickLatlng = point;
//构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_location);
//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
//在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
        }

        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            Log.d("名称1；",mapPoi.getName());
            Intent intent = new Intent(MainActivity.this,PanoramaActivity.class);
            intent.putExtra("longitude",mapPoi.getPosition().longitude);
            intent.putExtra("latitude",mapPoi.getPosition().latitude);
            startActivity(intent);
            return false;
        }
    };
    private BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Log.d("名称；",marker.getId());
            Intent intent = new Intent(MainActivity.this,PanoramaActivity.class);
            intent.putExtra("longitude",marker.getPosition().longitude);
            intent.putExtra("latitude",marker.getPosition().latitude);
            startActivity(intent);
            return false;
        }
    };
    // 获取导航控制类
// 引擎初始化
    private void BikeNavigateHelperInit(final LatLng endPt){

        BikeNavigateHelper.getInstance().initNaviEngine(this, new IBEngineInitListener() {
            @Override
            public void engineInitSuccess() {
                //骑行导航初始化成功之后的回调
                routePlanWithParam(endPt);
            }

            @Override
            public void engineInitFail() {
                //骑行导航初始化失败之后的回调
            }
        });
    }
    //算路
    private void routePlanWithParam(LatLng endPt){
        BikeNaviLaunchParam param = new BikeNaviLaunchParam().stPt(latLng).endPt(endPt).vehicle(0);
        BikeNavigateHelper.getInstance().routePlanWithParams(param, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                //执行算路开始的逻辑
            }

            @Override
            public void onRoutePlanSuccess() {
                //算路成功
                startActivity(new Intent(MainActivity.this,NavigationActivity.class));
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError bikeRoutePlanError) {
                //执行算路失败的逻辑
            }
        });
    }

    private void searchPoi(String name){
        poiSearch1.searchInCity(new PoiCitySearchOption()
            .city(city)
            .keyword(name));
        poiSearch1.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(final PoiResult poiResult) {

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR){
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.search);
                    OverlayOptions options = new MarkerOptions().position(poiResult.getAllPoi().get(0).getLocation()).icon(bitmap);
                    baiduMap.addOverlay(options);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MapStatus mapStatus = new MapStatus.Builder()
                                    .target(poiResult.getAllPoi().get(0).getLocation())
                                    .zoom(20)
                                    .build();
                            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                            baiduMap.setMapStatus(mapStatusUpdate);
                        }
                    }, 2000);//3秒后执行Runnable中的run方法
                }else {
                    Toast.makeText(MainActivity.this,"没有搜索到哟",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        Toast.makeText(MainActivity.this,"已经获得权限："+permission,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {
        Toast.makeText(MainActivity.this,"拒绝了权限："+permission,Toast.LENGTH_SHORT).show();
    }
}
