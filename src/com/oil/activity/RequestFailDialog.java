package com.oil.activity;

import com.example.oilclient.R;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class RequestFailDialog extends Activity {

	private Button connect, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.request_fail_dialog);

		Constants.isRequestFailDialogExist = true;
		connect = (Button) findViewById(R.id.connectService);
		cancel = (Button) findViewById(R.id.cancel);

		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri;
				uri = Uri.parse("tel:0533-2591688");
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				startActivity(intent);
				OilUser.logOut(RequestFailDialog.this);
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		Constants.isRequestFailDialogExist = false;
		super.onDestroy();
	}

}
