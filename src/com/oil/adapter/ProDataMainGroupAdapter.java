package com.oil.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oilclient.R;

public class ProDataMainGroupAdapter extends BaseAdapter {
	List<Map<String, String>> dataList;
	Context context;

	public ProDataMainGroupAdapter(Context context,
			List<Map<String, String>> dataList) {
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

	int selectedPosition = 0;

	public void SetSelectedPosition(int position) {
		// TODO Auto-generated method stub
		this.selectedPosition = position;
	}

	public int getSelectionPositon() {
		return selectedPosition;
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
		dHolder.tv_content.setText(dataList.get(position).get("temp_name"));
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
