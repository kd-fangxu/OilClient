package com.oil.fragments;

import com.example.oilclient.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragmentUserPage extends Fragment {
	public TabFragmentUserPage() {
	};

	public TabFragmentUserPage getInstance() {
		return new TabFragmentUserPage();
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_tab_five,
				null);
		return view;
	}
}
