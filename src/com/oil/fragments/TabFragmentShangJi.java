package com.oil.fragments;

import java.util.HashMap;

import com.oil.adapter.PagerAdapter;
import com.oil.bean.Constants;

import android.view.View;

/**
 * ил╩З
 * 
 * @author Administrator
 *
 */
public class TabFragmentShangJi extends TabFragmetLzDataPage {

	public TabFragmentShangJi(int type) {
		super(type);
		// TODO Auto-generated constructor stub
		this.tem_type = type;
	}

	public TabFragmentShangJi() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initWeidget(View view) {
		// TODO Auto-generated method stub
		// super.initWeidget(view);
		pagerAdapter = new PagerAdapter<HashMap<String, String>>(
				getFragmentManager(), mapList, Constants.pageType_shangji) {

			@Override
			public String getName(HashMap<String, String> item) {
				// TODO Auto-generated method stub
				return item.get("pro_cn_name");
			}

		};
		ocvp.setAdapter(pagerAdapter);
		psts.setViewPager(ocvp);
	}
}
