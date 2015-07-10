package com.oil.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.oilclient.R;
import com.oil.bean.OilUser;
import com.oil.iface.OnCommonBusListener;
import com.oil.utils.ToastUtils;

public class UserResetPwdActivity extends Activity implements OnClickListener {
	EditText et_oldpwd, et_newpwd, et_confirmpwd;
	Button btn_confirm;
	ImageView iv_oldPwd, iv_newPwd, iv_conPwd;
	boolean isOldPwdRight, isNewPwdRight, isConfirmPwdRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userresetpwd);
		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		OilUser oilUser = OilUser.getCurrentUser(UserResetPwdActivity.this);
		final String pwd = oilUser.getAccountPwd();
		et_oldpwd = (EditText) findViewById(R.id.et_oldpwd);
		et_newpwd = (EditText) findViewById(R.id.et_newpwd);
		et_confirmpwd = (EditText) findViewById(R.id.et_confirmpwd);
		iv_oldPwd = (ImageView) findViewById(R.id.iv_oldpwd);
		iv_newPwd = (ImageView) findViewById(R.id.iv_newpwd);
		iv_conPwd = (ImageView) findViewById(R.id.iv_confirmpwd);
		et_oldpwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stu

			}

			@SuppressLint("NewApi")
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().equals(pwd)) {
					iv_oldPwd.setImageResource(R.drawable.icon_right);
					isOldPwdRight = true;
				} else {
					iv_oldPwd.setImageResource(R.drawable.icon_wrong);

					isOldPwdRight = false;
				}
			}
		});
		et_newpwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@SuppressLint("NewApi")
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().length() < 6) {
					iv_newPwd.setImageResource(R.drawable.icon_wrong);

					isNewPwdRight = false;
				} else {
					iv_newPwd.setImageResource(R.drawable.icon_right);

					isNewPwdRight = true;
				}
				if (et_confirmpwd.getText().toString().equals(s.toString())) {
					iv_conPwd.setImageResource(R.drawable.icon_right);

					isConfirmPwdRight = true;
				} else {
					iv_conPwd.setImageResource(R.drawable.icon_wrong);
					isConfirmPwdRight = false;
				}
			}
		});

		et_confirmpwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@SuppressLint("NewApi")
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().equals(et_newpwd.getText().toString())) {
					iv_conPwd.setImageResource(R.drawable.icon_right);
					isConfirmPwdRight = true;
				} else {

					iv_conPwd.setImageResource(R.drawable.icon_wrong);
					isConfirmPwdRight = false;
				}
			}
		});
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_confirm:
			if (isConfirmPwdRight && isOldPwdRight && isNewPwdRight) {
				OilUser.changeUserPwd(UserResetPwdActivity.this, et_newpwd
						.getText().toString(), new OnCommonBusListener() {

					@Override
					public void onSucceed(String msg) {
						// TODO Auto-generated method stub
						ToastUtils.getInstance(UserResetPwdActivity.this)
								.showText(msg);
					}

					@Override
					public void onFailed(String msg) {
						// TODO Auto-generated method stub
						ToastUtils.getInstance(UserResetPwdActivity.this)
								.showText(msg);
					}
				});
			}
			break;

		default:
			break;
		}
	}
}
