package com.oil.fragments;

import com.example.oilclient.R;
import com.oil.activity.MainActivity;
import com.oil.activity.SearchPageActivity;
import com.oil.event.UserTemChangedEvent;
import com.oil.workmodel.AppConfigModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import de.greenrobot.event.EventBus;

public class MainFragment extends Fragment {
	RadioGroup rg_tab;
	ImageView iv_mainback;
	EditText et_search;
	RadioGroup rg_pageSel;
	RadioButton rb_allpro, rb_userFou;

	public MainFragment() {
	};

	public static MainFragment getInstance() {
		MainFragment mainFragment = new MainFragment();
		return mainFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_mainpage, null);
		rg_pageSel = (RadioGroup) view.findViewById(R.id.rg_pageRadio);
		rb_allpro = (RadioButton) view.findViewById(R.id.rb_allpro);
		rb_userFou = (RadioButton) view.findViewById(R.id.rb_userFou);
		rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab);
		iv_mainback = (ImageView) view.findViewById(R.id.iv_mainback);
		et_search = (EditText) view.findViewById(R.id.et_search);
		et_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), SearchPageActivity.class));
			}
		});
		initTabFragment();
		initWeidget();
		return view;
	}

	private void initTabFragment() {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// ft.replace(R.id.fl_showpage, TabFragmentMainPage.getInstance());
		// ft.commit();
	}

	public void initWeidget() {
		// TODO Auto-generated method stub

		rg_pageSel.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.rb_allpro:
					new AppConfigModel(getActivity()).setConfig(AppConfigModel.spKey_pageTypeShow, "1");

					break;
				case R.id.rb_userFou:
					new AppConfigModel(getActivity()).setConfig(AppConfigModel.spKey_pageTypeShow, "2");

					break;
				default:
					break;
				}
				EventBus.getDefault().post(new UserTemChangedEvent());
			}
		});
		String pageType = (String) new AppConfigModel(getActivity()).getConfig(AppConfigModel.spKey_pageTypeShow, "1");
		if (pageType.equals("1")) {
			rg_pageSel.check(R.id.rb_allpro);
		} else {
			rg_pageSel.check(R.id.rb_userFou);
		}
		iv_mainback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubif
				((MainActivity) getActivity()).getSlidingMenu().toggle(true);

			}
		});
		rg_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				switch (checkedId) {
				case R.id.rb_tab_main:
					ft.replace(R.id.fl_showpage, TabFragmentMainPage.getInstance());

					break;
				case R.id.rb_tab_info:
					rg_pageSel.setVisibility(View.INVISIBLE);
					ft.replace(R.id.fl_showpage, new TabFragmentLzInfoPage());
					break;
				case R.id.rb_tab_data:
					rg_pageSel.setVisibility(View.VISIBLE);
					ft.replace(R.id.fl_showpage, new TabFragmetLzDataPage(1));
					// 0:jia ge ku 价格库 1:shujuku 数据库
					break;
				case R.id.rb_tab_msg:
					rg_pageSel.setVisibility(View.INVISIBLE);
					ft.replace(R.id.fl_showpage, new TabFragmentShangJi());
					break;
				case R.id.rb_tab_user:
					rg_pageSel.setVisibility(View.VISIBLE);
					// ft.replace(R.id.fl_showpage, new TabFragmentUserPage());
					ft.replace(R.id.fl_showpage, new TabFragmetLzDataPage(0));
					// 0:jia ge ku 价格库 1:shujuku 数据库
					break;

				default:
					break;
				}
				ft.commit();
			}
		});
		rg_tab.check(R.id.rb_tab_user);
	}

	MainBackListener mainBackListener;

	public void setOnMainBackListener(MainBackListener mainBackListener) {
		this.mainBackListener = mainBackListener;
	}

	public void call() {
		this.mainBackListener.onMainBackListener();
	}

	public interface MainBackListener {
		public void onMainBackListener();
	}
}
