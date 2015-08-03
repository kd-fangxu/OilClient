package com.oil.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ToastUtils;

public class ShangjiDetailsActivity extends Activity implements OnClickListener {
	ImageView iv_back, iv_more;
	TextView tv_proInfo, tv_connInfo;
	PopupMenu pMenu;
	String sdid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shangjidetail);
		sdid = getIntent().getStringExtra("sdid");
		initView();
		initData();
	}

	StringBuilder sb_pro, sb_conn;

	private void initData() {
		// TODO Auto-generated method stub
		if (sdid != null) {
			MyRequestParams params = new MyRequestParams(
					ShangjiDetailsActivity.this);
			params.put("sdid", sdid);
			HttpTool.netRequest(ShangjiDetailsActivity.this, params,
					new OnReturnListener() {

						@Override
						public void onReturn(String jsString) {
							// TODO Auto-generated method stub
							try {
								JSONObject jObject = (JSONObject) new JSONObject(
										jsString).getJSONObject("data")
										.getJSONArray("message").get(0);
								sb_pro = new StringBuilder();
								sb_pro.append("��Ʒ���ƣ�"
										+ jObject.getString("cpname") + "\n"
										+ "������ͣ�" + jObject.getString("model")
										+ "\n" + "������"
										+ jObject.getString("stock") + "\n"
										+ "�۸�" + jObject.getString("price"));
								tv_proInfo.setText(sb_pro.toString());
								sb_conn = new StringBuilder();
								sb_conn.append("��ϵ�ˣ�"
										+ jObject.getString("name") + "\n"
										+ "������ҵ��"
										+ jObject.getString("corpname") + "\n"
										+ "ʡ�ݣ�" + jObject.getString("province")
										+ "\n" + "��ע��"
										+ jObject.getString("remark"));
								tv_connInfo.setText(sb_conn.toString());

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}, Constants.URL_getGQ, false);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_proInfo = (TextView) findViewById(R.id.tv_prodetails);
		tv_connInfo = (TextView) findViewById(R.id.tv_conn);
		initPoputMenu();
	}

	private void initPoputMenu() {
		// TODO Auto-generated method stub
		pMenu = new PopupMenu(ShangjiDetailsActivity.this, iv_more);
		pMenu.getMenuInflater().inflate(R.menu.order_details, pMenu.getMenu());
		pMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.item_share:
					// ToastUtils.getInstance(ShangjiDetailsActivity.this)
					// .showText("����");
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("image/*");
					intent.putExtra(Intent.EXTRA_SUBJECT, "����");
					intent.putExtra(Intent.EXTRA_TEXT, sb_pro.toString() + "\n"
							+ sb_conn.toString() + "\n"
							+ "���ԡ���¡����Ѷ www.oilchem.net");
					intent.putExtra(Intent.EXTRA_TITLE, "����");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(intent, getTitle()));
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_pageback:
			finish();
			break;
		case R.id.iv_more:
			pMenu.show();
			break;
		default:
			break;
		}
	}
}
