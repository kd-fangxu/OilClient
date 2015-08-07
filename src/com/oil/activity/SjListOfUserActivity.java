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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.ToastUtils;

/**
 * 我的供求列表界面
 * 
 * @author Administrator
 *
 */
public class SjListOfUserActivity extends Activity {
	ImageView iv_pageback, iv_add;
	PullToRefreshListView prlv;
	CommonAdapter<Map<String, Object>> commonAdapter;
	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();// 填充数据
	int requestType = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_shangji_user);
		// Intent intent = getIntent();

		initView();

	}

	private void initData() {
		// TODO Auto-generated method stub
		updataData(requestType);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		initData();
		super.onResume();
	}

	private void updataData(int type) {
		// TODO Auto-generated method stub
		String url = Constants.URl_getGqList + type;
		MyRequestParams params = new MyRequestParams(SjListOfUserActivity.this);
		params.put("corpid", 0 + "");

		HttpTool.netRequest(SjListOfUserActivity.this, params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub

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
											SjListOfUserActivity.this)
											.showText("无更多内容");

								} else {
									mapList.addAll(temMapList);
								}

							}

							commonAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						prlv.onRefreshComplete();

					}
				}, url, false);
	}

	int pageCount = 1;

	private void initView() {
		// TODO Auto-generated method stub
		iv_pageback = (ImageView) findViewById(R.id.iv_pageback);
		iv_pageback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv_add = (ImageView) findViewById(R.id.iv_add);
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SjListOfUserActivity.this,
						GqPublicActivity.class));
			}
		});
		prlv = (PullToRefreshListView) findViewById(R.id.prlv_data);

		// TODO Auto-generated method stub
		prlv.setMode(Mode.BOTH);
		prlv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				if (refreshView.getRefreshableView().getLastVisiblePosition() == refreshView
						.getRefreshableView().getAdapter().getCount() - 1) {
					// 加载更多
					pageCount++;
					updataData(requestType);
				} else {
					// 刷新
					pageCount = 1;
					updataData(requestType);
				}

			}
		});
		commonAdapter = new CommonAdapter<Map<String, Object>>(
				SjListOfUserActivity.this, mapList, R.layout.item_shangji_data) {

			@Override
			public void convert(CommonViewHolder helper,
					Map<String, Object> item, int Position) {
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
		prlv.setAdapter(commonAdapter);

		prlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String sdid = Float.valueOf(
						mapList.get(position - 1).get("sdid").toString())
						.intValue()
						+ "";

				Intent intent = new Intent(SjListOfUserActivity.this,
						ShangjiDetailsActivity.class).putExtra("sdid", sdid)
						.putExtra("isUserReq", true);
				;

				startActivity(intent);
			}
		});
		// updateDate();

	}
}
