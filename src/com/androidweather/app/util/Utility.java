package com.androidweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.androidweather.app.db.CoolWeatherDB;
import com.androidweather.app.entity.City;
import com.androidweather.app.entity.County;
import com.androidweather.app.entity.Province;
/*
 * 服务器会返回一段文本信息如下所示：
 *   01|北京, 02|上海, 03|天津 ,
 */

public class Utility {
	/*
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB coolWeatherDB, String response) {
		/*
		 * 提供了handleProvincesResponse()方法用于解析和处理服务器返回的省级数据
		 * 解析的规则就是（先按逗号分隔），再按单竖线分隔，接着将解析出来的数据设置到实体类中
		 * 最后调用CoolWeatherDB中的save()方法将数据存储到相应的表中
		 */
		
		/*
		 * 对于字符串处理Android为我们提供了一个简单实用的TextUtils类
		 * 是否为空字符 boolean android.text.TextUtils.isEmpty(CharSequence str)
		 * 拆分字符串 String[] android.text.TextUtils.split(String text, String expression)
		 * 拆分字符串使用正则 String[] android.text.TextUtils.split(String text, Pattern pattern)
		 */
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String [] array=p.split("\\|");
					Province province=new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//将解析出来的数据存储到Province表
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
		if (!TextUtils.isEmpty(response)) {
			String [] allCities=response.split(",");
			if (allCities!=null && allCities.length>0) {
				for (String c : allCities) {
					String [] array=c.split("\\|");
					City city=new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					//将解析出来的数据存储到City表
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的县级数据
	 */
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties!=null && allCounties.length>0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					County county=new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					//将解析出来的数据存储到County表
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
	 */
	public static void handleWeatherResponse(Context context,String response){
		try {
			// 利用要解析的response作为参数，构建一个JsonObject对象
			JSONObject jsonobject=new JSONObject(response);//解析的json作为对象
			//还原一个数据的方式/利用键名获得Json对象
			JSONObject weatherInfo = jsonobject.getJSONObject("weatherinfo");
			//从对象中获取数据信息
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1=weatherInfo.getString("temp1");
			String temp2=weatherInfo.getString("temp2");
			String weatherDesp=weatherInfo.getString("weather");
			String publishTime=weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中
	 */
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		//用editor的put方法将键值对存储数据
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		//通过Editor的commit()方法将数据提交到Sharedpreferences内
		editor.commit();
	}
}










