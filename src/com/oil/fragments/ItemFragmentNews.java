package com.oil.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.activity.BaseListActivity;
import com.oil.activity.MainActivity;
import com.oil.adapter.HeadPageAdapter;
import com.oil.adapter.NewsClumnAdapter;
import com.oil.bean.HotPoint;
import com.oil.bean.NewsClumns;
import com.oil.datamodel.InfoPage;
import com.oil.utils.GsonUtils;
import com.oil.utils.StringUtils;
import com.oil.weidget.HotHeaderPager;

public class ItemFragmentNews extends Fragment {
	List<NewsClumns> newList = new ArrayList<NewsClumns>();
	List<HotPoint> hotList = new ArrayList<HotPoint>();
	PullToRefreshListView pslv;
	RelativeLayout rl_loading;
	NewsClumnAdapter nClumnAdapter;
	HeadPageAdapter hpAdapter;
	List<View> viewList = new ArrayList<View>();
	private static ItemFragmentNews iFragment;

	public ItemFragmentNews() {
	}

	public static ItemFragmentNews getInstance() {

		iFragment = new ItemFragmentNews();

		return iFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_newsitem,
				null);
		((MainActivity) getActivity()).getSlidingMenu().addIgnoredView(view);
		pslv = (PullToRefreshListView) view.findViewById(R.id.prlv_news);
		rl_loading = (RelativeLayout) view.findViewById(R.id.rl_loading);
		initDemoData();
		// initData();

		pslv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), BaseListActivity.class));
				;
			}
		});
		return view;
	}

	InfoPage infoPage = null;
	HotHeaderPager hPager;

	private void initDemoData() {
		// TODO Auto-generated method stub
		try {
			infoPage = new GsonUtils().getInfoPage(StringUtils
					.convertStreamToString(getActivity().getAssets().open(
							"info.txt")));
			if (null != infoPage) {
				nClumnAdapter = new NewsClumnAdapter(getActivity(),
						infoPage.getNewsList());
				pslv.setAdapter(nClumnAdapter);
				hPager = new HotHeaderPager(getActivity());
				hPager.setHotList(infoPage.getPointList());
				hPager.update();
				pslv.getRefreshableView().addHeaderView(hPager);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		rl_loading.setVisibility(View.GONE);
		NewsClumns newsClumns = new NewsClumns();
		newsClumns.setTitle("模块1");
		newsClumns.setId("111");
		newsClumns.setImageresource("adad");
		newsClumns.setPointnewtitle("热点事件");
		newsClumns.setNewslink("adddd");
		newList.add(newsClumns);
		newList.add(newsClumns);
		newList.add(newsClumns);
		newList.add(newsClumns);
		nClumnAdapter = new NewsClumnAdapter(getActivity(), newList);
		initWeidget();
	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		pslv.setAdapter(nClumnAdapter);
		HotPoint hPoint = new HotPoint();
		hPoint.setTitle("title");
		hPoint.setImagelink("aaa");
		hPoint.setLink("aaaa");
		hotList.add(hPoint);
		hotList.add(hPoint);
		hotList.add(hPoint);
		hotList.add(hPoint);

		// PagerHotAdapter phAdapter = new PagerHotAdapter(getFragmentManager(),
		// hotList);

		hPager = new HotHeaderPager(getActivity());
		// View viewItem = View.inflate(getActivity(),
		// R.layout.fragment_hotpoint,
		// null);
		for (int i = 0; i < hotList.size(); i++) {

			ImageView iv_content = new ImageView(getActivity());
			iv_content.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			iv_content.setScaleType(ScaleType.FIT_XY);
			iv_content.setImageResource(R.drawable.icon_default);
			viewList.add(iv_content);
		}
		hpAdapter = new HeadPageAdapter(viewList, getActivity());
		hPager.init(hpAdapter);
		// hPager.beginShow();
		// hPager.setLayoutParams(new LayoutParams(ScreenUtils
		// .getScreenWidth(getActivity()), ScreenUtils
		// .getScreenHeight(getActivity()) / 4));
		pslv.getRefreshableView().addHeaderView(hPager);
	}
}
