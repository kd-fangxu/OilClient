package com.oil.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.oilclient.R;

/**
 * 资讯界面
 * 
 * @author user
 *
 */
public class TabFragmentLzInfoPage extends Fragment implements OnClickListener {
	public TabFragmentLzInfoPage() {
	};

	public static TabFragmentLzInfoPage getInstance() {
		return new TabFragmentLzInfoPage();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_tab_second,
				null);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
