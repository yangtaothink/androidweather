package com.androidweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//�Զ���һ��SQLite���ݿ�����ĸ�����
public class CoolWeatherOpenHelper extends SQLiteOpenHelper{
    //������������䶨��Ϊ������Ȼ����onCreate()������ִ�д���
	/**
	 *  Province�������
	 *  
	 *  create table Province(
	 *          ����     ������
	 *  		id integer primary key autoincrement,
	 *  		province_name text,
	 *  		province_code text)
	 */
	public static final String CREATE_PROVINCE="create table Province("
			+ "id integer primary key autoincrement,"//����id������������
			+ "province_name text,"//province_name��ʾʡ��
			+ "province_code text)";//province_code��ʾʡ������
	
	/**
	 *  City������� 
	 *  
	 *  create table City(
	 *          id integer primary key autoincrement,
	 *          city_name text,
	 *          city_code text,
	 *          ����                          ������
	 *          province_id integer)
	 */
	
	public static final String CREATE_CITY="create table City("
			+ "id integer primary key autoincrement,"//����id������������
			+ "city_name text,"//city_name��ʾ������
			+ "city_code text,"//city_code��ʾ�м�����
			+ "province_id integer)";//province_id��City�������Province������
	
	/**
	 *   County�������
	 *   
	 *   create table County(
	 *           id integer primary key autoincrement,
	 *           county_name text,
	 *           county_code text,
	 *           city_id integer)
	 */
	public static final String CREATE_COUNTY="create table County("
			+ "id integer primary key autoincrement,"//id������������
			+ "county_name text,"//county_name��ʾ����
			+ "county_code text,"//county_code��ʾ�ؼ�����
			+ "city_id integer)";//city_id��County�������City������
	
	//���ø���SQLiteOpenHelper�Ĺ��캯��
	public CoolWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	//����(�����ǵ�һ�λ��SQLite���ݿ��ʱ���Զ����лص��ķ���)��
	//������ʹ��getReadableDatabase() ������ getWritableDatabase
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);//����Province��
		db.execSQL(CREATE_CITY);//����City��
		db.execSQL(CREATE_COUNTY);//����County��
	}
	//����(�������������ݿ�İ汾��ʱ�򣬱��ص���һ������).
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
