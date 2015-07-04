package com.oil.fragments;

import android.support.v4.app.Fragment;

public class ItemFragmentNews extends Fragment {
	private static ItemFragmentNews iFragment;

	public ItemFragmentNews() {
	}

	public static ItemFragmentNews getInstance() {

		iFragment = new ItemFragmentNews();

		return iFragment;
	}
}
