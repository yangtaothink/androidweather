package com.androidweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//自定义一个SQLite数据库操作的辅助类
public class CoolWeatherOpenHelper extends SQLiteOpenHelper{
    //将三条建表语句定义为常量，然后再onCreate()方法中执行创建
	/**
	 *  Province表建表语句
	 *  
	 *  create table Province(
	 *          列名     列类型
	 *  		id integer primary key autoincrement,
	 *  		province_name text,
	 *  		province_code text)
	 */
	public static final String CREATE_PROVINCE="create table Province("
			+ "id integer primary key autoincrement,"//其中id是自增长主键
			+ "province_name text,"//province_name表示省名
			+ "province_code text)";//province_code表示省级代号
	
	/**
	 *  City表建表语句 
	 *  
	 *  create table City(
	 *          id integer primary key autoincrement,
	 *          city_name text,
	 *          city_code text,
	 *          列名                          列类型
	 *          province_id integer)
	 */
	
	public static final String CREATE_CITY="create table City("
			+ "id integer primary key autoincrement,"//其中id是自增长主键
			+ "city_name text,"//city_name表示城市名
			+ "city_code text,"//city_code表示市级代号
			+ "province_id integer)";//province_id是City表关联的Province表的外键
	
	/**
	 *   County表建表语句
	 *   
	 *   create table County(
	 *           id integer primary key autoincrement,
	 *           county_name text,
	 *           county_code text,
	 *           city_id integer)
	 */
	public static final String CREATE_COUNTY="create table County("
			+ "id integer primary key autoincrement,"//id是自增长主键
			+ "county_name text,"//county_name表示县名
			+ "county_code text,"//county_code表示县级代号
			+ "city_id integer)";//city_id是County表关联的City表的外键
	
	//调用父类SQLiteOpenHelper的构造函数
	public CoolWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	//创建(当我们第一次获得SQLite数据库的时候，自动进行回调的方法)。
	//不管是使用getReadableDatabase() 或者是 getWritableDatabase
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);//创建Province表
		db.execSQL(CREATE_CITY);//创建City表
		db.execSQL(CREATE_COUNTY);//创建County表
	}
	//更新(当我们提升数据库的版本的时候，被回调的一个方法).
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
