package com.oil.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.example.oilclient.R;
import com.oil.bean.Constants;
import com.oil.event.FinishDialog;

import de.greenrobot.event.EventBus;

public class NetworkDialog extends Activity {
	private Button set, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.network_dia);

		Constants.isNetWorkDialogExist = true;
		set = (Button) findViewById(R.id.setting);
		cancel = (Button) findViewById(R.id.cancel);

		set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = null;
				intent = new Intent();
				intent.setClassName("com.android.settings",
						"com.android.settings.Settings");
				intent.setAction("android.intent.action.VIEW");
				startActivity(intent);

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		EventBus.getDefault().register(this);
	}

	public void onEvent(FinishDialog event) {
		finish();
	}

	@Override
	protected void onDestroy() {
		Constants.isNetWorkDialogExist = false;
		super.onDestroy();
	}
}
