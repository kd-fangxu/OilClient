package com.oil.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.bean.OilUser.onLoginListener;
import com.oil.dialogs.CommontitleDialog;
import com.oil.dialogs.CommontitleDialog.onComDialogBtnClick;
import com.oil.event.LoginSuccessEvent;
import com.oil.utils.CommonUtil;
import com.oil.utils.ToastUtils;

import de.greenrobot.event.EventBus;

public class UserLoginActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userlogin);
		initWeidget();

		EventBus.getDefault().register(this);
	}

	public void onEvent(LoginSuccessEvent event) {
		finish();
	}

	EditText et_name, et_pwd;
	TextView tv_smslogin, tv_losspwd;
	Button btn_register, btn_login;
	SharedPreferences mySharedPreferences;

	private void initWeidget() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.et_login_accountname);
		et_pwd = (EditText) findViewById(R.id.et_login_accountpwd);
		tv_smslogin = (TextView) findViewById(R.id.tv_smslogin);
		tv_losspwd = (TextView) findViewById(R.id.tv_losspwd);
		btn_login = (Button) findViewById(R.id.btn_Login);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		tv_smslogin.setOnClickListener(this);
		mySharedPreferences = getSharedPreferences(Constants.USER_INFO_SHARED,
				Activity.MODE_PRIVATE);
		et_name.setText(mySharedPreferences.getString(Constants.USER_PHONE, ""));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_Login:
			doLogin();

			break;
		case R.id.btn_register:
			startActivityForResult(new Intent(UserLoginActivity.this,
					UserRegisterActivity.class), 1);

			break;
		case R.id.tv_smslogin:
			startActivity(new Intent(UserLoginActivity.this,
					UserSmsLoginActivity.class));
			// finish();
			break;
		default:
			break;
		}
	}

	private void doLogin() {
		String name = et_name.getText().toString();
		final String pwd = et_pwd.getText().toString();
		if (!name.equals("") && !pwd.equals("")) {
			OilUser.Login(UserLoginActivity.this, name, pwd,
					new onLoginListener() {

						@Override
						public void onSuccess(String resCode, String response) {
							// TODO Auto-generated method stub
							CommonUtil.loginSuccess(response,
									UserLoginActivity.this, "main", "");
							Editor editor = mySharedPreferences.edit();
							editor.putString(Constants.USER_PWD, pwd);
							editor.commit();
						}

						@Override
						public void onError(String resCode, String errorReason) {
							// TODO Auto-generated method stub
							ToastUtils.getInstance(UserLoginActivity.this)
									.showText(errorReason);
							final CommontitleDialog comDialog = new CommontitleDialog(
									UserLoginActivity.this);
							comDialog.Init("提示", "是否通过短信登陆",
									new onComDialogBtnClick() {

										@Override
										public void onConfirmClick() {
											// TODO Auto-generated method stub
											startActivity(new Intent(
													UserLoginActivity.this,
													UserSmsLoginActivity.class));
											// finish();
										}

										@Override
										public void onCancelClick() {
											// TODO Auto-generated method stub
											comDialog.disMiss();
										}
									});
						}
					});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:

			if (data.getBooleanExtra("autoLogin", false)) {// 注册成功，自动登录
				et_name.setText(data.getStringExtra("userAccount"));
				et_pwd.setText(data.getStringExtra("pass"));
				doLogin();
			}

			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
