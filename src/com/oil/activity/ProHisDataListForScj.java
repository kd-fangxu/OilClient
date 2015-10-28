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

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.ToastUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 国内市场价 （高中低价格界面）
 * 
 * @author xu
 *
 */
public class ProHisDataListForScj extends Activity {
	private ArrayList<Map<String, Object>> unitList;
	List<Map<String, Object>> GdList, ZlList, DdList;
	boolean isGsLoaded, isDdLoaded, isZlLoaded;// 数据加载结束标志
	PullToRefreshListView lv_data;
	String timeTag;
	int pageIndex = 1;
	CommonAdapter<Map<String, Object>> commonAdapter;
	TextView tv_time_head, tv_time_end, tv_time_range;
	TextView tv_pagetitle;
	ImageView iv_back, iv_linechart;
	String currentProId;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_prodatalist_scj);
		Intent intent = getIntent();
		if (intent != null) {
			unitList = (ArrayList<Map<String, Object>>) intent.getSerializableExtra("unitList");
			if (unitList != null) {
				if (unitList.size() == 3) {
					initView();
				} else {
					finish();// 无效数据
				}

			}
			String title = intent.getStringExtra("title");
			currentProId = intent.getStringExtra("proId");
			tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
			tv_pagetitle.setText(title);
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_linechart = (ImageView) findViewById(R.id.iv_linechart);
		iv_linechart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String userId = OilUser.getCurrentUser(ProHisDataListForScj.this).getCuuid();
				Intent intent = new Intent(ProHisDataListForScj.this, CommonMultiLineChartActivity.class);
				intent.putExtra("userId", userId);
				// intent.putExtra("title", title);
				// intent.putExtra("unitId", unitId);
				intent.putExtra("dataList", (Serializable) ZlList);
				// intent.putex
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
		timeTag = String.valueOf(System.currentTimeMillis());
		lv_data = (PullToRefreshListView) findViewById(R.id.lv_data);
		lv_data.setMode(Mode.BOTH);
		lv_data.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (lv_data.getRefreshableView().getLastVisiblePosition() == lv_data.getRefreshableView().getAdapter()
						.getCount()) {
					// jiazai
					pageIndex++;
					upDateData();
				} else {
					pageIndex = 1;
					upDateData();
				}
			}
		});
		initData();
	}

	private void timeSelect(View v) {
		// TODO Auto-generated method stub
		initPopuTimeSelect(v);
	}

	PopupWindow pWindow;

	private void initPopuTimeSelect(final View v) {
		View view = View.inflate(ProHisDataListForScj.this, R.layout.view_datapicker, null);
		Button btn_confirm = (Button) view.findViewById(R.id.btn_common_confirm);
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pWindow.dismiss();
			}
		});
		DatePicker datePiceker = (DatePicker) view.findViewById(R.id.dp_select);
		datePiceker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

					@SuppressLint("SimpleDateFormat")
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						// String time = year + "-" + (monthOfYear + 1) + "-"
						// + dayOfMonth;
						// Date date1=new da
						// Log.e("year", year + "");
						@SuppressWarnings("deprecation")
						Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
						SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");

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
		pWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
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
				if (tv_time_head.getText().toString().length() > 0 && tv_time_end.getText().toString().length() > 0) {
					lv_data.setRefreshing(true);
					pageIndex = 1;
					// updateData();u
					upDateData();
				}

			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		GdList = new ArrayList<Map<String, Object>>();
		ZlList = new ArrayList<Map<String, Object>>();
		DdList = new ArrayList<Map<String, Object>>();
		// String zLUnitId;
		commonAdapter = new CommonAdapter<Map<String, Object>>(ProHisDataListForScj.this, ZlList,
				R.layout.itemdata_for_chj) {
			@Override
			public void convert(CommonViewHolder helper, Map<String, Object> item, int Position) {
				// TODO Auto-generated method stub
				helper.setText(R.id.tv_item_content1, item.get("data_time").toString());
				helper.setText(R.id.tv_item_content2, item.get("unit_value_zl").toString());

				helper.setText(R.id.tv_item_content3,
						item.get("unit_value_gd") != null ? item.get("unit_value_gd").toString() : "无数据");
				helper.setText(R.id.tv_item_content4,
						item.get("unit_value_dd") != null ? item.get("unit_value_dd").toString() : "无数据");
			}
		};
		lv_data.getRefreshableView().setAdapter(commonAdapter);
		upDateData();

	}

	private void upDateData() {
		isDdLoaded = false;
		isGsLoaded = false;
		isZlLoaded = false;
		for (int i = 0; i < unitList.size(); i++) {
			if (unitList.get(i).get("unit_name").toString().contains("主流价")) {
				String unitId = unitList.get(i).get("unit_id").toString();
				getZlData(Float.valueOf(unitId).intValue() + "");
			} else if (unitList.get(i).get("unit_name").toString().contains("低端价")) {
				String unitId = unitList.get(i).get("unit_id").toString();
				getDdData(Float.valueOf(unitId).intValue() + "");
			} else if (unitList.get(i).get("unit_name").toString().contains("高端价")) {
				String unitId = unitList.get(i).get("unit_id").toString();
				getGdData(Float.valueOf(unitId).intValue() + "");
			}
		}
	}

	/**
	 * 获取高端价
	 * 
	 * @param unitId
	 */
	private void getGdData(String unitId) {
		// TODO Auto-generated method stub

		String url = Constants.URL_GETPRODATALIST;
		String userId = OilUser.getCurrentUser(ProHisDataListForScj.this).getCuuid();
		MyRequestParams params = new MyRequestParams(ProHisDataListForScj.this);
		if (tv_time_head.getText().toString().length() > 0 && tv_time_end.getText().toString().length() > 0) {
			params.put("startTime", tv_time_head.getText().toString());
			params.put("endTime", tv_time_end.getText().toString());
		}
		params.put("userId", userId);
		params.put("unitId", Double.valueOf(unitId).intValue() + "");
		params.put("proId", currentProId);
		params.put("timetag", timeTag);
		params.put("pageSize", 20 + "");
		params.put("currentPage", pageIndex + "");
		HttpTool.netRequestNoCheck(ProHisDataListForScj.this, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				if (pageIndex == 1) {
					GdList.clear();
				}
				ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
				try {
					isGsLoaded = true;
					JSONObject jo = new JSONObject(jsString);
					if (!jo.getString("status").equals("1")) {
						ToastUtils.getInstance(ProHisDataListForScj.this).showText(jo.getString("reason"));
					} else {
						GdList.addAll(ocu.convert(jo.getString("data")));
					}

					onDataLoaded();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, url, false);

	}

	/**
	 * 获取低端价
	 * 
	 * @param unitId
	 */
	private void getDdData(String unitId) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String url = Constants.URL_GETPRODATALIST;
		String userId = OilUser.getCurrentUser(ProHisDataListForScj.this).getCuuid();
		MyRequestParams params = new MyRequestParams(ProHisDataListForScj.this);
		if (tv_time_head.getText().toString().length() > 0 && tv_time_end.getText().toString().length() > 0) {
			params.put("startTime", tv_time_head.getText().toString());
			params.put("endTime", tv_time_end.getText().toString());
		}
		params.put("userId", userId);
		params.put("unitId", Double.valueOf(unitId).intValue() + "");
		params.put("proId", currentProId);
		params.put("timetag", timeTag);
		params.put("pageSize", 20 + "");
		params.put("currentPage", pageIndex + "");
		HttpTool.netRequestNoCheck(ProHisDataListForScj.this, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {

				if (pageIndex == 1) {
					DdList.clear();
				}
				ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
				try {
					isDdLoaded = true;
					JSONObject jo = new JSONObject(jsString);
					if (!jo.getString("status").equals("1")) {
						ToastUtils.getInstance(ProHisDataListForScj.this).showText(jo.getString("reason"));
					} else {
						GdList.addAll(ocu.convert(jo.getString("data")));
					}
					onDataLoaded();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, url, false);

	}

	/**
	 * 获取主流价
	 * 
	 * @param unitId
	 */
	private void getZlData(String unitId) {
		// TODO Auto-generated method stub

		String url = Constants.URL_GETPRODATALIST;
		String userId = OilUser.getCurrentUser(ProHisDataListForScj.this).getCuuid();
		MyRequestParams params = new MyRequestParams(ProHisDataListForScj.this);
		if (tv_time_head.getText().toString().length() > 0 && tv_time_end.getText().toString().length() > 0) {
			params.put("startTime", tv_time_head.getText().toString());
			params.put("endTime", tv_time_end.getText().toString());
		}
		params.put("userId", userId);
		params.put("unitId", Double.valueOf(unitId).intValue() + "");
		params.put("proId", currentProId);
		params.put("timetag", timeTag);
		params.put("pageSize", 20 + "");
		params.put("currentPage", pageIndex + "");
		HttpTool.netRequestNoCheck(ProHisDataListForScj.this, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {

				if (pageIndex == 1) {
					ZlList.clear();
				}
				ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
				try {
					isZlLoaded = true;
					JSONObject jo = new JSONObject(jsString);
					if (!jo.getString("status").equals("1")) {
						ToastUtils.getInstance(ProHisDataListForScj.this).showText(jo.getString("reason"));
					} else {
						GdList.addAll(ocu.convert(jo.getString("data")));
					}
					onDataLoaded();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, url, false);

	}

	/**
	 * 将主流价 高端价 低端机 按主流价时间 注入到主流价 数据列中
	 */
	private void onDataLoaded() {
		if (isDdLoaded && isGsLoaded && isZlLoaded) {
			for (int i = 0; i < ZlList.size(); i++) {
				String dataTime = ZlList.get(i).get("data_time").toString();
				ZlList.get(i).put("unit_value_zl",
						unLock(ZlList.get(i).get("unit_value").toString(), ZlList.get(i).get("pro_id").toString()));
				for (int j = 0; j < GdList.size(); j++) {
					if (dataTime.equals(GdList.get(j).get("data_time").toString())) {
						ZlList.get(i).put("unit_value_gd", unLock(GdList.get(j).get("unit_value").toString(),
								GdList.get(j).get("pro_id").toString()));
						break;
					}
				}

				for (int j = 0; j < DdList.size(); j++) {
					if (dataTime.equals(DdList.get(j).get("data_time").toString())) {
						ZlList.get(i).put("unit_value_dd", unLock(DdList.get(j).get("unit_value").toString(),
								DdList.get(j).get("pro_id").toString()));
						break;
					}
				}
			}

			tv_time_range.setText(ZlList.get(ZlList.size() - 1).get("data_time").toString() + "--"
					+ ZlList.get(0).get("data_time").toString());

			commonAdapter.notifyDataSetChanged();
			lv_data.onRefreshComplete();
		}
	}

	/**
	 * 还原数值
	 * 
	 * @param unitValue
	 * @return
	 */
	@SuppressWarnings("unused")
	private String unLock(String unitValue, String proId) {
		Double data = Double.valueOf(unitValue);
		Float a = Float.valueOf(proId) % 8;
		// Double time_tag
		Double baseData = Double.valueOf(timeTag) / (Math.pow(10, a));
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(data - baseData);
	}

}
