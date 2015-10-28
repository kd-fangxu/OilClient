package com.oil.utils;


import com.oil.bean.MyConfig;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
public class UserInfoTools {
	
	/**
	 * 用户信息
	 * @param context
	 * @return
	 */
	public static SharedPreferences getUserSP(Context context){
		
		SharedPreferences mySharedPreferences=context.getSharedPreferences(MyConfig.USER_INFO_FLAG, 
				Activity.MODE_PRIVATE); 
		return mySharedPreferences;
	}
	
	
	
}