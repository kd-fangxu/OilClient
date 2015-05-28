package com.oil.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.example.oilclient.R;
import com.oil.bean.OilUser;

public class FirstPageShowActivity extends Activity {
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstpage);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:
					if (null != OilUser
							.getCurrentUser(FirstPageShowActivity.this)) {
						startActivity(new Intent(FirstPageShowActivity.this,
								MainActivity.class));
					} else {
						startActivity(new Intent(FirstPageShowActivity.this,
								UserLoginActivity.class));
					}
//					startActivity(new Intent(FirstPageShowActivity.this,
//							MainActivity.class));
					finish();
					break;

				default:
					break;
				}
			}
		};
		init();
	}

	Timer timer;

	private void init() {
		// TODO Auto-generated method stub
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		}, 2000);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			timer.cancel();
			finish();
			break;

		default:
			break;
		}
		return true;
	}
}
