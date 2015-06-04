package com.oil.application;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.view.View.OnCreateContextMenuListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
	}

	public Map<String, String> getTempleDataMap() {
		return TempleDataMap;
	}

	public String getTempleMapValue(String key) {
		return TempleDataMap.get(key);
	}

}
