package com.allen.onlyweather.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.allen.onlyweather.MyApplication;
import com.allen.onlyweather.city.adapter.CityInfoData;
import com.allen.onlyweather.scheduler.TaskScheduler;
import com.allen.onlyweather.utils.FileUtil;
import com.allen.onlyweather.utils.PreferencesUtil;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Allen.
 */

public class DBManage {

    private static DBHelper sDBHelper;
    private static DBManage sDBManage;
    private static final String CITY_INITED = "city_inited";

    private DBManage() {

    }

    public static DBManage getInstance() {
        if (sDBManage == null) {
            synchronized (DBManage.class) {
                if (sDBManage == null) {
                    sDBManage = new DBManage();
                    sDBHelper = DBHelper.getInstance(MyApplication.getContext());
                }
            }
        }
        return sDBManage;
    }


    public void copyCitiesToDb(){
        boolean cityInited = PreferencesUtil.get(CITY_INITED, false);
        if (cityInited) {
            return;
        }
        TaskScheduler.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                Log.i("copy", "yes");
                String citys = FileUtil.assetFile2String("cityList.txt", MyApplication.getContext());
                CityEntry cityEntry = MyApplication.getGson().fromJson(citys, CityEntry.class);
                Collections.sort(cityEntry.city_info, new CityComparator());
                SQLiteDatabase database = sDBHelper.getWritableDatabase();
                database.beginTransaction();
                try {
                    ContentValues values;
                    for (CityInfo cityInfo : cityEntry.city_info) {
                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder initials = new StringBuilder();
                        for (char hanzi : cityInfo.city.toCharArray()) {
                            String pinyin = Pinyin.toPinyin(hanzi);
                            stringBuilder.append(pinyin);
                            initials.append(pinyin.charAt(0));
                        }
                        stringBuilder.append(initials);
                        values = new ContentValues();
                        values.put(CityDao.CITY_NAME, cityInfo.city);
                        values.put(CityDao.PINYIN, stringBuilder.toString());
                        values.put(CityDao.CITY_ID, cityInfo.id);
                        database.insert(CityDao.TABLE_NAME, null, values);
                    }
                    database.setTransactionSuccessful();
                    PreferencesUtil.put(CITY_INITED, true);
                } catch (Exception e) {
                    e.getMessage();
                }finally {
                    database.endTransaction();
                }
            }
        });

    }

    public List<CityInfoData> getAllCities() {
        String sql = "select * from " + CityDao.TABLE_NAME;
        return getCities(sql, true);
    }

    public List<CityInfoData> searchCity(final String keyword) {
        String searchSql = "select * from " + CityDao.TABLE_NAME + " where " +
                CityDao.CITY_NAME + " like \"%" + keyword + "%\" or " +
                CityDao.PINYIN + " like \"%" + keyword + "%\" or " +
                CityDao.CITY_ID + " like \"%" + keyword + "%\"";
        return getCities(searchSql, false);
    }



    private List<CityInfoData> getCities(String sql, boolean all) {
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<CityInfoData> result = new ArrayList<>();
        CityInfoData city;
        String lastInitial = "";
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CityDao.CITY_NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(CityDao.PINYIN));
            String cityId = cursor.getString(cursor.getColumnIndex(CityDao.CITY_ID));
            city = new CityInfoData(name, pinyin, cityId);
            String currentInitial = pinyin.substring(0, 1);
            if (!lastInitial.equals(currentInitial) && all) {
                city.setInitial(currentInitial);
                lastInitial = currentInitial;
            }
            result.add(city);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * a-z排序
     */
   private class CityComparator implements Comparator<CityInfo> {

        @Override
        public int compare(CityInfo o1, CityInfo o2) {
            char a = Pinyin.toPinyin(o1.city.charAt(0)).charAt(0);
            char b = Pinyin.toPinyin(o2.city.charAt(0)).charAt(0);

            return a - b;
        }
    }

}
