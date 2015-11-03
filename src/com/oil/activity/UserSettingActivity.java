package com.oil.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;
import com.oil.application.OilApplication;
import com.oil.bean.Constants;
import com.oil.datasave.AppDataCatchModel;
import com.oil.dialogs.CommontitleDialog;
import com.oil.dialogs.CommontitleDialog.onComDialogBtnClick;
import com.oil.utils.SharedPreferenceUtils;
import com.oil.utils.ToastUtils;
import com.oil.workmodel.AppVersionManager;

public class UserSettingActivity extends Activity implements OnClickListener {
	LinearLayout ll_checkUpdate;
	ImageView iv_back;
	TextView tv_pagetitle;
	LinearLayout ll_clearCatch;
	LinearLayout ll_editIp;

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
		ll_editIp = (LinearLayout) findViewById(R.id.ll_uc_editip);
		if (!OilApplication.isDebug) {
			ll_editIp.setVisibility(View.GONE);
		}
		ll_editIp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initIpEditDialog();
			}
		});
	}

	Dialog ipEditDialog;

	@SuppressWarnings("unused")
	private void initIpEditDialog() {
		if (ipEditDialog != null) {
			ipEditDialog.show();
			return;
		}
		ipEditDialog = new Dialog(this);
		View contentView = View.inflate(this, R.layout.debug_dia_editip, null);
		final EditText et_editip = (EditText) contentView.findViewById(R.id.et_ipedip);
		Button btn_confirm = (Button) contentView.findViewById(R.id.btn_ipconfirm);
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String contentIp = et_editip.getText().toString();
				if (contentIp.length() > 0) {
					if (contentIp.equals("1")) {
						Constants.IP="http://192.168.1.234:8080/oilWeb/";
					}else if(contentIp.equals("2"))
					{
						Constants.IP="http://info.oilchem.net/";
					}
//					Constants.IP = contentIp;
					contentIp=Constants.IP ;
					SharedPreferenceUtils.setParam(UserSettingActivity.this, "ipconfig", "ip", contentIp);
					ToastUtils.getInstance(UserSettingActivity.this).showText("ip设置为" + Constants.IP);
					ipEditDialog.dismiss();
				}
			}
		});
		ipEditDialog.setContentView(contentView);
		ipEditDialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_uc_checkupdate:
			AppVersionManager.doCheckUpdate(UserSettingActivity.this, true);
			break;
		case R.id.ll_clearcatch:
			final CommontitleDialog ctDialog = new CommontitleDialog(UserSettingActivity.this);
			ctDialog.Init("提示", "确定要清除缓存吗？", new onComDialogBtnClick() {

				@Override
				public void onConfirmClick() {
					// TODO Auto-generated method stub
					new AppDataCatchModel(UserSettingActivity.this).clearCatch();
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
