package com.oil.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.etsy.android.grid.StaggeredGridView;
import com.example.oilclient.R;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oil.adapter.HeadPageAdapter;
import com.oil.adapter.StaggeredAdapter;
import com.oil.bean.HotPoint;
import com.oil.bean.NewsClumns;
import com.oil.datamodel.MainPage;
import com.oil.utils.GsonParserFactory;
import com.oil.utils.StringUtils;
import com.oil.weidget.HotHeaderPager;

public class TabFragmentMainPage extends Fragment {
	View view;
	RelativeLayout rl_loading;
	StaggeredGridView sgv_show;
	List<HotPoint> hotList = new ArrayList<HotPoint>();
	// List<View> viewList = new ArrayList<View>();
	List<View> headviewList = new ArrayList<View>();
	HeadPageAdapter hpAdapter;
	HashMap<String, Object> hotPoingMap;
	HotHeaderPager hPager;
	StaggeredAdapter sAdapter;
	List<NewsClumns> newsList = new ArrayList<NewsClumns>();

	public TabFragmentMainPage() {
	};

	public static TabFragmentMainPage getInstance() {

		return new TabFragmentMainPage();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_tab_first, container,
					false);
		}
		initImageLoader();
		initWeidget(view);
		return view;
	}

	private void initWeidget(View view) {
		// TODO Auto-generated method stub
		rl_loading = (RelativeLayout) view.findViewById(R.id.rt_loading);
		sgv_show = (StaggeredGridView) view.findViewById(R.id.sgv_show);
		sAdapter = new StaggeredAdapter(getActivity(), newsList);
		hPager = new HotHeaderPager(getActivity());
		hpAdapter = new HeadPageAdapter(headviewList, getActivity());
		hPager.init(hpAdapter);
		sgv_show.addHeaderView(hPager);
		sgv_show.setAdapter(sAdapter);
		initHeadView();
		// initData();
		initDemoData();

	}

	DisplayImageOptions options;
	ImageLoader imageLoader;

	private void initImageLoader() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.icon_pic_default)
				.showStubImage(R.drawable.icon_pic_default).cacheInMemory(true)
				.cacheOnDisc(true).build();
		imageLoader = ImageLoader.getInstance();

	}

	private void initHeadView() {
		// TODO Auto-generated method stub
		HotPoint hPoint = new HotPoint();
		hPoint.setImagelink("");
		hPoint.setLink("");
		hPoint.setTitle("无推荐信息");
		hotList.add(hPoint);
		updateHeadview(true);
	}

	private void updateHeadview(boolean isFirstInit) {
		if (!isFirstInit) {
			headviewList.clear();
		}
		for (int i = 0; i < hotList.size(); i++) {

			ImageView iv_content = new ImageView(getActivity());
			iv_content.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			iv_content.setScaleType(ScaleType.FIT_XY);
			// iv_content.setImageResource(R.drawable.icon_default);
			imageLoader.displayImage(hotList.get(i).getImagelink(), iv_content,
					options);
			headviewList.add(iv_content);
		}
		hpAdapter.notifyDataSetChanged();
	}

	// private void initData() {
	// // TODO Auto-generated method stub
	// List<String> contentList = new ArrayList<String>();
	// contentList.add("公司介绍");
	// contentList.add("企业文化");
	// contentList.add("企業荣誉");
	// contentList.add("产品体系");
	// sAdapter = new StaggeredAdapter(getActivity(), contentList);
	//
	// HotPoint hPoint = new HotPoint();
	// hPoint.setTitle("title");
	// hPoint.setImagelink("aaa");
	// hPoint.setLink("aaaa");
	// hotList.add(hPoint);
	// hotList.add(hPoint);
	// hotList.add(hPoint);
	// hotList.add(hPoint);
	// // PagerHotAdapter phAdapter = new PagerHotAdapter(getFragmentManager(),
	// // hotList);
	//
	// HotHeaderPager hPager = new HotHeaderPager(getActivity());
	// // View viewItem = View.inflate(getActivity(),
	// // R.layout.fragment_hotpoint,
	// // null);
	// for (int i = 0; i < hotList.size(); i++) {
	//
	// ImageView iv_content = new ImageView(getActivity());
	// iv_content.setLayoutParams(new LayoutParams(
	// LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	// iv_content.setScaleType(ScaleType.FIT_XY);
	// iv_content.setImageResource(R.drawable.icon_default);
	// viewList.add(iv_content);
	// }
	// hpAdapter = new HeadPageAdapter(viewList, getActivity());
	// hPager.init(hpAdapter);
	//
	// sgv_show.addHeaderView(hPager);
	// sgv_show.setAdapter(sAdapter);
	// }

	@SuppressWarnings("unchecked")
	private void initDemoData() {
		// TODO Auto-generated method stub
		try {
			Gson gson = new Gson();
			String content = StringUtils.convertStreamToString(getActivity()
					.getAssets().open("mainpage.txt"));
			GsonParserFactory gUtils = new GsonParserFactory();
			MainPage mPage = gUtils.converMainPage(content);
			newsList.clear();
			newsList.addAll(mPage.getNewsList());

			hotPoingMap = mPage.getHotPointMap();
			hotList.clear();
			hotList.addAll(mPage.getHotList());

			updateHeadview(false);
			sAdapter.notifyDataSetChanged();
			rl_loading.setVisibility(View.INVISIBLE);
			// gUtils.getHotPoints(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		((ViewGroup) view.getParent()).removeView(view);
	}
}
