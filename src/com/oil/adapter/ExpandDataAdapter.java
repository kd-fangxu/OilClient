package com.oil.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.bean.DataSimple;

public class ExpandDataAdapter extends BaseExpandableListAdapter {
	List<HashMap<String, List<DataSimple>>> mapList = new ArrayList<HashMap<String, List<DataSimple>>>();
	List<String> keyList = new ArrayList<String>();
	Context context;

	public ExpandDataAdapter(Context context,
			List<HashMap<String, List<DataSimple>>> mapList,
			List<String> keyList) {
		if (mapList.size() == keyList.size()) {
			this.mapList.clear();
			this.mapList.addAll(mapList);
			this.keyList.clear();
			this.keyList.addAll(keyList);
			this.context = context;
		}

	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mapList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mapList.get(groupPosition).get("content").size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mapList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mapList.get(groupPosition).get("content").get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// mapList.get(groupPosition).
		GroupHolder gHolder = null;
		if (convertView == null) {
			gHolder = new GroupHolder();
			convertView = View.inflate(context, R.layout.item_eplv_group, null);
			gHolder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_item_content);
			gHolder.iv_indicator = (ImageView) convertView
					.findViewById(R.id.iv_indicator);
			convertView.setTag(gHolder);
		}
		gHolder = (GroupHolder) convertView.getTag();
		gHolder.tv_content.setText(keyList.get(groupPosition));
		if (isExpanded) {
			gHolder.iv_indicator.setImageResource(R.drawable.icon_arrow_up);
		} else {
			gHolder.iv_indicator.setImageResource(R.drawable.icon_arrow_below);
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHolder cHolder = null;
		if (convertView == null) {
			convertView = View
					.inflate(context, R.layout.item_data_simple, null);
			cHolder = new ChildHolder();
			cHolder.tv_conten1 = (TextView) convertView
					.findViewById(R.id.tv_item_content1);
			cHolder.tv_content2 = (TextView) convertView
					.findViewById(R.id.tv_item_content2);
			cHolder.tv_content3 = (TextView) convertView
					.findViewById(R.id.tv_item_content3);
			cHolder.tv_content4 = (TextView) convertView
					.findViewById(R.id.tv_item_content4);
			convertView.setTag(cHolder);
		}
		cHolder = (ChildHolder) convertView.getTag();
		cHolder.tv_conten1.setText(mapList.get(groupPosition).get("content")
				.get(childPosition).getDataName());

		cHolder.tv_content2.setText(mapList.get(groupPosition).get("content")
				.get(childPosition).getTodayData());
		cHolder.tv_content3.setText(mapList.get(groupPosition).get("content")
				.get(childPosition).getYestData());
		cHolder.tv_content4.setText(mapList.get(groupPosition).get("content")
				.get(childPosition).getPrice());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	class GroupHolder {
		TextView tv_content;
		ImageView iv_indicator;
	}

	class ChildHolder {
		TextView tv_conten1, tv_content2, tv_content3, tv_content4;
	}
}
