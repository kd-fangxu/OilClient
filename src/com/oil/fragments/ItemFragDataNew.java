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
import android.widget.ExpandableListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.oil.activity.ProHisDataListActivity;
import com.oil.adapter.ExpandDataAdapter;
import com.oil.adapter.ExpandDataTagAdapter;
import com.oil.adapter.ProDataMainGroupAdapter;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.datasave.AppDataCatchModel;
import com.oil.iface.OnDataReturnListener;
import com.oil.inter.OnReturnListener;
import com.oil.utils.GsonParserFactory;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.ToastUtils;
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
	List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();// 保存标签列表
	ProDataMainGroupAdapter groupAdapter;
	BaseExpandableListAdapter expendAdapter;

	private int groupSelectPosition = 0;
	boolean isMsgShow = false;

	public ItemFragDataNew() {
	};

	public ItemFragDataNew(int type, HashMap<String, String> map) {
		this.type = type;
		this.map = map;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_dataitem, null);
		ptep_lv = (PullToRefreshExpandableListView) view.findViewById(R.id.prex_lv);
		hlv_type = (HorizontalListView) view.findViewById(R.id.hlv_type);
		initView();
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		getGroupList(true);
	}

	/**
	 * 获取底部栏
	 * 
	 * @param isFromCatch：是否从缓存取数据
	 */
	private void getGroupList(boolean isFromCatch) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String url = Constants.URL_GET_PRO_GROUP + "/" + OilUser.getCurrentUser(getActivity()).getCuuid() + "/"
				+ map.get("wang_id") + "/" + map.get("chan_id") + "/" + map.get("pro_id");
		Log.e("url", url);
		MyRequestParams params = new MyRequestParams(getActivity());
		params.put("groupID", type + "");
		AppDataCatchModel catchModel = new AppDataCatchModel(getActivity(), url, params);
		catchModel.setOnDataReturnListener(
				OilUser.getCurrentUser(getActivity()).getCuuid() + "datagroup" + map.get("wang_id") + "_"
						+ map.get("chan_id") + "_" + map.get("pro_id") + type + ".json",
				true, isFromCatch, new OnDataReturnListener() {

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
			groupTitleList.clear();
			JSONObject jObject = new JSONObject(content);
			String data = jObject.getString("data");
			ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
			groupTitleList.addAll(ocu.convert(data));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ptep_lv.onRefreshComplete();
			if (isMsgShow) {
				ToastUtils.getInstance(getActivity()).showText("无分组数据");
			}

		}
		setGroupSelPos(groupSelectPosition);
		groupAdapter.notifyDataSetChanged();
	}

	public void initView() {
		// TODO Auto-generated method stub
		normalGroupInit();
		groupAdapter = new ProDataMainGroupAdapter(getActivity(), groupTitleList);
		hlv_type.setAdapter(groupAdapter);

		ptep_lv.getRefreshableView().setGroupIndicator(null);

		hlv_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				setGroupSelPos(position);
			}
		});

		ptep_lv.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
				// TODO Auto-generated method stub
				isMsgShow = true;
				getGroupList(false);
			}
		});
	}

	/**
	 * 正常分组显示
	 */
	private void normalGroupInit() {
		expendAdapter = new ExpandDataAdapter(getActivity(), currentGroupMapList);
		ptep_lv.getRefreshableView().setAdapter(expendAdapter);
		ptep_lv.getRefreshableView().setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				// TODO Auto-generated method stub
				String title = ((Map<String, Object>) expendAdapter.getChild(groupPosition, childPosition))
						.get("unit_name").toString();
				String unitId = ((Map<String, Object>) expendAdapter.getChild(groupPosition, childPosition))
						.get("unit_id").toString();
				Intent intent = new Intent(getActivity(), ProHisDataListActivity.class);
				intent.putExtra("unitId", unitId);
				intent.putExtra("title", title);
				startActivity(intent);
				return false;
			}
		});
	}

	/**
	 * 组分组初始化
	 */
	private void tagGroupInit() {
		expendAdapter = new ExpandDataTagAdapter(getActivity(), tagList, currentGroupMapList);
		ptep_lv.getRefreshableView().setAdapter(expendAdapter);
		ptep_lv.getRefreshableView().setOnChildClickListener(new OnChildClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				// TODO Auto-generated method stub
				String title = null;
				String unitId = null;
				@SuppressWarnings("unchecked")
				Map<String, Object> mapItme = (Map<String, Object>) expendAdapter.getChild(groupPosition,
						childPosition);
				String tempId = mapItme.get("temp_id").toString();
				for (int i = 0; i < currentGroupMapList.size(); i++) {
					if (((Map<String, Object>) currentGroupMapList.get(i).get("productTemplate")).get("temp_id")
							.toString().equals(tempId)) {
						List<Map<String, Object>> unitList = (List<Map<String, Object>>) currentGroupMapList.get(i)
								.get("productUnitList");
						if (unitList != null) {
							if (unitList.size() > 0) {
								title = unitList.get(0).get("unit_name").toString();
								unitId = unitList.get(0).get("unit_id").toString();
							}
						}
						break;
					}

				}
				if (unitId != null && title != null) {
					Intent intent = new Intent(getActivity(), ProHisDataListActivity.class);
					intent.putExtra("unitId", unitId);
					intent.putExtra("title", title);
					startActivity(intent);
				}

				return false;
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected void setGroupSelPos(int position) {
		// TODO Auto-generated method stub
		groupSelectPosition = position;
		groupAdapter.SetSelectedPosition(groupSelectPosition);
		if (groupAdapter.getCount() > 0) {

			updatePagedata(((Map<String, Object>) groupAdapter.getItem(position)));
		}

		groupAdapter.notifyDataSetChanged();
	}

	/**
	 * get data and update page
	 * 
	 * @param mapItem
	 */
	private void updatePagedata(Map<String, Object> mapItem) {// 获取页内容
		// TODO Auto-generated method stub
		boolean isFromCatch = true;
		if (ptep_lv.isRefreshing()) {
			isFromCatch = false;
		}
		if (mapItem != null) {
			// TODO Auto-generated method stub
			final String clasId = mapItem.get("clas_id").toString();
			// TODO Auto-generated method stub
			String url = Constants.URL_GET_PRO_PAGEDATA + "/" + OilUser.getCurrentUser(getActivity()).getCuuid() + "/"
					+ map.get("wang_id") + "/" + map.get("chan_id") + "/" + map.get("pro_id");
			Log.e("url", url);
			MyRequestParams params = new MyRequestParams(getActivity());
			params.put("groupID", type + "");
			params.put("clasId", clasId);
			AppDataCatchModel catchModel = new AppDataCatchModel(getActivity(), url, params);
			catchModel.setOnDataReturnListener(
					OilUser.getCurrentUser(getActivity()).getCuuid() + "dataUnit" + map.get("wang_id") + "_"
							+ map.get("chan_id") + "_" + map.get("pro_id") + clasId + ".json",
					true, isFromCatch, new OnDataReturnListener() {

						@Override
						public void onDataReturn(String content) {
							// onGroupLoaded(content);
							currentGroupMapList.clear();
							tagList.clear();
							ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
							// GsonParserFactory gsonParser=new
							// GsonParserFactory();
							try {
								JSONObject jo = new JSONObject(content);
								tagList.addAll(ocu.convert(jo.getString("tag")));
								currentGroupMapList.addAll(ocu.convert(jo.getString("data")));
								editTag();

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String id = Float.valueOf(clasId).intValue() + "";
							if (id.equals("26") || id.equals("28") || id.equals("30") || id.equals("31")) {
								tagGroupInit();
							} else {
								normalGroupInit();
							}
							// expendAdapter.notifyDataSetChanged();

							ptep_lv.onRefreshComplete();
						}

						/**
						 * 整理标签数据标签
						 */
						private void editTag() {
							for (int i = 0; i < tagList.size(); i++) {// 将标签下的template放在tagList的每个item标签下
								List<Object> temList = new ArrayList<Object>();
								@SuppressWarnings("unchecked")
								List<Map<String, Object>> templateList = (List<Map<String, Object>>) tagList.get(i)
										.get("bindTemplate");
								if (templateList != null) {
									for (int a = 0; a < templateList.size(); a++) {
										for (int j = 0; j < currentGroupMapList.size(); j++) {
											@SuppressWarnings("unchecked")
											String temId = ((Map<String, Object>) currentGroupMapList.get(j)
													.get("productTemplate")).get("temp_id").toString();
											int id = Float.valueOf(temId).intValue();
											if ((id + "").equals(templateList.get(a).get("temp_Id").toString())) {

												temList.add(currentGroupMapList.get(j).get("productTemplate"));
											}
										}
									}

									tagList.get(i).put("template", temList);
								}

							}
						}

					});

		}
	}

}
