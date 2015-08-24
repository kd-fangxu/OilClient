package com.oil.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.MyConfig;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.ToastUtils;

public class ShangjiMatachAct extends Activity implements OnClickListener {
	ImageView iv_back;
	PullToRefreshListView lv_data;
	CommonAdapter<Map<String, Object>> commonAdapter;
	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
	String sdid;
	int pageCount = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_list);
		initView();
		Intent intData = getIntent();
		if (intData != null) {
			sdid = intData.getStringExtra("sdid");
			initData();
		}

	}

	private void initData() {
		// TODO Auto-generated method stub
		updateData();
	}

	private void updateData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		MyRequestParams params = new MyRequestParams(ShangjiMatachAct.this);
		params.put("sdid", sdid);
		params.put("tb", 1 + "");
		params.put("cuuid1", OilUser.getCurrentUser(ShangjiMatachAct.this)
				.getCuuid());
		HttpTool.netRequest(ShangjiMatachAct.this, params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {

						// TODO Auto-generated method stub
						try {
							JSONObject jo = new JSONObject(jsString);
							String data = jo.getJSONObject("data").getString(
									"message");
							// jaArray.
							List<Map<String, Object>> temMapList;
							ObjectConvertUtils<List<Map<String, Object>>> ocUtils = new ObjectConvertUtils<List<Map<String, Object>>>();
							if (pageCount == 1) {
								mapList.clear();

							}
							temMapList = ocUtils.convert(data);
							if (temMapList != null) {
								if (temMapList.size() > 0
										&& mapList.size() > 0
										&& temMapList
												.get(0)
												.get("sdid")
												.toString()
												.equals(mapList.get(0)
														.get("sdid").toString())) {
									ToastUtils.getInstance(
											ShangjiMatachAct.this).showText(
											"无更多内容");

								} else {
									mapList.addAll(temMapList);
								}

							}

							commonAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						lv_data.onRefreshComplete();

					}
				}, MyConfig.MATCH, false);

	}

	private void initView() {
		// TODO Auto-generated method stub
		lv_data = (PullToRefreshListView) findViewById(R.id.lv_data);
		commonAdapter = new CommonAdapter<Map<String, Object>>(
				ShangjiMatachAct.this, mapList, R.layout.item_shangji_data) {

			@Override
			public void convert(CommonViewHolder helper,
					Map<String, Object> item, int Position) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				// helper.setText(R.id.tv_item_content, item.get("cpname")
				// .toString());
				int td = Float.valueOf(item.get("tb").toString()).intValue();
				// helper.setText(R.id.tv_itemName, td == 10 ? "求购" : "供应");

				TextView tv_buType, tv_comName, tv_busInfo, tv_price, tv_updateTime;
				tv_buType = helper.getView(R.id.tv_itemType);
				tv_comName = helper.getView(R.id.tv_item_comName);
				tv_busInfo = helper.getView(R.id.tv_item_proInfo);
				tv_price = helper.getView(R.id.tv_item_price);
				tv_updateTime = helper.getView(R.id.tv_item_upTime);
				if (td == 1) {
					tv_buType
							.setBackgroundResource(R.drawable.selector_item_selcted_bgreen);
					tv_buType.setText("供");
				} else {

					tv_buType
							.setBackgroundResource(R.drawable.selector_selected_textview);
					tv_buType.setText("求");
				}
				String conName = item.get("corpname").toString();
				if (conName != null) {
					tv_comName.setText(conName);

				} else {
					tv_comName.setText("信息未录入");
				}
				StringBuilder sb = new StringBuilder();
				Object pro_name = item.get("cpname");
				Object stock = item.get("stock");
				Object price = item.get("price");
				Object unit = item.get("unit");
				if (pro_name != null && stock != null) {
					sb.append(pro_name.toString() + stock.toString()
							+ (unit == null ? "吨" : unit.toString()));
					tv_busInfo.setText(sb.toString());

				}

				if (price != null) {
					tv_price.setText(price.toString() + "元");
				} else {
					tv_price.setText("面议");
				}
				String upTime = item.get("refreshtime").toString().split(" ")[0];
				if (upTime != null) {
					tv_updateTime.setText(upTime);
				}

			}
		};
		lv_data.setAdapter(commonAdapter);

		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_pageback:
			finish();
			break;

		default:
			break;
		}
	}
}
