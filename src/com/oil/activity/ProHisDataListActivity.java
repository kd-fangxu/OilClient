package com.oil.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity.Header;
import android.widget.ListView;
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
import com.oil.bean.OilUser;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;

public class ProHisDataListActivity extends Activity {
	String unitId = "";
	TextView tv_content1, tv_content2, tv_content3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prodata_hislist);
		Intent intent = getIntent();
		unitId = intent.getStringExtra("unitId");
		initWeideget();
		initData();
	}

	PullToRefreshListView prListView;

	private void initWeideget() {
		// TODO Auto-generated method stub
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

	private void updateData(final int pageIndex) {
		String url = Constants.URL_GETPRODATALIST;
		String userId = OilUser.getCurrentUser(ProHisDataListActivity.this)
				.getCuuid();
		RequestParams params = new RequestParams();
		params.put("userId", userId);
		params.put("unitId", unitId);
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

							mapContentList.addAll(convertUtils
									.convert(new JSONObject(jsString)
											.getString("data")));
							cAdapter.notifyDataSetChanged();
							prListView.onRefreshComplete();
							prListView.getRefreshableView().setSelection(
									20 * (pageIndex - 1));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, url, false);
	}
}
