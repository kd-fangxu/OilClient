package com.oil.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class OilSearchView extends SearchView {

	public OilSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		traversSearchView(getRootView(), 0);
	}

	private void traversSearchView(View view, int index) {
		if (view instanceof SearchView) {
			SearchView searchView = (SearchView) view;
			for (int i = 0; i < searchView.getChildCount(); ++i) {
				traversSearchView(searchView.getChildAt(i), i);
			}
		} else if (view instanceof LinearLayout) {
			// if (/*id value from search_view.xml*/ == view.getId()) {
			// //TODO
			// }
			LinearLayout linearlayout = (LinearLayout) view;
			for (int i = 0; i < linearlayout.getChildCount(); i++) {
				traversSearchView(linearlayout.getChildAt(i), i);
			}
		} else if (view instanceof EditText) {
			// if (/*id value from search_view.xml*/ == view.getId()) {
			// //TODO
			// }
			// TODO
		} else if (view instanceof ImageView) {
			// if (/*id value from search_view.xml*/ == view.getId()) {
			// //TODO
			// }
			// TODO
		}
		// other 'else if' clause
	}
}
