package com.oil.application;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.view.View.OnCreateContextMenuListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.oil.workmodel.AppInit;
import com.tencent.bugly.crashreport.CrashReport;

public class OilApplication extends Application {
	private Map<String, String> TempleDataMap = new HashMap<String, String>();
	private static OilApplication OilApplication;

	public static OilApplication getinstance() {
		if (null == OilApplication) {
			OilApplication = new OilApplication();
		}
		return OilApplication;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));
		new AppInit(this).initProductStruct();
		initCrashReport();
	}

	private void initCrashReport() {
		String appId = "900004672"; // 上Bugly(bugly.qq.com)注册产品获取的AppId

		boolean isDebug = false; // true代表App处于调试阶段，false代表App发布阶段

		CrashReport.initCrashReport(this, appId, isDebug); // 初始化SDK
	}

	public Map<String, String> getTempleDataMap() {
		return TempleDataMap;
	}

	public String getTempleMapValue(String key) {
		return TempleDataMap.get(key);
	}

}
