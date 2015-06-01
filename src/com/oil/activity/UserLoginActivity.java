package com.oil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.example.oilclient.R;
import com.oil.bean.OilUser;
import com.oil.bean.OilUser.onLoginListener;
import com.oil.utils.ToastUtils;

public class UserLoginActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userlogin);
		initWeidget();
	}

	EditText et_name, et_pwd;
	TextView tv_wechatlogin, tv_losspwd;
	Button btn_register, btn_login;

	private void initWeidget() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.et_login_accountname);
		et_pwd = (EditText) findViewById(R.id.et_login_accountpwd);
		tv_wechatlogin = (TextView) findViewById(R.id.tv_wechatlogin);
		tv_losspwd = (TextView) findViewById(R.id.tv_losspwd);
		btn_login = (Button) findViewById(R.id.btn_Login);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_Login:
			String name = et_name.getText().toString();
			String pwd = et_pwd.getText().toString();
			if (!name.equals("") && !pwd.equals("")) {
				OilUser oilUser = new OilUser(name, pwd);
				oilUser.Login(UserLoginActivity.this, new onLoginListener() {

					@Override
					public void onSuccess(String resCode, String response) {
						// TODO Auto-generated method stub

						startActivity(new Intent(UserLoginActivity.this,
								MainActivity.class));
						finish();
					}

					@Override
					public void onError(String resCode, String errorReason) {
						// TODO Auto-generated method stub
						ToastUtils.getInstance(UserLoginActivity.this)
								.showText(errorReason);
					}
				});
			}

			break;
		case R.id.btn_register:
			startActivity(new Intent(UserLoginActivity.this,
					UserRegisterActivity.class));
			break;
		default:
			break;
		}
	}
}
