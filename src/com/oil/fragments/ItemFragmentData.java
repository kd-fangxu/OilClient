package com.oil.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.Toast;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.oil.activity.ProductDetailsActivity;
import com.oil.adapter.ExpandDataAdapter;
import com.oil.adapter.ProDataMainGroupAdapter;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.inter.OnReturnListener;
import com.oil.utils.GsonParserFactory;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;
import com.oil.weidget.HorizontalListView;

public class ItemFragmentData extends Fragment {
	PullToRefreshExpandableListView ptep_lv;
	HorizontalListView hlv_type;
	int type = 0;// Ĭ�ϡ��ҡ� 1������
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

	public ItemFragmentData(int type, HashMap<String, String> map) {
		this.map = map;
		this.type = type;
	};

	HashMap<String, Object> currentDataMap = new HashMap<String, Object>();
	List<HashMap<String, Object>> mapContentList = new ArrayList<HashMap<String, Object>>();
	ExpandDataAdapter edAdapter;

	// List<HashMap<String, List<DataSimple>>> mapList;
	List<Map<String, String>> groupTitleList = new ArrayList<Map<String, String>>();
	ProDataMainGroupAdapter sdAdapter;// ������

	// List<String> keyList = new ArrayList<String>();

	// HashMap<String, List<DataSimple>> contentMap;// ������

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
						Toast.makeText(getActivity(),
								"groupid:" + groupPosition + "child",
								Toast.LENGTH_SHORT).show();
						// startActivity(new Intent(getActivity(),
						// ProductDataDetailActivity.class));
						startActivity(new Intent(getActivity(),
								ProductDetailsActivity.class));
						return false;
					}
				});
		return view;
	}

	private void initWeidgetAdapters() {
		// TODO Auto-generated method stub
		edAdapter = new ExpandDataAdapter(getActivity(), currentDataMap);
		sdAdapter = new ProDataMainGroupAdapter(getActivity(), groupTitleList);
		hlv_type.setAdapter(sdAdapter);
		ptep_lv.getRefreshableView().setAdapter(edAdapter);
		ptep_lv.getRefreshableView().setGroupIndicator(null);

		hlv_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				sdAdapter.SetSelectedPosition(position);
				sdAdapter.notifyDataSetChanged();
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
		HttpTool.netRequestNoCheck(getActivity(), null, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				GsonParserFactory gpf = new GsonParserFactory();
				mapContentList.clear();
				mapContentList.addAll(gpf.getProDataDatails(jsString));
				updateMainTitleAdapter();
				updateProDataAdapter();
			}

		}, url, false);
	}

	private void updateMainTitleAdapter() {
		// TODO Auto-generated method stub

		groupTitleList.clear();
		for (int i = 0; i < mapContentList.size(); i++) {
			groupTitleList.add(new ObjectConvertUtils<Map<String, String>>()
					.convert(mapContentList.get(i).get("productTemplate")));
			// Gson gson = new Gson();
			// gson.fromJson(gson.toJson(mapContentList.get(i)),
			// new TypeToken<HashMap<String, String>>() {
			// }.getType());
		}
		sdAdapter.SetSelectedPosition(0);
		sdAdapter.notifyDataSetChanged();

	}

	/**
	 * ���²�Ʒ����
	 */
	private void updateProDataAdapter() {

		currentDataMap.clear();
		currentDataMap
				.putAll((Map<? extends String, ? extends Object>) mapContentList
						.get(sdAdapter.getSelectionPositon()));
		edAdapter.notifyDataSetChanged();
	}

	// private void initTestData() {
	// // TODO Auto-generated method stub
	// mapList = new ArrayList<HashMap<String, List<DataSimple>>>();
	// contentMap = new HashMap<String, List<DataSimple>>();
	// keyList = new ArrayList<String>();
	// keyList.add("������˾����");
	// keyList.add("������˾����");
	// keyList.add("������˾����");
	// keyList.add("���Ϲ�˾����");
	//
	// // List<String> TitleList = new ArrayList<String>();
	// // TitleList.add("��Ʒ����");
	// // TitleList.add("���ձ���");
	// // TitleList.add("�q����");
	//
	// DataSimple dataSimple = new DataSimple();
	// dataSimple.setDataName("ʯ��");
	// dataSimple.setTodayData("1000rmb/t");
	// dataSimple.setYestData("970rmb/t");
	// dataSimple.setPrice("+3%");
	// DataSimple ds_header = new DataSimple();
	// ds_header.setDataName("��Ʒ����");
	// ds_header.setTodayData("���ռ۸�");
	// ds_header.setYestData("���ռ۸�");
	// ds_header.setPrice("�ǵ���");
	// List<DataSimple> contentList = new ArrayList<DataSimple>();
	// contentList.add(ds_header);
	// contentList.add(dataSimple);
	// contentList.add(dataSimple);
	// contentList.add(dataSimple);
	// contentMap.put("content", contentList);
	// for (int i = 0; i < keyList.size(); i++) {
	// mapList.add(contentMap);
	// }
	// edAdapter = new ExpandDataAdapter(getActivity(), mapList, keyList);
	// ptep_lv.getRefreshableView().setAdapter(edAdapter);
	// ptep_lv.getRefreshableView().setGroupIndicator(null);
	//
	// List<String> titleList = new ArrayList<String>();
	// if (type == 0) {
	// titleList.add("����");
	// titleList.add("������");
	// titleList.add("����");
	// titleList.add("���");
	// titleList.add("������");
	// } else if (type == 1) {
	// titleList.add("��ҵ������");
	// titleList.add("�����г���");
	// titleList.add("�����г���");
	// titleList.add("����ԭ��");
	// titleList.add("��Ӫ������");
	// }
	//
	// SimpleDemoAdapter sdAdapter = new SimpleDemoAdapter(getActivity(),
	// titleList);
	// hlv_type.setAdapter(sdAdapter);
	// hlv_type.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view,
	// int position, long id) {
	// // TODO Auto-generated method stub
	//
	// ((SimpleDemoAdapter) hlv_type.getAdapter())
	// .SetSelectedPosition(position);
	// ((SimpleDemoAdapter) hlv_type.getAdapter())
	// .notifyDataSetChanged();
	// }
	// });
	// }
}
