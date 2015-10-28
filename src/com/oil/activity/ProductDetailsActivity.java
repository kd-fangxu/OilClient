package com.oil.activity;

import java.util.HashMap;
import java.util.Map;

import com.example.oilclient.R;
import com.oil.datamodel.OilProductStrucModel;
import com.oil.fragments.ItemFragDataNew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 *
 * 
 * @author Administrator
 *
 */
public class ProductDetailsActivity extends FragmentActivity implements
		OnClickListener {
	ImageView iv_back, iv_mark;
	String pro_id;
	String chan_id;
	String pro_cn_name;
	String wang_id;
	Map<String, Object> dataMap;
	OilProductStrucModel oilProductStrucModel = new OilProductStrucModel();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_productdetails);
		initView();
		Intent intent = getIntent();
		if (null != intent) {
			pro_id = intent.getStringExtra("pro_id");
			chan_id = intent.getStringExtra("chan_id");
			pro_cn_name = intent.getStringExtra("pro_cn_name");
			// dataMap = oilProductStrucModel.getProItem(pro_id);

			wang_id = oilProductStrucModel.getChanItem(chan_id).get("wang_id")
					.toString();
			// Log.e("ProDetails", pro_id + "  " + chan_id + "   " + wang_id +
			// "");
			initDataFra();

		}

	}

	private void initDataFra() {
		// TODO Auto-generated method stub
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("pro_id", dataConvert(pro_id));
		dataMap.put("chan_id", dataConvert(chan_id));
		dataMap.put("wang_id", dataConvert(wang_id));
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fl_content, new ItemFragDataNew(1, dataMap));
		ft.commit();
	}

	private String dataConvert(String data) {
		return Double.valueOf(data).intValue() + "";
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_mark = (ImageView) findViewById(R.id.iv_mark);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
