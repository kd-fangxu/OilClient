package com.oil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;
import com.oil.bean.OilUser;
import com.oil.bean.OilUser.onRegistListener;

public class UserRegisterActivity extends Activity implements OnClickListener {
	// EditText et_name, et_pwd, et_pwdconfirm;
	private EditText E_userName, E_phone, E_pwd, E_pwdConfirm, E_name,
			E_corpName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uesersignin);
		initWeidget();
	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		// et_name = (EditText) findViewById(R.id.et_login_accountname);
		// et_pwd = (EditText) findViewById(R.id.et_login_accountpwd);
		// et_pwdconfirm = (EditText)
		// findViewById(R.id.et_login_accountpwdconfirm);
		TextView back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		E_userName = (EditText) findViewById(R.id.E_userName);
		E_phone = (EditText) findViewById(R.id.E_phone);
		E_pwd = (EditText) findViewById(R.id.E_pwd);
		E_pwdConfirm = (EditText) findViewById(R.id.E_pwdConfirm);
		E_name = (EditText) findViewById(R.id.E_name);
		E_corpName = (EditText) findViewById(R.id.E_corName);

		Button regist = (Button) findViewById(R.id.regist);
		regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				userSinin();
			}
		});
	}

	private void userSinin() {

		final String userName = E_userName.getText() == null ? "" : E_userName
				.getText() + "".trim();
		if ("".equals(userName)) {
			Toast.makeText(this,
					getResources().getString(R.string.inputAccount),
					Toast.LENGTH_LONG).show();
			return;
		}
		final String phone = E_phone.getText() == null ? "" : E_phone.getText()
				+ "".trim();
		if ("".equals(phone) || phone.length() != 11) {
			Toast.makeText(this,
					getResources().getString(R.string.phoneNumInvalid),
					Toast.LENGTH_LONG).show();
			return;
		}
		final String pass = E_pwd.getText() == null ? "" : E_pwd.getText()
				+ "".trim();
		if ("".equals(pass)) {
			Toast.makeText(this, getResources().getString(R.string.inputPass),
					Toast.LENGTH_LONG).show();
			return;
		}
		if (pass.length() < 6) {
			Toast.makeText(this,
					getResources().getString(R.string.passwordInvalid),
					Toast.LENGTH_LONG).show();
			return;
		}
		String name = E_name.getText() == null ? "" : E_name.getText()
				+ "".trim();
		if ("".equals(name)) {
			Toast.makeText(this, getResources().getString(R.string.inputName),
					Toast.LENGTH_LONG).show();
			return;
		}

		String corpName = E_corpName.getText() == null ? "" : E_corpName
				.getText() + "".trim();
		if ("".equals(corpName)) {
			Toast.makeText(this,
					getResources().getString(R.string.inputCompanyName),
					Toast.LENGTH_LONG).show();
			return;
		}

		String confirmPass = E_pwdConfirm.getText() == null ? "" : E_pwdConfirm
				.getText() + "".trim();
		if (!confirmPass.equals(pass)) {
			Toast.makeText(this,
					getResources().getString(R.string.passwordNotSame),
					Toast.LENGTH_LONG).show();
			return;
		} else {
			OilUser.signIn(this, userName, name, pass, phone, corpName,
					new onRegistListener() {

						@Override
						public void onSuccess(String resCode, String response) {
							// TODO Auto-generated method stub
							finish();
						}

						@Override
						public void onError(String resCode, String errorReason) {
							// TODO Auto-generated method stub
							Toast.makeText(UserRegisterActivity.this,
									errorReason, 0).show();
						}
					});
		}

	}

	@Override
	public void onClick(View v) {
	}

	boolean regState = false;

	@Override
	public void finish() {
		Intent intent = new Intent();
		intent.putExtra("autoLogin", regState);
		intent.putExtra("userAccount", E_phone.getText().toString());
		intent.putExtra("pass", E_pwd.getText().toString());
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		setResult(11, intent);
		super.finish();
	}
}
