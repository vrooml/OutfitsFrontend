package com.example.outfits.UI;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.weather.OnGetWeatherResultListener;
import com.baidu.mapapi.search.weather.WeatherDataType;
import com.baidu.mapapi.search.weather.WeatherResult;
import com.baidu.mapapi.search.weather.WeatherSearch;
import com.baidu.mapapi.search.weather.WeatherSearchOption;
import com.example.outfits.MyApplication;
import com.example.outfits.R;

import java.util.List;
import java.util.Locale;

public class OutfitFragment extends Fragment{
    ViewPager2 viewPager2;
    TextView myOutfit;
    TextView recommendOutfit;
    TextView tempetature;
    TextView phenomenon;
    ConstraintLayout rootLayout;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener;
    LocationClientOption option = new LocationClientOption();

    public OutfitFragment(){
        // Required empty public constructor
    }


    public static OutfitFragment newInstance(){
        OutfitFragment fragment=new OutfitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_outfit,container,false);
        viewPager2=view.findViewById(R.id.outfit_viewpager);
        myOutfit=view.findViewById(R.id.my_outfit);
        recommendOutfit=view.findViewById(R.id.recommend_outfit);
        rootLayout=view.findViewById(R.id.outfit_toolbar);
        tempetature=view.findViewById(R.id.temperature);
        phenomenon=view.findViewById(R.id.phenomenon);
        //定位初始化
        mLocationClient = new LocationClient(MyApplication.getContext());

//        //通过LocationClientOption设置LocationClient相关参数
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        option.setIsNeedAddress(true);
//
//        //设置locationClientOption
//        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        initLocation();
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);
        //开启地图定位图层
        mLocationClient.start();

        //注册监听函数
        FragmentStateAdapter adapter=new FragmentStateAdapter(this){
            @NonNull
            @Override
            public Fragment createFragment(int position){
                if(position==0){
                    return MyOutfitFragment.newInstance();
                }else{
                    return RecommendOutfitFragment.newInstance();
                }
            }

            @Override
            public int getItemCount(){
                return 2;
            }
        };

        myOutfit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                myOutfit.setTextSize(20);
                recommendOutfit.setTextSize(16);
                viewPager2.setCurrentItem(0,true);
            }
        });

        recommendOutfit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                myOutfit.setTextSize(16);
                recommendOutfit.setTextSize(20);
                viewPager2.setCurrentItem(1,true);
            }
        });
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        Transition transition = new ChangeBounds();
                        transition.setDuration(1000);
                        TransitionManager.beginDelayedTransition(rootLayout,transition);
                        myOutfit.setTextSize(20);
                        recommendOutfit.setTextSize(16);
                        break;
                    case 1:
                        transition = new ChangeBounds();
                        transition.setDuration(1000);
                        TransitionManager.beginDelayedTransition(rootLayout,transition);
                        myOutfit.setTextSize(16);
                        recommendOutfit.setTextSize(20);
                        break;
                }
            }
        });
        return view;
    }

    private void initLocation() {
        //就是这个方法设置为true，才能获取当前的位置信息
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setScanSpan(1000);
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span = 1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            //经纬度
            if(location.getLatitude()==4.9E-324){
                option.setScanSpan(1000);
            }else{
                option.setScanSpan(100000);
            }
            mLocationClient.setLocOption(option);
            double lati = location.getLatitude();
            double longa = location.getLongitude();
            //打印出当前位置
            Log.e("TAG", "location.getLat()=" + location.getLatitude());
            Log.e("TAG", "location.getLong()=" + location.getLongitude());
            Log.e("TAG", "location.getAddrStr()=" + location.getAddrStr());
            //打印出当前城市
            Log.e("TAG", "location.getCity()=" + location.getCity());
            //返回码
            int i = location.getLocType();
            String districtID =location.getAdCode();
            WeatherSearchOption weatherSearchOption = new WeatherSearchOption()
                    .weatherDataType(WeatherDataType.WEATHER_DATA_TYPE_ALL)
                    .districtID(districtID);

            WeatherSearch mWeatherSearch = WeatherSearch.newInstance();
            mWeatherSearch.setWeatherSearchResultListener(new OnGetWeatherResultListener() {
                @Override
                public void onGetWeatherResultListener(final WeatherResult weatherResult) {
                    if(weatherResult!=null){
                        tempetature.setText(String.valueOf(weatherResult.getRealTimeWeather().getTemperature()));
                        phenomenon.setText(weatherResult.getRealTimeWeather().getPhenomenon());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getPhenomenon());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getWindDirection());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getWindPower());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getAirQualityIndex());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getRelativeHumidity());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getTemperature());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getSensoryTemp());
                        Log.e("TAG", ""+weatherResult.getRealTimeWeather().getHourlyPrecipitation());
                        Log.e("TAG", ""+weatherResult.getLocation().getCity());

                    }
                }
            });
            mWeatherSearch.request(weatherSearchOption);
        }
    }

}