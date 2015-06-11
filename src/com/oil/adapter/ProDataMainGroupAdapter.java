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
	List<Map<String, Object>> dataList;
	Context context;

	public ProDataMainGroupAdapter(Context context,
			List<Map<String, Object>> dataList) {
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

		convertView = View.inflate(context, R.layout.item_simpledatademo, null);
		TextView tv = (TextView) convertView.findViewById(R.id.tv_content);
		tv.setText(dataList.get(position).get("clas_name").toString());
		if (position == selectedPosition) {
			tv.setSelected(true);
		} else {
			tv.setSelected(false);
		}

		return convertView;
	}

}
