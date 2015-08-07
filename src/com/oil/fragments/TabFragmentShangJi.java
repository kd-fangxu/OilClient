package com.oil.fragments;

import java.util.HashMap;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.oilclient.R;
import com.oil.activity.SjListOfUserActivity;
import com.oil.adapter.PagerAdapter;
import com.oil.bean.Constants;
import com.oil.utils.ToastUtils;

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

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// return super.onCreateView(inflater, container, savedInstanceState);
	// }
	ImageView iv_myShangji;

	@Override
	public void initWeidget(View view) {
		// TODO Auto-generated method stub
		// super.initWeidget(view);
		iv_myShangji = (ImageView) view.findViewById(R.id.iv_myShangji);
		iv_myShangji.setVisibility(View.VISIBLE);
		iv_myShangji.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_myShangji:
			// ToastUtils.getInstance(getActivity()).showText("onclick");
			startActivity(new Intent(getActivity(), SjListOfUserActivity.class));
			break;

		default:
			break;
		}
	}
}
