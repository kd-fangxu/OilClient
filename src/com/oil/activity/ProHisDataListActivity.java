package com.oil.activity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;

public class ProHisDataListActivity extends Activity {
	String unitId = "";
	String title = "";
	TextView tv_content1, tv_content2, tv_content3;
	TextView tv_pagetitle;
	ImageView iv_back, iv_linechart;
	TextView tv_time_head, tv_time_end, tv_time_range;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prodata_hislist);
		Intent intent = getIntent();
		unitId = intent.getStringExtra("unitId");
		title = intent.getStringExtra("title");
		time_tag = String.valueOf(System.currentTimeMillis());
		initWeideget();
		initData();
	}

	PullToRefreshListView prListView;

	private void timeSelect(View v) {
		// TODO Auto-generated method stub
		initPopuTimeSelect(v);
	}

	PopupWindow pWindow;

	private void initPopuTimeSelect(final View v) {
		View view = View.inflate(ProHisDataListActivity.this,
				R.layout.view_datapicker, null);
		Button btn_confirm = (Button) view
				.findViewById(R.id.btn_common_confirm);
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pWindow.dismiss();
			}
		});
		DatePicker datePiceker = (DatePicker) view.findViewById(R.id.dp_select);
		datePiceker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar
				.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						// String time = year + "-" + (monthOfYear + 1) + "-"
						// + dayOfMonth;
						// Date date1=new da
						// Log.e("year", year + "");
						@SuppressWarnings("deprecation")
						Date date = new Date(year - 1900, monthOfYear,
								dayOfMonth);
						SimpleDateFormat dateFm = new SimpleDateFormat(
								"yyyy-MM-dd");

						switch (v.getId()) {
						case R.id.tv_time_head:
							tv_time_head.setText(dateFm.format(date));
							break;
						case R.id.tv_time_end:
							tv_time_end.setText(dateFm.format(date));
							break;
						default:
							break;
						}

					}
				});
		pWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pWindow.setFocusable(true);
		pWindow.setBackgroundDrawable(new ColorDrawable(0));
		pWindow.setOutsideTouchable(true);
		pWindow.setAnimationStyle(R.style.popwin_anim_style);
		// pWindow.se(R.layout.view_popu_userfouce);
		// pWindow.set
		// pWindow.setContentView(view);
		pWindow.showAsDropDown(tv_time_head);
		pWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				if (tv_time_head.getText().toString().length() > 0
						&& tv_time_end.getText().toString().length() > 0) {
					prListView.setRefreshing(true);
					currentPage = 1;
					updateData(currentPage);
				}

			}
		});
	}

	private void initWeideget() {
		// TODO Auto-generated method stub
		tv_time_end = (TextView) findViewById(R.id.tv_time_end);
		tv_time_head = (TextView) findViewById(R.id.tv_time_head);
		tv_time_range = (TextView) findViewById(R.id.tv_time_range);
		tv_time_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeSelect(v);
			}

		});
		tv_time_end.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeSelect(v);
			}
		});
		iv_linechart = (ImageView) findViewById(R.id.iv_linechart);
		iv_linechart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userId = OilUser.getCurrentUser(
						ProHisDataListActivity.this).getCuuid();
				Intent intent = new Intent(ProHisDataListActivity.this,
						CommonLineChartActivity.class);
				intent.putExtra("userId", userId);
				intent.putExtra("title", title);
				intent.putExtra("unitId", unitId);
				intent.putExtra("dataList", (Serializable) mapContentList);
				startActivity(intent);
			}
		});
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
		tv_pagetitle.setText(title);
		tv_content1 = (TextView) findViewById(R.id.tv_item_content1);
		tv_content2 = (TextView) findViewById(R.id.tv_item_content2);
		tv_content3 = (TextView) findViewById(R.id.tv_item_content3);
		tv_content1.setText("时间");
		tv_content2.setText("数据");
		tv_content3.setText("单位");
		prListView = (PullToRefreshListView) findViewById(R.id.prlv_news);

		prListView.setMode(Mode.BOTH);

		prListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				if (prListView.getRefreshableView().getLastVisiblePosition() == prListView
						.getRefreshableView().getAdapter().getCount() - 1) {
					// 加载更多
					// Toast.makeText(ProHisDataListActivity.this, "加载",
					// 0).show();
					// 可能有问题
					tv_time_range.setText("");
					currentPage++;
					updateData(currentPage);

				} else {
					// 刷新
					// Toast.makeText(ProHisDataListActivity.this, "刷新",
					// 0).show();
					currentPage = 1;
					updateData(currentPage);
				}
			}
		});

	}

	List<Map<String, Object>> mapContentList = new ArrayList<Map<String, Object>>();
	CommonAdapter<Map<String, Object>> cAdapter;

	private void initData() {
		// TODO Auto-generated method stub
		cAdapter = new CommonAdapter<Map<String, Object>>(
				ProHisDataListActivity.this, mapContentList,
				R.layout.item_data_simple) {

			@Override
			public void convert(CommonViewHolder helper,
					Map<String, Object> item, int positon) {
				// TODO Auto-generated method stub
				TextView tv1 = helper.getView(R.id.tv_item_content1);

				helper.setText(R.id.tv_item_content1, item.get("data_time")
						.toString());
				helper.setText(R.id.tv_item_content2, item.get("unit_value")
						.toString());
				helper.setText(R.id.tv_item_content3, item.get("temp_unit")
						.toString());
			}
		};
		prListView.setAdapter(cAdapter);

		updateData(currentPage);
	}

	private int currentPage = 1;
	String time_tag;

	private void updateData(final int pageIndex) {
		String url = Constants.URL_GETPRODATALIST;
		String userId = OilUser.getCurrentUser(ProHisDataListActivity.this)
				.getCuuid();
		MyRequestParams params = new MyRequestParams(
				ProHisDataListActivity.this);
		if (tv_time_head.getText().toString().length() > 0
				&& tv_time_end.getText().toString().length() > 0) {
			params.put("startTime", tv_time_head.getText().toString());
			params.put("endTime", tv_time_end.getText().toString());
		}
		params.put("userId", userId);
		params.put("unitId", Double.valueOf(unitId).intValue() + "");

		params.put("timetag", time_tag);
		params.put("pageSize", 20 + "");
		params.put("currentPage", pageIndex + "");
		HttpTool.netRequestNoCheck(ProHisDataListActivity.this, params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub

						Gson gson = new Gson();
						ObjectConvertUtils<List<Map<String, Object>>> convertUtils = new ObjectConvertUtils<List<Map<String, Object>>>();
						try {
							if (pageIndex == 1) {
								mapContentList.clear();
							}
							List<Map<String, Object>> temList = convertUtils
									.convert(new JSONObject(jsString)
											.getString("data"));
							for (int i = 0; i < temList.size(); i++) {
								Double data = Double.valueOf(temList.get(i)
										.get("unit_value").toString());
								Float a = Float.valueOf(temList.get(i)
										.get("pro_id").toString()) % 8;
								// Double time_tag
								Double baseData = Double.valueOf(time_tag)
										/ (Math.pow(10, a));
								DecimalFormat df = new DecimalFormat(
										"######0.00");
								temList.get(i).put("unit_value",
										df.format(data - baseData));
							}
							if (pageIndex > 1 && mapContentList.size() > 0
									&& temList.size() > 0) {

								if (mapContentList
										.get(mapContentList.size() - 1)
										.get("data_time")
										.toString()
										.equals(temList.get(temList.size() - 1)
												.get("data_time").toString())) {
									prListView.onRefreshComplete();
									if (mapContentList.size() > 0) {
										tv_time_range.setText(mapContentList
												.get(mapContentList.size() - 1)
												.get("data_time").toString()
												+ "--"
												+ mapContentList.get(0)
														.get("data_time")
														.toString());

									}
									Toast.makeText(ProHisDataListActivity.this,
											"无更多数据", 0).show();

									return;
								}
							}

							mapContentList.addAll(temList);
							if (mapContentList.size() > 0) {
								tv_time_range.setText(mapContentList
										.get(mapContentList.size() - 1)
										.get("data_time").toString()
										+ "--"
										+ mapContentList.get(0)
												.get("data_time").toString());

							}

							cAdapter.notifyDataSetChanged();
							prListView.onRefreshComplete();
							// prListView.getRefreshableView().setSelection(
							// 20 * (pageIndex - 1));

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, url, false);
	}
}
