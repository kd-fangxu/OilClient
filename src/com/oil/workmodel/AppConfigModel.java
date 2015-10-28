package com.oil.workmodel;

import com.oil.utils.SharedPreferenceUtils;

import android.content.Context;

/**
 * app ������Ϣ ������
 * 
 * @author xu
 *
 */
public class AppConfigModel {
	Context context;
	String spName = "appConfigModel";
	public static String spKey_pageTypeShow = "pageTypeShow";// 1�������л��� ȫ����Ʒ
																// 2�������л����ҵĹ�ע������

	public AppConfigModel(Context context) {
		this.context = context;
	}

	/**
	 * �������
	 * 
	 * @param object
	 */
	public void setConfig(String key, Object object) {
		SharedPreferenceUtils.setParam(context, spName, key, object);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Object getConfig(String key, Object defaultValue) {
		return SharedPreferenceUtils.getParam(context, spName, key, defaultValue);
	}
}
