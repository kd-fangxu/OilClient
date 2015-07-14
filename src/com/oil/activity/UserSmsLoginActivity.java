package com.oil.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.oilclient.R;
import com.loopj.android.http.RequestParams;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.inter.OnReturnListener;
import com.oil.utils.CommonUtil;
import com.oil.utils.HttpTool;
import com.tencent.bugly.proguard.t;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserSmsLoginActivity extends Activity implements OnClickListener {
	EditText et_tel, et_smscoede;
	ImageView iv_pageback;
	Button btn_getcode, btn_confirm;
	boolean isCodeEffect = false;// 验证码是否有效

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersmslogin);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_tel = (EditText) findViewById(R.id.et_sms_tel);
		et_smscoede = (EditText) findViewById(R.id.et_sms_code);
		btn_getcode = (Button) findViewById(R.id.btn_getsmscode);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		iv_pageback = (ImageView) findViewById(R.id.iv_pageback);
		btn_getcode.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		iv_pageback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_pageback:
			finish();
			break;
		case R.id.btn_getsmscode:
			if (isCodeEffect) {
				getSmsCode();
			}
			break;
		case R.id.btn_confirm:
			String tel = et_tel.getText().toString();
			String code = et_smscoede.getText().toString();
			MyRequestParams oilParam = new MyRequestParams(
					UserSmsLoginActivity.this);
			oilParam.put("appname", Constants.OilAppTag + "");
			oilParam.put("mobile", tel);
			oilParam.put("vkey", code);
			HttpTool.netRequest(UserSmsLoginActivity.this, oilParam,
					new OnReturnListener() {

						@Override
						public void onReturn(String jsString) {
							// TODO Auto-generated method stub
							try {
								JSONObject obj = new JSONObject(jsString)
										.getJSONObject("data");
								if (obj.getString("login").equals("1")) {

									// loginListener.onSuccess("100", jsString);

									CommonUtil.loginSuccess(jsString,
											UserSmsLoginActivity.this, "main",

											"");
									// 短信登陆成功
								} else {
									// 登录失败
									String errorMessage = obj
											.getString("message");

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, Constants.URL_SMS_LOGIN, false);
			break;
		default:
			break;
		}
	}

	private void getSmsCode() {
		// TODO Auto-generated method stub
		String phone = et_tel.getText().toString();
		RequestParams param = new RequestParams();
		param.put("mobile", phone);
		param.put("tag", 1 + "");
		param.put("appname", Constants.OilAppTag + "");
		HttpTool.netRequestNoCheck(UserSmsLoginActivity.this, param,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						new Clicker(60 * 1000, 1000).start();
					}
				}, Constants.URL_GET_SMS_CODE, false);
	}

	class Clicker extends CountDownTimer {

		public Clicker(long millisInFuture, long countDownInterval) {

			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
			isCodeEffect = false;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			btn_getcode.setText("验证码有效" + millisUntilFinished / 1000 + "秒");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			btn_getcode.setText("获取验证码");
			isCodeEffect = false;
		}
	}

}
