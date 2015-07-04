package com.oil.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ∆Û“µΩÈ…‹
 * 
 * @author Administrator
 *
 */
public class TabFragmentMainPage extends Fragment {
	public TabFragmentMainPage() {
	};

	public static TabFragmentMainPage getInstance() {

		return new TabFragmentMainPage();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
