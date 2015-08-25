package com.oil.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.oil.activity.ProHisDataListActivity;
import com.oil.adapter.ExpandDataAdapter;
import com.oil.adapter.ProDataMainGroupAdapter;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.datasave.AppDataCatchModel;
import com.oil.iface.OnDataReturnListener;
import com.oil.utils.GsonParserFactory;
import com.oil.utils.ObjectConvertUtils;
import com.oil.weidget.HorizontalListView;

public class ItemFragmentData extends Fragment {
	PullToRefreshExpandableListView ptep_lv;
	HorizontalListView hlv_type;
	int groupID = 0;// 榛橀敓杈冣槄鎷烽敓鎻槄鎷�1閿熸枻鎷烽敓鏂ゆ嫹閿燂拷
	Map<String, String> map;

	/**
	 * 
	 * @param type
	 *            0:wo 1:data
	 * @return
	 */
	public static ItemFragmentData getInstance(int type,
			HashMap<String, String> map) {

		return new ItemFragmentData(type, map);

	}

	public ItemFragmentData() {
	}

	public ItemFragmentData(int type, HashMap<String, String> map) {
		this.map = map;
		this.groupID = type;
	};

	// HashMap<String, Object> currentDataMap = new HashMap<String, Object>();
	List<HashMap<String, Object>> mapContentList = new ArrayList<HashMap<String, Object>>();
	ExpandDataAdapter expendAdapter;

	// List<HashMap<String, List<DataSimple>>> mapList;
	List<Map<String, Object>> groupTitleList = new ArrayList<Map<String, Object>>();
	ProDataMainGroupAdapter groupAdapter;// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�

	// List<String> keyList = new ArrayList<String>();

