package com.oil.workmodel;

import com.oil.utils.SharedPreferenceUtils;

import android.content.Context;

/**
 * app 设置信息 管理类
 * 
 * @author xu
 *
 */
public class AppConfigModel {
	Context context;
	String spName = "appConfigModel";
	public static String spKey_pageTypeShow = "pageTypeShow";// 1：界面切换到 全部产品
																// 2：界面切换到我的关注数据组

	public AppConfigModel(Context context) {
		this.context = context;
	}

	/**
	 * 添加设置
	 * 
	 * @param object
	 */
	public void setConfig(String key, Object object) {
		SharedPreferenceUtils.setParam(context, spName, key, object);
	}

	/**
	 * 获取设置
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Object getConfig(String key, Object defaultValue) {
		return SharedPreferenceUtils.getParam(context, spName, key, defaultValue);
	}
}
