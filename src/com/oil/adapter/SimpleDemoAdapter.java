package com.oil.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oilclient.R;

public class SimpleDemoAdapter extends BaseAdapter {
	List<String> dataList;
	Context context;

	public SimpleDemoAdapter(Context context, List<String> dataList) {
		this.context = context;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	int selectedPosition = -1;

	public void SetSelectedPosition(int position) {
		// TODO Auto-generated method stub
		this.selectedPosition = position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DemoViewHolder dHolder = null;
		if (convertView == null) {
			dHolder = new DemoViewHolder();
			convertView = View.inflate(context, R.layout.item_simpledatademo,
					null);
			dHolder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			convertView.setTag(dHolder);
		}
		dHolder = (DemoViewHolder) convertView.getTag();
		dHolder.tv_content.setText(dataList.get(position));
		if (position == selectedPosition) {
			dHolder.tv_content.setSelected(true);
		} else {
			dHolder.tv_content.setSelected(false);
		}

		return convertView;
	}

	private class DemoViewHolder {
		TextView tv_content;
	}
}
