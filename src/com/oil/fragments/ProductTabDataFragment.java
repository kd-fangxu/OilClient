package com.oil.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oilclient.R;
import com.oil.adapter.PagerAdapter;
import com.oil.bean.NewsClumns;
import com.oil.datamodel.InfoPage;
import com.oil.utils.GsonParserFactory;
import com.oil.utils.StringUtils;
import com.oil.weidget.OilContentViewPager;
import com.oil.weidget.PagerSlidingTabStrip;

public class ProductTabDataFragment extends Fragment {
	public ProductTabDataFragment() {
	};

	String pageId;

	public ProductTabDataFragment(String productPageId) {
		// TODO Auto-generated constructor stub
		this.pageId = productPageId;
	}

	public static ProductTabDataFragment getInstance(String productPageId) {// ΩÁ√Êid
		return new ProductTabDataFragment(productPageId);
	}

	OilContentViewPager ocViewpager;
	PagerSlidingTabStrip pagerIndicator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_protab_data,
				null);
		ocViewpager = (OilContentViewPager) view.findViewById(R.id.vp_content);
		pagerIndicator = (PagerSlidingTabStrip) view
				.findViewById(R.id.ps_indicator);
		// pAdapter=new PagerAdapter(getFragmentManager(), titleList, type)
		pAdapter = new PagerAdapter<NewsClumns>(getFragmentManager(),
				clumnList, PagerAdapter.TYPE_PRODUCT_DETAILS) {

			@Override
			public String getName(NewsClumns item) {
				// TODO Auto-generated method stub
				return item.getTitle();
			}
		};
		ocViewpager.setAdapter(pAdapter);
		pagerIndicator.setViewPager(ocViewpager);
		initDemoData();

		return view;
	}

	List<String> titleList = new ArrayList<String>();
	List<NewsClumns> clumnList = new ArrayList<NewsClumns>();
	// PagerAdapter pAdapter;
	PagerAdapter<NewsClumns> pAdapter;

	private void initDemoData() {
		// TODO Auto-generated method stub
		GsonParserFactory gsonUtils = new GsonParserFactory();
		InfoPage infoPage;
		try {
			infoPage = gsonUtils.getInfoPage(StringUtils
					.convertStreamToString(getActivity().getAssets().open(
							"info.txt")));
			clumnList.clear();
			clumnList.addAll(infoPage.getNewsList());
			pAdapter.notifyDataSetChanged();
			pagerIndicator.notifyDataSetChanged();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
