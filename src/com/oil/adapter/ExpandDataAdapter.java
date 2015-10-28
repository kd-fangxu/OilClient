package com.oil.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.oilclient.R;
import com.oil.bean.OilUser;
import com.oil.workmodel.UserTemFouceModel;
import com.oil.workmodel.UserTemFouceModel.onTemFouceCallBack;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 通常二级显示（template---》unit）
 * 
 * @author xu
 *
 */
public class ExpandDataAdapter extends BaseExpandableListAdapter {
	// List<HashMap<String, List<DataSimple>>> mapList = new
	// ArrayList<HashMap<String, List<DataSimple>>>();
	// List<String> keyList = new ArrayList<String>();
	Context context;
	String unit = "";
	// UserTemFouceModel temFouceModel;
	// public ExpandDataAdapter(Context context,
	// List<HashMap<String, List<DataSimple>>> mapList,
	// List<String> keyList) {
	// if (mapList.size() == keyList.size()) {
	// this.mapList.clear();
	// this.mapList.addAll(mapList);
	// this.keyList.clear();
	// this.keyList.addAll(keyList);
	// this.context = context;
	// }
	//
	// }

	List<Map<String, String>> dataTimeHeadList = new ArrayList<Map<String, String>>();
	List<Map<String, Object>> productUnitList = new ArrayList<Map<String, Object>>();
	HashMap<String, Object> currentDataMap;
	List<Map<String, Object>> currentGroupMapList;
	private List<Map<String, Object>> temUserFouceList;

	public ExpandDataAdapter(Context context, List<Map<String, Object>> currentGroupMapList,
			List<Map<String, Object>> userTemList) {
		this.context = context;
		this.currentGroupMapList = currentGroupMapList;
		this.temUserFouceList = userTemList;
		// temFouceModel = new UserTemFouceModel();
		// TODO Auto-generated constructor stub
		// unit = ((HashMap<String, String>)
		// currentDataMap.get("productTemplate"))
		// .get("temp_unit");
		// dataTimeHeadList.clear();
		// dataTimeHeadList.addAll((List<HashMap<String, String>>)
		// currentDataMap
		// .get("dataTimeHeadList"));
		// productUnitList.clear();
		// productUnitList
		// .addAll((Collection<? extends HashMap<String, String>>)
		// currentDataMap
		// .get("productUnitList"));
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		// unit = ((Map<String, String>) currentDataMap.get("productTemplate"))
		// .get("temp_unit");
		// dataTimeHeadList.clear();
		// dataTimeHeadList.addAll((List<Map<String, String>>) currentDataMap
		// .get("dataTimeHeadList"));
		// productUnitList.clear();
		// productUnitList.addAll();
		super.notifyDataSetChanged();
	}

