package com.allen.onlyweather.model;

import com.allen.onlyweather.ModelManager;
import com.allen.onlyweather.MyApplication;
import com.allen.onlyweather.city.adapter.CityInfoData;
import com.allen.onlyweather.common.BaseModel;
import com.allen.onlyweather.common.Constants;
import com.allen.onlyweather.db.DBManage;
import com.allen.onlyweather.model.callbacks.ModelCallback;
import com.allen.onlyweather.scheduler.TaskCallback;
import com.allen.onlyweather.scheduler.TaskScheduler;
import com.allen.onlyweather.utils.LogHelper;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.silencedut.router.Router;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Allen.
 */

public class CityModel extends BaseModel {

    private AMapLocationClient mLocationClient;
    private String mCityName = Constants.DEFAULT_STR;
    private String mLocationId = Constants.DEFAULT_STR;
    private String mDefaultId = Constants.DEFAULT_STR;

    @Override
    public void onCreate() {
        mDefaultId = PreferencesUtil.get(Constants.DEFAULT_CITY, Constants.DEFAULT_STR);
        mLocationId = PreferencesUtil.get(Constants.LOCATION, Constants.DEFAULT_STR);
        initLocation();
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(MyApplication.getContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    TaskScheduler.execute(new Runnable() {
                        @Override
                        public void run() {
                            String locationCityId = PreferencesUtil.get(Constants.DEFAULT_CITY, Constants.DEFAULT_STR);
                            if (aMapLocation.getErrorCode() == 0) {
                                String district = aMapLocation.getDistrict();
                                LogHelper.d(district);
                                List<CityInfoData> citiesInfo = DBManage.getInstance().searchCity(district);
                                if (citiesInfo.size() == 0) {
                                    district = district.substring(0, 2);
                                }
                                citiesInfo = DBManage.getInstance().searchCity(district);
                                mCityName = Constants.DEFAULT_STR;


                                if (citiesInfo.size() == 0) {
                                    district = aMapLocation.getCity();
                                    district = district.substring(0, 2);
                                    citiesInfo = DBManage.getInstance().searchCity(district);
                                }
                                if (citiesInfo.size() > 0) {
                                    mCityName = citiesInfo.get(0).getCityName();
                                    locationCityId = citiesInfo.get(0).getCityId();

                                }
                                mLocationId = locationCityId;
                                PreferencesUtil.put(Constants.LOCATION, mLocationId);
                            }
                            Router.instance().getReceiver(ModelCallback.LocationResult.class).onLocationComplete(locationCityId, aMapLocation.getErrorCode() == 0);
                        }
                    });
                }
            }
        });
    }


    public String getLocationCityId() {
        return mLocationId;
    }

    public String getDefaultId() {
        return mDefaultId;
    }

    public boolean noDefaultCity() {
        return Constants.DEFAULT_STR.equals(mDefaultId);
    }

    public void setDefaultId(String defaultId) {
        mDefaultId = defaultId;
        PreferencesUtil.remove(Constants.DEFAULT_CITY);
        PreferencesUtil.put(Constants.DEFAULT_CITY, defaultId);
    }

    public String locationCityName() {
        return mCityName;
    }

    public void getAllCities(final TaskCallback.Success<List<CityInfoData>> taskCallback) {
        TaskScheduler.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                List<CityInfoData> allCities = DBManage.getInstance().getAllCities();
                TaskScheduler.notifySuccessToUI(allCities,taskCallback);
            }
        });
    }

    public void matchCities(final String key,final TaskCallback.Success<List<CityInfoData>> matchedCityCallback) {
        TaskScheduler.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                List<CityInfoData> matchedCities = DBManage.getInstance().searchCity(key);
                TaskScheduler.notifySuccessToUI(matchedCities,matchedCityCallback);
            }
        });
    }

    public void unFollowedCity(String cityId) {
        Set<String> defaultFollowed = new HashSet<>();
        defaultFollowed = PreferencesUtil.get(Constants.FOLLOWED_CITIES, defaultFollowed);
        if (!defaultFollowed.contains(cityId)) {
            return;
        }
        String defaultCityId = getDefaultId();
        if (defaultCityId.equals(cityId)) {
            ModelManager.getModel(WeatherModel.class).updateWeather(getLocationCityId());
        }
        defaultFollowed.remove(cityId);
        PreferencesUtil.remove(Constants.FOLLOWED_CITIES);
        PreferencesUtil.put(Constants.FOLLOWED_CITIES, defaultFollowed);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }

    public void startLocation() {
        mLocationClient.startLocation();
    }
}
