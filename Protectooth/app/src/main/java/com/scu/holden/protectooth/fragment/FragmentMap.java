package com.scu.holden.protectooth.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.scu.holden.protectooth.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sql on 2017/10/31.
 */
public class FragmentMap extends Fragment{

    private Map<String,PoiItem> poiitemlist;
    private AMap.OnMarkerClickListener markerClickListener;
    private  MapView mMapView = null;
    private  AMap aMap=null;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Marker myMarker=null;
    private boolean flag=false;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    //button
    private String city = "";
    private Button button;
    private Context mContext;
    private static int[] z={R.drawable.poi_marker_1,R.drawable.poi_marker_2,R.drawable.poi_marker_3,R.drawable.poi_marker_4,R.drawable.poi_marker_5
            ,R.drawable.poi_marker_6,R.drawable.poi_marker_7,R.drawable.poi_marker_8,R.drawable.poi_marker_9};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.fragment_map, container, false);

        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.map);
        //在fragment执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
//        //设置蓝点模式
//        mMapView = (MapView)view.findViewById(R.id.map);
//        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        poiitemlist = new HashMap<String,PoiItem>();
        this.mContext=getActivity();
        iniView(view,mContext);

        return view;
    }

    private void iniView(View v, final Context context){
        button=(Button)v.findViewById(R.id.button_search);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        // 设置定位监听
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener listener) {
                mListener = listener;
                if (mlocationClient == null) {
                    //初始化定位
                    mlocationClient = new AMapLocationClient(context);
                    //初始化定位参数
                    mLocationOption = new AMapLocationClientOption();
                    //设置定位回调监听
                    mlocationClient.setLocationListener(new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation amapLocation) {
                            if (mListener != null&&amapLocation != null) {
                                if (amapLocation != null
                                        &&amapLocation.getErrorCode() == 0) {

                                    if(myMarker==null){
                                        myMarker=aMap.addMarker(new MarkerOptions().position(new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude())));
                                    }
                                    else {
                                        myMarker.setPosition(new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude()));
                                        if(flag==false){
                                            aMap.moveCamera(CameraUpdateFactory.newLatLng(myMarker.getPosition()));
                                            flag=true;
                                        }
                                    }
                                } else {
                                    String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                                    Log.e("AmapErr",errText);
                                }
                            }
                        }
                    });
                    //设置为高精度定位模式
                    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    //设置定位参数
                    mlocationClient.setLocationOption(mLocationOption);
                    // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                    // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                    // 在定位结束后，在合适的生命周期调用onDestroy()方法
                    // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                    mlocationClient.startLocation();//启动定位
                }
            }

            @Override
            public void deactivate() {

                mListener = null;
                if (mlocationClient != null) {
                    mlocationClient.stopLocation();
                    mlocationClient.onDestroy();
                }
                mlocationClient = null;
            }
        });
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        //设置BUTTON
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=mlocationClient.getLastKnownLocation().getCity();
                if(city.equals("")){
                    Toast.makeText(context,"定位失败，无法查询",Toast.LENGTH_SHORT).show();
                    return;
                }
                aMap.clear();
                query=new PoiSearch.Query("口腔医院","090202",city);
                query.setPageSize(10);// 设置每页最多返回多少条poiitem
                query.setPageNum(0);
                poiSearch = new PoiSearch(context,query);
                poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(myMarker.getPosition().latitude,
                        myMarker.getPosition().longitude), 10000));
                poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult result, int rCode) {
                        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                                if (result.getQuery().equals(query)) {// 是否是同一条
                                    poiResult = result;
                                    // 取得搜索到的poiitems有多少页
                                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                                    List<SuggestionCity> suggestionCities = poiResult
                                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                                    if (poiItems != null && poiItems.size() > 0) {
                                        poiitemlist.clear();
                                        int break_num=0;
                                        for(int i =0;i<poiItems.size()&&i<10;i++){
                                            if(poiItems.get(i).getTel().equals("")||poiItems.get(i).getSnippet().equals(""))
                                                continue;
//                                            Toast.makeText(context,poiItems.get(i).toString(),Toast.LENGTH_SHORT).show();
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(new LatLng(poiItems.get(i).getLatLonPoint().getLatitude(),poiItems.get(i).getLatLonPoint().getLongitude()));
                                            markerOptions.title(poiItems.get(i).getTitle());
                                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                                    .decodeResource(getResources(),z[break_num])));
                                            poiitemlist.put(poiItems.get(i).getTitle(),poiItems.get(i));
                                            aMap.addMarker(markerOptions).setInfoWindowEnable(false);
                                            break_num++;
                                        }
//                                        button.reset();


                                    } else if (suggestionCities != null
                                            && suggestionCities.size() > 0) {

//                                        Toast.makeText(context,suggestionCities.get(0).getCityName().toString(),Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context,"sss",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(context,"noresult",Toast.LENGTH_SHORT).show();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
                poiSearch.searchPOIAsyn();


            }
        });
        //设置marker监听
        markerClickListener = new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //调用alertDialog
                //Toast.makeText(context,((Integer)poiitemlist.get(marker.getTitle()).getDistance()).toString(),Toast.LENGTH_SHORT).show();
                PoiItem poiItem = poiitemlist.get(marker.getTitle());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(poiItem.getTitle());
                builder.setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setIcon(R.drawable.hsp);
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view=inflater.inflate(R.layout.dialog_normal_layout, null);
                TextView title_distance=(TextView)view.findViewById(R.id.title_distance);
                title_distance.setText(poiItem.getDistance()+"米");
                TextView title_tel=(TextView)view.findViewById(R.id.title_tel);
                title_tel.setText(poiItem.getTel());
                TextView title_address=(TextView)view.findViewById(R.id.title_address);
                title_address.setText(poiItem.getSnippet());
                builder.setView(view);
                builder.show();
                return true;
            }
        };
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //在fragment执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在fragment执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
