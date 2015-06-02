package com.oil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.example.oilclient.R;
import com.oil.bean.OilUser;
import com.oil.bean.OilUser.onRegistListener;
import com.oil.utils.ToastUtils;

public class UserRegisterActivity extends Activity implements OnClickListener {
	EditText et_name, et_pwd, et_pwdconfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userregister);
		initWeidget();
	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.et_login_accountname);
		et_pwd = (EditText) findViewById(R.id.et_login_accountpwd);
		et_pwdconfirm = (EditText) findViewById(R.id.et_login_accountpwdconfirm);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_register:

			String name = et_name.getText().toString();
			String pwd = et_pwd.getText().toString();
			if (!name.equals("") && !pwd.equals("")) {
				OilUser.signIn(name,pwd,new onRegistListener() {

					@Override
					public void onSuccess(String resCode, String response) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(String resCode, String errorReason) {
						// TODO Auto-generated method stub

					}
				});
			} else {
				ToastUtils.getInstance(UserRegisterActivity.this).showText(
						"用户信息不完整");
			}
			break;

		default:
			break;
		}
	}
}
