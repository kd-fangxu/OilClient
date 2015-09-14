package com.oil.adapter;

import java.util.List;
import java.util.Map;

import com.example.oilclient.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * group by tag
 * 
 * @author xu
 *
 */
public class ExpandDataTagAdapter extends BaseExpandableListAdapter {

	// {
	// "reason": "",
	// "status": 1,
	// "tag": [{
	// "tag_Id": 8,
	// "tag_Name": "测试",
	// "clas_Name": "利润",
	// "pro_Name": "调和柴油",
	// "pro_Id": "302",
	// "clas_Id": "9"
	// }],
	// "data": [{
	// "productUnitList": [{
	// "unit_id": 2171,
	// "unit_name": "山东地区调和柴油利润估算",
	// "data_times": "2013-07-22至2015-05-24",
	// "data_time_20150420": 203.00,
	// "data_time_20150427": 150.00,
	// "data_time_20150504": 112.00,
	// "data_time_20150511": 155.00,
	// "data_time_20150518": 162.00
	// }],
	// "productTemplate": {
	// "clas_id": 9,
	// "temp_id": 298,
	// "temp_name": "山东地区调和柴油利润",
	// "temp_type": "1",
	// "temp_unit": "元/吨",
	// "clas_order": 9,
	// "emc_id": null
	// }
	// }]
	// }

	List<Map<String, Object>> tagList;
	List<Map<String, Object>> dataList;
	Context context;

	/**
	 * 
	 * @param context
	 * @param tagList
	 *            标签
	 * @param dataList
	 *            数据
	 */
	@SuppressWarnings("unchecked")
	public ExpandDataTagAdapter(Context context, List<Map<String, Object>> tagList,
			List<Map<String, Object>> dataList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.tagList = tagList;
		this.dataList = dataList;

	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub

		super.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return ((List<Map<String, Object>>) tagList.get(groupPosition).get("template")).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHolder ch = null;
		if (convertView == null) {
			ch = new ChildHolder();
			convertView = View.inflate(context, R.layout.item_simple_text, null);
			ch.tv_content = (TextView) convertView.findViewById(R.id.tv_item_simple);
			convertView.setTag(ch);
		}
		ch = (ChildHolder) convertView.getTag();
		ch.tv_content.setBackgroundResource(R.color.lu_white);
		ch.tv_content.setTextColor(Color.WHITE);
		ch.tv_content.setText(((List<Map<String, Object>>) tagList.get(groupPosition).get("template"))
				.get(childPosition).get("temp_name").toString());

		return convertView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ((List<Map<String, Object>>) tagList.get(groupPosition).get("template")).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return tagList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return tagList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupHolder gHolder = null;
		if (convertView == null) {
			gHolder = new GroupHolder();
			convertView = View.inflate(context, R.layout.item_eplv_group, null);
			gHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_item_content);
			gHolder.iv_indicator = (ImageView) convertView.findViewById(R.id.iv_indicator);
			convertView.setTag(gHolder);
		}
		gHolder = (GroupHolder) convertView.getTag();
		String content = "";
		content = ((Map<String, Object>) tagList.get(groupPosition)).get("tag_Name").toString();

		gHolder.tv_content.setText(content);
		if (isExpanded) {
			gHolder.iv_indicator.setImageResource(R.drawable.icon_arrow_up);
		} else {
			gHolder.iv_indicator.setImageResource(R.drawable.icon_arrow_below);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	private class GroupHolder {
		TextView tv_content;
		ImageView iv_indicator;
	}

	private class ChildHolder {

		TextView tv_content;
	}
}
