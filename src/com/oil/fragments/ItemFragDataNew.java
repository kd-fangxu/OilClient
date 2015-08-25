package com.oil.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.oil.adapter.ExpandDataAdapter;
import com.oil.adapter.ProDataMainGroupAdapter;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.datasave.AppDataCatchModel;
import com.oil.iface.OnDataReturnListener;
import com.oil.utils.ObjectConvertUtils;
import com.oil.weidget.HorizontalListView;

/**
 * page to show price or data
 * 
 * @author user
 *
 */
public class ItemFragDataNew extends Fragment {
	int type = 0; // 0:price 1:data
	HashMap<String, String> map;
	PullToRefreshExpandableListView ptep_lv;
	HorizontalListView hlv_type;
	List<Map<String, Object>> groupTitleList = new ArrayList<Map<String, Object>>();
	List<HashMap<String, Object>> mapContentList = new ArrayList<HashMap<String, Object>>();
	List<Map<String, Object>> currentGroupMapList = new ArrayList<Map<String, Object>>();

	ProDataMainGroupAdapter groupAdapter;
	ExpandDataAdapter expendAdapter;

	private int groupSelectPosition = 0;

	public ItemFragDataNew() {
	};

	public ItemFragDataNew(int type, HashMap<String, String> map) {
		this.type = type;
		this.map = map;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_dataitem,
				null);
		ptep_lv = (PullToRefreshExpandableListView) view
				.findViewById(R.id.prex_lv);
		hlv_type = (HorizontalListView) view.findViewById(R.id.hlv_type);
		initView();
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		getGroupList();
	}

	private void getGroupList() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String url = Constants.URL_GET_PRO_GROUP + "/"
				+ OilUser.getCurrentUser(getActivity()).getCuuid() + "/"
				+ map.get("wang_id") + "/" + map.get("chan_id") + "/"
				+ map.get("pro_id");
		Log.e("url", url);
		MyRequestParams params = new MyRequestParams(getActivity());
		params.put("groupID", type + "");
		AppDataCatchModel catchModel = new AppDataCatchModel(getActivity(),
				url, params);
		catchModel.setOnDataReturnListener(
				OilUser.getCurrentUser(getActivity()).getCuuid() + "datagroup"
						+ map.get("wang_id") + "_" + map.get("chan_id") + "_"
						+ map.get("pro_id") + type + ".json", false, false,
				new OnDataReturnListener() {

					@Override
					public void onDataReturn(String content) {
						onGroupLoaded(content);
					}

				});

	}

	private void onGroupLoaded(String content) {
		// TODO Auto-generated method stub
		// onDataLoaded(content);
		try {
			JSONObject jObject = new JSONObject(content);
			String data = jObject.getString("data");
			ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
			groupTitleList.addAll(ocu.convert(data));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setGroupSelPos(groupSelectPosition);
		groupAdapter.notifyDataSetChanged();
	}

	private void initView() {
		// TODO Auto-generated method stub
		expendAdapter = new ExpandDataAdapter(getActivity(),
				currentGroupMapList);
		groupAdapter = new ProDataMainGroupAdapter(getActivity(),
				groupTitleList);
		hlv_type.setAdapter(groupAdapter);
		ptep_lv.getRefreshableView().setAdapter(expendAdapter);
		ptep_lv.getRefreshableView().setGroupIndicator(null);

		hlv_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setGroupSelPos(position);
			}
		});
	}

	protected void setGroupSelPos(int position) {
		// TODO Auto-generated method stub
		groupSelectPosition = position;
		groupAdapter.SetSelectedPosition(groupSelectPosition);
		updatePagedata();
		groupAdapter.notifyDataSetChanged();
	}

	private void updatePagedata() {// »ñÈ¡Ò³ÄÚÈÝ
		// TODO Auto-generated method stub

	}
}
