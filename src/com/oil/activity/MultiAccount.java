package com.oil.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.event.LoginSuccessEvent;
import com.oil.inter.OnReturnListener;
import com.oil.utils.CommonUtil;
import com.oil.utils.HttpTool;

import de.greenrobot.event.EventBus;

public class MultiAccount extends Activity { // ���û���¼����
	private GridView accountGV;
	private ArrayList<OilUser> accountList;
	private MyAdapter adapter;
	private String destination;
	private String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.multi_account);

		Intent intent = getIntent();
		accountList = (ArrayList<OilUser>) intent
				.getSerializableExtra("accountList");
		destination = intent.getStringExtra("destination");
		tag = intent.getStringExtra("tag");

		accountGV = (GridView) findViewById(R.id.accountGV);
		adapter = new MyAdapter();
		accountGV.setAdapter(adapter);

		accountGV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				MyRequestParams params = new MyRequestParams(MultiAccount.this);
				params.put("cuuid", accountList.get(position).getCuuid());
				params.put("tag", tag);

				HttpTool.netRequest(MultiAccount.this, params,
						new OnReturnListener() {

							@Override
							public void onReturn(String jsString) {
								try {
									JSONObject obj = new JSONObject(jsString)
											.getJSONObject("data");
									if (obj.getString("login").equals("1")) {
										Toast.makeText(
												MultiAccount.this,
												getResources().getText(
														R.string.loginSuccess),
												1).show();
										CommonUtil.saveUserInfo(
												MultiAccount.this,
												obj.getString("accessToken"),
												obj.getString("timestamp"),
												accountList.get(position),
												destination);

									} else {
										Toast.makeText(MultiAccount.this,
												obj.getString("message"), 1)
												.show();
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}, Constants.MULTI_ACCOUNT_LOGIN, true);

			}
		});

		EventBus.getDefault().register(this);
	}

	public void onEvent(LoginSuccessEvent event) {
		finish();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return accountList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return accountList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				convertView = View.inflate(MultiAccount.this,
						R.layout.multi_account_item, null);
				vh = new ViewHolder();
				vh.userName = (TextView) convertView
						.findViewById(R.id.userName);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.userName.setText(accountList.get(position).getUserName());
			return convertView;
		}

	}

	class ViewHolder {
		TextView userName;
	}
}
