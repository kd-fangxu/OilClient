package com.oil.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oilclient.R;

public class TabFragmentMsgPage extends Fragment {
	public TabFragmentMsgPage() {
	};

	public static TabFragmentMsgPage getInstance() {
		return new TabFragmentMsgPage();
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_tab_four,
				null);
		return view;
	}
}