	// HashMap<String, List<DataSimple>> contentMap;// 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_dataitem,
				null);
		ptep_lv = (PullToRefreshExpandableListView) view
				.findViewById(R.id.prex_lv);
		hlv_type = (HorizontalListView) view.findViewById(R.id.hlv_type);

		ptep_lv.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {

			@Override
			public void onRefresh(
					PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO Auto-generated method stub
				getData();
			}
		});

		// initTestData();
		initWeidgetAdapters();
		getData();
		ptep_lv.getRefreshableView().setOnChildClickListener(
				new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// TODO Auto-generated method stub
						String title = ((Map<String, Object>) expendAdapter
								.getChild(groupPosition, childPosition)).get(
								"unit_name").toString();
						String unitId = ((Map<String, Object>) expendAdapter
								.getChild(groupPosition, childPosition)).get(
								"unit_id").toString();
						Intent intent = new Intent(getActivity(),
								ProHisDataListActivity.class);
						intent.putExtra("unitId", unitId);
						intent.putExtra("title", title);
						startActivity(intent);
						return false;
					}
				});
		return view;
	}

	private void initWeidgetAdapters() {
		// TODO Auto-generated method stub
		expendAdapter = new ExpandDataAdapter(getActivity(), currentGroupMapList);
		groupAdapter = new ProDataMainGroupAdapter(getActivity(), groupTitleList);
		hlv_type.setAdapter(groupAdapter);
		ptep_lv.getRefreshableView().setAdapter(expendAdapter);
		ptep_lv.getRefreshableView().setGroupIndicator(null);

		hlv_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				groupAdapter.SetSelectedPosition(position);
				groupAdapter.notifyDataSetChanged();
				updateProDataAdapter();
			}
		});
	}

	private void getData() {

		// TODO Auto-generated method stub
		String url = Constants.URL_GETPRODDATA + "/"
				+ OilUser.getCurrentUser(getActivity()).getCuuid() + "/"
				+ map.get("wang_id") + "/" + map.get("chan_id") + "/"
				+ map.get("pro_id");
		Log.e("url", url);
		MyRequestParams params = new MyRequestParams(getActivity());
		params.put("groupID", groupID + "");
		AppDataCatchModel catchModel = new AppDataCatchModel(getActivity(),
				url, params);
		catchModel.setOnDataReturnListener(OilUser
				.getCurrentUser(getActivity()).getCuuid()
				+ map.get("wang_id")
				+ "_"
				+ map.get("chan_id")
				+ "_"
				+ map.get("pro_id")
				+ groupID
				+ ".json", true, true, new OnDataReturnListener() {

			@Override
			public void onDataReturn(String content) {
				// TODO Auto-generated method stub
				onDataLoaded(content);
			}
		});
	}

	private void onDataLoaded(String jsString) {
		// TODO Auto-generated method stub
		GsonParserFactory gpf = new GsonParserFactory();
		mapContentList.clear();
		mapContentList.addAll(gpf.getProDataDatails(jsString));
		ObjectConvertUtils<List<Map<String, Object>>> convertUtils = new ObjectConvertUtils<List<Map<String, Object>>>();
		groupTitleList.clear();
		try {
			groupTitleList.addAll(convertUtils.convert(new JSONObject(jsString)
					.getString("productTempClassList")));
			filterEmptyGroupList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			groupTitleList.clear();
		}
		if (groupTitleList.size() > 0) {

			updateMainTitleAdapter();
			updateProDataAdapter();
		}
		ptep_lv.onRefreshComplete();
	}

	// private void getData() {
	//
	// // TODO Auto-generated method stub
	// String url = Constants.URL_GETPRODDATA + "/"
	// + OilUser.getCurrentUser(getActivity()).getCuuid() + "/"
	// + map.get("wang_id") + "/" + map.get("chan_id") + "/"
	// + map.get("pro_id");
	// Log.e("url", url);
	// MyRequestParams params = new MyRequestParams(getActivity());
	// params.put("groupID", groupID + "");
	// HttpTool.netRequestNoCheck(getActivity(), params,
	// new OnReturnListener() {
	//
	// @Override
	// public void onReturn(String jsString) {
	// // TODO Auto-generated method stub
	// GsonParserFactory gpf = new GsonParserFactory();
	// mapContentList.clear();
	// mapContentList.addAll(gpf.getProDataDatails(jsString));
	// ObjectConvertUtils<List<Map<String, Object>>> convertUtils = new
	// ObjectConvertUtils<List<Map<String, Object>>>();
	// groupTitleList.clear();
	// try {
	// groupTitleList.addAll(convertUtils
	// .convert(new JSONObject(jsString)
	// .getString("productTempClassList")));
	// filterEmptyGroupList();
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (groupTitleList.size() > 0) {
	//
	// updateMainTitleAdapter();
	// updateProDataAdapter();
	// }
	// ptep_lv.onRefreshComplete();
	//
	// }
	//
	// }, url, false);
	// }

	private void updateMainTitleAdapter() {
		// TODO Auto-generated method stub

		// groupTitleList.clear();
		// for (int i = 0; i < mapContentList.size(); i++) {
		// groupTitleList.add(new ObjectConvertUtils<Map<String, String>>()
		// .convert(new Gson().toJson(mapContentList.get(i).get(
		// "productTemplate"))));
		// // Gson gson = new Gson();
		// // gson.fromJson(gson.toJson(mapContentList.get(i)),
		// // new TypeToken<HashMap<String, String>>() {
		// // }.getType());
		// }
		groupAdapter.SetSelectedPosition(0);
		groupAdapter.notifyDataSetChanged();

	}

	List<Map<String, Object>> currentGroupMapList = new ArrayList<Map<String, Object>>();

	/**
	 * 閿熸枻鎷烽敓閾拌鎷峰搧閿熸枻鎷烽敓锟�
	 */
	private void updateProDataAdapter() {

		// currentDataMap.clear();
		// currentDataMap
		// .putAll((Map<? extends String, ? extends Object>) mapContentList
		// .get(groupAdapter.getSelectionPositon()));
		// expendAdapter.notifyDataSetChanged();
		currentGroupMapList.clear();
		for (int i = 0; i < mapContentList.size(); i++) {
			String s = ((Map<String, Object>) groupAdapter.getItem(groupAdapter
					.getSelectionPositon())).get("clas_id").toString();
			String s1 = ((Map<String, Object>) mapContentList.get(i).get(
					"productTemplate")).get("clas_id").toString();
			if (s.equals(s1)) {
				currentGroupMapList.add(mapContentList.get(i));
			}
		}
		expendAdapter.notifyDataSetChanged();
	}

	private boolean filterEmptyGroupList() {
		// int size = ;
		for (int i = 0; i < groupTitleList.size(); i++) {
			String s = groupTitleList.get(i).get("clas_id").toString();
			if (!clasIdcheck(s)) {
				
				groupTitleList.remove(i);
				i--;
			}
		}
		return false;
	}

	public boolean clasIdcheck(String s) {
		try {
			for (int j = 0; j < mapContentList.size(); j++) {

				String s1 = ((Map<String, Object>) mapContentList.get(j).get(
						"productTemplate")).get("clas_id").toString();
				if (s.equals(s1)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;

	}
}
