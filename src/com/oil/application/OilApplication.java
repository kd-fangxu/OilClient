package com.oil.application;

import java.util.HashMap;
import java.util.Map;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.oil.workmodel.AppInit;
import com.oil.workmodel.InstallInfoManager;
import com.oil.workmodel.UserRegisterManager;
import com.tencent.bugly.crashreport.CrashReport;

import android.app.Application;

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
		new InstallInfoManager(this).doPostInstallInfo();
		new UserRegisterManager(this).doAutoPost();
	}

	private void initCrashReport() {
		String appId = "900004672"; // ��Bugly(bugly.qq.com)ע���Ʒ��ȡ��AppId

		boolean isDebug = false; // true���App���ڵ��Խ׶Σ�false���App�����׶�

		CrashReport.initCrashReport(this, appId, isDebug); // ��ʼ��SDK
	}

	public Map<String, String> getTempleDataMap() {
		return TempleDataMap;
	}

	public String getTempleMapValue(String key) {
		return TempleDataMap.get(key);
	}

}
