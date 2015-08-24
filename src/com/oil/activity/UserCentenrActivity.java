package com.oil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.bean.OilUser;

public class UserCentenrActivity extends Activity implements OnClickListener {
	TextView tv_name, tv_realName, tv_phone, tv_comName;
	LinearLayout ll_changePwd;
	TextView tv_userinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercenter);
		initWeidget();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		OilUser oilUser = OilUser.getCurrentUser(UserCentenrActivity.this);
		if (oilUser != null) {
			tv_realName.setText(oilUser.getName());
			tv_phone.setText(oilUser.getPhone());
			tv_comName.setText(oilUser.getCorpName());
		}
	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		ImageView iv_back;
		TextView tv_pagetitle;
		tv_userinfo = (TextView) findViewById(R.id.tv_userinfo);
		tv_userinfo.setText(OilUser.getCurrentUser(UserCentenrActivity.this)
				.getUserName() + "\n" + "试阅用户" + "\n" + "到期时间：2015/10/01");
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
		tv_pagetitle.setText("会员中心");

		tv_realName = (TextView) findViewById(R.id.tv_uc_realName);
		tv_phone = (TextView) findViewById(R.id.tv_uc_tel);
		tv_comName = (TextView) findViewById(R.id.tv_uc_comName);
		ll_changePwd = (LinearLayout) findViewById(R.id.ll_uc_changepwd);
		ll_changePwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_uc_changepwd:
			startActivity(new Intent(UserCentenrActivity.this,
					UserResetPwdActivity.class));
			break;

		default:
			break;
		}
	}
}