	// /**
	// * 锟斤拷锟截碉拷元id
	// *
	// * @return
	// */
	// public String getProductTemplate() {
	// return this.unit;
	// }

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return currentGroupMapList.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ((List<Map<String, Object>>) currentGroupMapList.get(groupPosition).get("productUnitList")).size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return (Map<String, Object>) currentGroupMapList.get(groupPosition).get("productTemplate");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return ((List<Map<String, Object>>) currentGroupMapList.get(groupPosition).get("productUnitList"))
				.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// mapList.get(groupPosition).
		GroupHolder gHolder = null;
		if (convertView == null) {
			gHolder = new GroupHolder();
			convertView = View.inflate(context, R.layout.item_eplv_group, null);
			gHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_item_content);
			gHolder.iv_indicator = (ImageView) convertView.findViewById(R.id.iv_indicator);
			gHolder.iv_temFouce = (ImageView) convertView.findViewById(R.id.iv_temfou);
			convertView.setTag(gHolder);
		}
		gHolder = (GroupHolder) convertView.getTag();
		gHolder.tv_content.setText(((Map<String, Object>) currentGroupMapList.get(groupPosition).get("productTemplate"))
				.get("temp_name").toString());
		if (isExpanded) {
			gHolder.iv_indicator.setImageResource(R.drawable.icon_arrow_up);
		} else {
			gHolder.iv_indicator.setImageResource(R.drawable.icon_arrow_below);
		}
		final String tempId = ((Map<String, Object>) getGroup(groupPosition)).get("temp_id").toString();
		final String userId = OilUser.getCurrentUser(context).getCuuid();
		if (isFouced(tempId)) {
			gHolder.iv_temFouce.setImageResource(R.drawable.icon_mark);
			gHolder.iv_temFouce.setSelected(true);
		} else {
			gHolder.iv_temFouce.setImageResource(R.drawable.icon_unmark);
			gHolder.iv_temFouce.setSelected(false);
		}
		gHolder.iv_temFouce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.isSelected()) {// 已收藏
					((ImageView) v).setSelected(false);
					((ImageView) v).setImageResource(R.drawable.icon_unmark);
					UserTemFouceModel.getInstace().changeTemFouce(context, tempId, userId, "2",
							new onTemFouceCallBack() {

						@Override
						public void onTemFouceReturn(List<Map<String, Object>> maps) {
							// TODO Auto-generated method stub
							if (maps != null) {
								temUserFouceList.clear();
								temUserFouceList.addAll(maps);
								notifyDataSetChanged();
							}
						}
					});
				} else {// 未收藏
					((ImageView) v).setSelected(true);
					((ImageView) v).setImageResource(R.drawable.icon_mark);
					UserTemFouceModel.getInstace().changeTemFouce(context, tempId, userId, "1",
							new onTemFouceCallBack() {

						@Override
						public void onTemFouceReturn(List<Map<String, Object>> maps) {
							// TODO Auto-generated method stub
							if (maps != null) {
								temUserFouceList.clear();
								temUserFouceList.addAll(maps);
								notifyDataSetChanged();
							}
						}
					});
				}
			}

		});
		return convertView;
	}

	private boolean isFouced(String tempId) {

		if (temUserFouceList != null) {
			for (int i = 0; i < temUserFouceList.size(); i++) {
				if (temUserFouceList.get(i).get("temp_id").toString().equals(tempId)) {
					return true;
				}

			}
		}
		return false;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHolder cHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_data_simple, null);
			cHolder = new ChildHolder();
			cHolder.tv_conten1 = (TextView) convertView.findViewById(R.id.tv_item_content1);
			cHolder.tv_content2 = (TextView) convertView.findViewById(R.id.tv_item_content2);
			cHolder.tv_content3 = (TextView) convertView.findViewById(R.id.tv_item_content3);
			cHolder.tv_content3.setVisibility(View.GONE);
			cHolder.ll_header = (LinearLayout) convertView.findViewById(R.id.ll_item_title);
			convertView.setTag(cHolder);
		}
		cHolder = (ChildHolder) convertView.getTag();
		String name = ((Map<String, Object>) getChild(groupPosition, childPosition)).get("unit_name").toString();
		String timeTag = ((Map<String, Object>) getChild(groupPosition, childPosition)).get("data_times").toString();
		// Log.e("timetag", timeTag);
		// String data = productUnitList.get(groupPosition).get(timeTag) + "";

		cHolder.tv_conten1.setText(timeTag.trim());

		cHolder.tv_content2.setText(name.trim());
		// cHolder.tv_content3.setText(unit.trim());
		if (childPosition == 0) {
			cHolder.ll_header.setVisibility(View.VISIBLE);
		} else {
			cHolder.ll_header.setVisibility(View.GONE);
		}
		return convertView;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	private class GroupHolder {
		TextView tv_content;
		ImageView iv_indicator;
		ImageView iv_temFouce;
	}

	private class ChildHolder {
		TextView tv_conten1, tv_content2, tv_content3;
		LinearLayout ll_header;
	}
}
