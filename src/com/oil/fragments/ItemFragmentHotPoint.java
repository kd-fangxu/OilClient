package com.oil.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.oilclient.R;
import com.oil.bean.HotPoint;

public class ItemFragmentHotPoint extends Fragment {
	HotPoint hotPoint;
	ImageView iv_content;

	public ItemFragmentHotPoint(HotPoint hotPoint) {
		// TODO Auto-generated constructor stub
		this.hotPoint = hotPoint;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_hotpoint,
				null);
		iv_content = (ImageView) view.findViewById(R.id.iv_content);
		initWeidget();
		return view;
	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		iv_content.setImageResource(R.drawable.ic_launcher);
	}

}
