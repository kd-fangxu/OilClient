package com.oil.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.HotPoint;
import com.oil.datamodel.NewsListPage;
import com.oil.utils.GsonUtils;
import com.oil.utils.StringUtils;

public class BaseListActivity extends Activity {
	public static String TypeKey = "typekey";
	public static String DataSourceKey = "datalist";
	public static int StandardNewsItem = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commonlist);
		initBaseWeidget();
		initWeidget();
	}

	List<Object> beanList = new ArrayList<Object>();
	PullToRefreshListView prListView;
	CommonAdapter<Object> commonAdapter;

	@SuppressWarnings("unchecked")
	private void initWeidget() {
		// TODO Auto-generated method stub
		prListView = (PullToRefreshListView) findViewById(R.id.prlv_news);

		Intent intent = getIntent();
		@SuppressWarnings("unused")
		int itemType = intent.getIntExtra(TypeKey, StandardNewsItem);
		// beanList = (List<Object>) intent.getSerializableExtra(DataSourceKey);

		initDemodata();

		
		prListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BaseListActivity.this,WebViewShowActivity.class);
				Gson gson=new Gson();
				HotPoint hotPoint = gson.fromJson(gson.toJson(commonAdapter.getItem(position)),
						new TypeToken<HotPoint>() {
						}.getType());
				;
			intent.putExtra(WebViewShowActivity.PAGE_URL, hotPoint.getLink());
						startActivity(intent);
			}
		});
	}

	private void initBaseWeidget() {
		// TODO Auto-generated method stub
		ImageView iv_back;
		TextView tv_pagetitle;

		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_pagetitle = (TextView) findViewById(R.id.tv_page_title);
		tv_pagetitle.setText("");
	}

	private void initDemodata() {
		// TODO Auto-generated method stub
		NewsListPage newsListPage;
		try {
			newsListPage = new GsonUtils().getNewsListPage(StringUtils
					.convertStreamToString(getAssets().open("newslist.txt")));
			beanList = newsListPage.getPointList();
			commonAdapter = new CommonAdapter<Object>(BaseListActivity.this,
					beanList, R.layout.item_normalnewsitem) {
				Gson gson = new Gson();

				@Override
				public void convert(CommonViewHolder helper, Object item) {
					// TODO Auto-generated method stub
					HotPoint hotPoint = gson.fromJson(gson.toJson(item),
							new TypeToken<HotPoint>() {
							}.getType());
					Log.e("HotPointInfo", hotPoint.toString());
					helper.setImageByUrl(R.id.iv_item_pic,
							hotPoint.getImagelink());
					helper.setText(R.id.tv_item_title, hotPoint.getTitle());
				}
			};
			prListView.setAdapter(commonAdapter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
