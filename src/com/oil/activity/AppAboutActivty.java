package com.oil.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oilclient.R;

public class AppAboutActivty extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appabout);
		initBaseWeidget();
		initWeidget();
	}

	ImageView iv_appicon;
	TextView tv_appInfo;

	private void initWeidget() {
		// TODO Auto-generated method stub
		iv_appicon = (ImageView) findViewById(R.id.iv_ab_appicon);
		tv_appInfo = (TextView) findViewById(R.id.tv_ab_appinfo);
		initInfo();
	}

	private void initBaseWeidget() {
		// TODO Auto-generated method stub
		ImageView iv_back;
		TextView tv_pagetitle;

		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
		tv_pagetitle.setText("关于");
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public void initInfo() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			iv_appicon.setImageResource(info.applicationInfo.icon);
			tv_appInfo.setText(this.getString(R.string.app_name) + "\nV"
					+ version);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
