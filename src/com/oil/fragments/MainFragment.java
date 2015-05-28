package com.oil.fragments;

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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.oilclient.R;
import com.oil.activity.MainActivity;
import com.oil.activity.SearchPageActivity;

public class MainFragment extends Fragment {
	RadioGroup rg_tab;
	ImageView iv_mainback;
	EditText et_search;

	public MainFragment() {
	};

	public static MainFragment getInstance() {
		MainFragment mainFragment = new MainFragment();
		return mainFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_mainpage,
				null);
		rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab);
		iv_mainback = (ImageView) view.findViewById(R.id.iv_mainback);
		et_search = (EditText) view.findViewById(R.id.et_search);
		et_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),
						SearchPageActivity.class));
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

	private void initWeidget() {
		// TODO Auto-generated method stub
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
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				switch (checkedId) {
				case R.id.rb_tab_main:
					ft.replace(R.id.fl_showpage,
							TabFragmentMainPage.getInstance());

					break;
				case R.id.rb_tab_info:
					ft.replace(R.id.fl_showpage, new TabFragmentLzInfoPage());
					break;
				case R.id.rb_tab_data:
					ft.replace(R.id.fl_showpage, new TabFragmetLzDataPage(1));
					break;
				case R.id.rb_tab_msg:
					ft.replace(R.id.fl_showpage, new TabFragmentMsgPage());
					break;
				case R.id.rb_tab_user:
					// ft.replace(R.id.fl_showpage, new TabFragmentUserPage());
					ft.replace(R.id.fl_showpage, new TabFragmetLzDataPage(0));
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
