package com.oil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.workmodel.AppVersionManager;

public class UserSettingActivity extends Activity implements OnClickListener {
	LinearLayout ll_checkUpdate;
	ImageView iv_back;
	TextView tv_pagetitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting);
		initWeidget();

	}

	private void initWeidget() {
		// TODO Auto-generated method stub

		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
		tv_pagetitle.setText("…Ë÷√");

		ll_checkUpdate = (LinearLayout) findViewById(R.id.ll_uc_checkupdate);
		ll_checkUpdate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_uc_checkupdate:
			AppVersionManager.doCheckUpdate(UserSettingActivity.this, true);
			break;

		default:
			break;
		}
	}

}
