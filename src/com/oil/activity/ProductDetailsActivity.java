package com.oil.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.oilclient.R;
import com.oil.adapter.PagerAdapter;
import com.oil.fragments.ProductTabDataFragment;
import com.oil.fragments.ProductTableTabFragment;
import com.oil.weidget.OilContentViewPager;

/**
 * Êý¾ÝÍ¼±íÒ³ (!!!!·ÏÆú)
 * 
 * @author Administrator
 *
 */
public class ProductDetailsActivity extends FragmentActivity implements
		OnClickListener {
	RadioGroup rg_tab;
	// RadioButton rb_data, rb_table;
	OilContentViewPager ocViewPager;
	ImageView iv_back;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_productdetails);
		initWeidget();
	}

	PagerAdapter pagedAdapter;

	private void initWeidget() {
		// TODO Auto-generated method stub
		rg_tab = (RadioGroup) findViewById(R.id.rg_protab);
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(this);
		// List<String> titleList = new ArrayList<String>();
		// titleList.add("data");
		// titleList.add("table");
		// pagedAdapter = new PagerAdapter(getSupportFragmentManager(),
		// titleList,
		// PagerAdapter.TYPE_PRODUCT_DETAILS);
		// ocViewPager = (OilContentViewPager) findViewById(R.id.vp_content);
		// ocViewPager.setAdapter(pagedAdapter);
		// ocViewPager.setOnPageChangeListener(new OnPageChangeListener() {
		//
		// @Override
		// public void onPageSelected(int arg0) {
		// // TODO Auto-generated method stub
		// switch (arg0) {
		// case 0:
		// rg_tab.check(R.id.rb_data);
		// break;
		// case 1:
		// rg_tab.check(R.id.rb_table);
		// break;
		// default:
		// break;
		// }
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// rb_data = (RadioButton) findViewById(R.id.rb_data);
		// rb_table = (RadioButton) findViewById(R.id.rb_table);
		// rb_data.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// ocViewPager.setCurrentItem(0, true);
		// }
		// });
		// rb_table.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// ocViewPager.setCurrentItem(1, true);
		// }
		// });

		rg_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getSupportFragmentManager()
						.beginTransaction();
				switch (checkedId) {
				case R.id.rb_data:
					transaction.replace(R.id.fl_content,
							ProductTabDataFragment.getInstance(1 + ""));
					break;
				case R.id.rb_table:
					transaction.replace(R.id.fl_content,
							ProductTableTabFragment.getInstance());
					break;
				default:
					break;
				}
				transaction.commit();
			}
		});
		rg_tab.check(R.id.rb_data);

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
