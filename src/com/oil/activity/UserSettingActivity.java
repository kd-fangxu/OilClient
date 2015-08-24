package com.oil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.datasave.AppDataCatchModel;
import com.oil.dialogs.CommontitleDialog;
import com.oil.dialogs.CommontitleDialog.onComDialogBtnClick;
import com.oil.workmodel.AppVersionManager;

public class UserSettingActivity extends Activity implements OnClickListener {
	LinearLayout ll_checkUpdate;
	ImageView iv_back;
	TextView tv_pagetitle;
	LinearLayout ll_clearCatch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting);
		initWeidget();

	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		ll_clearCatch = (LinearLayout) findViewById(R.id.ll_clearcatch);
		ll_clearCatch.setOnClickListener(this);
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
		tv_pagetitle.setText("设置");

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
		case R.id.ll_clearcatch:
			final CommontitleDialog ctDialog = new CommontitleDialog(
					UserSettingActivity.this);
			ctDialog.Init("提示", "确定要清除缓存吗？", new onComDialogBtnClick() {

				@Override
				public void onConfirmClick() {
					// TODO Auto-generated method stub
					new AppDataCatchModel(UserSettingActivity.this)
							.clearCatch();
					ctDialog.disMiss();

				}

				@Override
				public void onCancelClick() {
					// TODO Auto-generated method stub
					ctDialog.disMiss();
				}
			});
			ctDialog.show();
			break;
		default:
			break;
		}
	}

}
