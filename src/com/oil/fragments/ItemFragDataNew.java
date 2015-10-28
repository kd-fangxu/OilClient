package com.oil.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.oil.activity.ProHisDataListActivity;
import com.oil.activity.ProHisDataListForScj;
import com.oil.adapter.ExpandDataAdapter;
import com.oil.adapter.ExpandDataTagAdapter;
import com.oil.adapter.ProDataMainGroupAdapter;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.datasave.AppDataCatchModel;
import com.oil.event.UserTemChangedEvent;
import com.oil.iface.OnDataReturnListener;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.ToastUtils;
import com.oil.weidget.HorizontalListView;
import com.oil.workmodel.AppConfigModel;
import com.oil.workmodel.UserTemFouceModel;
import com.oil.workmodel.UserTemFouceModel.onTemFouceCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.greenrobot.event.EventBus;

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
	LinearLayout ll_pageSlideMenu;
	private int groupSelectPosition = 0;
	boolean isMsgShow = false;
	Handler handler;
	ImageView iv_showFouTem, iv_temEdit;
	UserTemFouceModel temFouce = UserTemFouceModel.getInstace();

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
		ll_pageSlideMenu = (LinearLayout) view.findViewById(R.id.ll_pageslideMenu);
		ll_pageSlideMenu.setVisibility(View.GONE);
		iv_showFouTem = (ImageView) view.findViewById(R.id.iv_userFoucedata);
		iv_temEdit = (ImageView) view.findViewById(R.id.iv_temEdit);
		initView();
		initData();
		initHandler();
		EventBus.getDefault().register(this);
		return view;
	}

	public void onEvent(UserTemChangedEvent temChangedEvent) {
		setGroupSelPos(groupSelectPosition);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onPause();
	}

	private void initHandler() {
		// TODO Auto-generated method stub
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					if (getActivity() != null) {
						ll_pageSlideMenu.setVisibility(View.INVISIBLE);
						ll_pageSlideMenu
								.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_right));
					}

					break;

				default:
					break;
				}
			}
		};
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
			if (jObject.getString("status").equals("1")) {
				String data = jObject.getString("data");
				ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
				groupTitleList.addAll(ocu.convert(data));
			} else {
				ToastUtils.getInstance(getActivity()).showText(jObject.getString("reason"));
				ptep_lv.onRefreshComplete();
				return;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
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
		iv_showFouTem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		iv_temEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
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
		// ptep_lv.getRefreshableView().setOnScrollListener(new
		// OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// // TODO Auto-generated method stub
		// if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState ==
		// SCROLL_STATE_FLING) {
		// if (ll_pageSlideMenu.getVisibility() != View.VISIBLE) {
		// handler.removeMessages(1);
		// ll_pageSlideMenu.setVisibility(View.VISIBLE);
		// ll_pageSlideMenu.startAnimation(
		// AnimationUtils.loadAnimation(getActivity(),
		// R.anim.slide_in_from_right));
		// }
		//
		// } else {
		// if (ll_pageSlideMenu.getVisibility() == View.VISIBLE) {
		// handler.sendEmptyMessageDelayed(1, 3 * 1000);
		//
		// }
		//
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem, int
		// visibleItemCount, int totalItemCount) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	/**
	 * 正常分组显示
	 */
	private void normalGroupInit() {
		expendAdapter = new ExpandDataAdapter(getActivity(), currentGroupMapList, userTemList);
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
				String proId = map.get("pro_id");
				Intent intent = new Intent(getActivity(), ProHisDataListActivity.class);
				intent.putExtra("unitId", unitId);
				intent.putExtra("title", title);
				intent.putExtra("proId", proId);
				startActivity(intent);
				return false;
			}
		});
	}

	/**
	 * tag分组初始化
	 */
	private void tagGroupInit() {
		expendAdapter = new ExpandDataTagAdapter(getActivity(), tagList, currentGroupMapList, userTemList);
		ptep_lv.getRefreshableView().setAdapter(expendAdapter);
		ptep_lv.getRefreshableView().setOnChildClickListener(new OnChildClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				// TODO Auto-generated method stub
				String title = null;
				String unitId = null;
				String proId = map.get("pro_id");
				@SuppressWarnings("unchecked")
				Map<String, Object> mapItme = (Map<String, Object>) expendAdapter.getChild(groupPosition,
						childPosition);
				String tempId = mapItme.get("temp_id").toString();
				ArrayList<Map<String, Object>> unitList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < currentGroupMapList.size(); i++) {
					if (((Map<String, Object>) currentGroupMapList.get(i).get("productTemplate")).get("temp_id")
							.toString().equals(tempId)) {
						unitList.clear();
						unitList.addAll((List<Map<String, Object>>) currentGroupMapList.get(i).get("productUnitList"));
						if (unitList != null) {
							if (unitList.size() > 0) {
								title = unitList.get(0).get("unit_name").toString();
								unitId = unitList.get(0).get("unit_id").toString();
							}
						}
						break;
					}

				}

				// String clasId = mapItem.get("clas_id").toString();
				String intClasId = Float.valueOf(clasId).intValue() + "";
				if (intClasId.equals("30")) {
					// when clas is 国内市场价 page to another Activity
					Intent intent = new Intent(getActivity(), ProHisDataListForScj.class);
					intent.putExtra("unitList", unitList);
					intent.putExtra("title", title);
					intent.putExtra("proId", proId);
					startActivity(intent);
				} // 26 28 30
					// 31国内出厂价，主营批发价，国内市场价，国际市场价
				else {
					if (unitId != null && title != null) {
						Intent intent = new Intent(getActivity(), ProHisDataListActivity.class);
						intent.putExtra("unitId", unitId);
						intent.putExtra("title", title);
						intent.putExtra("proId", proId);
						startActivity(intent);
					}
				}
				return false;
			}
		});
	}

	List<Map<String, Object>> userTemList = new ArrayList<Map<String, Object>>();

	@SuppressWarnings("unchecked")
	protected void setGroupSelPos(final int position) {
		// TODO Auto-generated method stub
		groupSelectPosition = position;
		groupAdapter.SetSelectedPosition(groupSelectPosition);
		if (groupAdapter.getCount() > 0) {

			temFouce.getUserTemFou(getActivity(), new onTemFouceCallBack() {// 查询用户数据组关注

				@Override
				public void onTemFouceReturn(List<Map<String, Object>> maps) {
					// TODO Auto-generated method stub
					if (maps != null) {
						userTemList.clear();
						userTemList.addAll(maps);
						updatePagedata(((Map<String, Object>) groupAdapter.getItem(position)));
					}

				}
			});

		}

		groupAdapter.notifyDataSetChanged();
	}

	String clasId;
	String pageType;

	/**
	 * get data and update page
	 * 
	 * @param mapItem
	 */
	private void updatePagedata(Map<String, Object> mapItem) {// 获取页内容
		// TODO Auto-generated method stub
		pageType = (String) new AppConfigModel(getActivity()).getConfig(AppConfigModel.spKey_pageTypeShow, "1");// 1：全部
		// 2;用户收藏数据组
		boolean isFromCatch = true;
		if (ptep_lv.isRefreshing()) {
			isFromCatch = false;
		}
		if (mapItem != null) {
			// TODO Auto-generated method stub
			clasId = mapItem.get("clas_id").toString();
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

							onDataReturnComplete(content);
							// onGroupLoaded(content);
						}

						private void onDataReturnComplete(String content) {
							currentGroupMapList.clear();
							tagList.clear();
							ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
							// GsonParserFactory gsonParser=new
							// GsonParserFactory();
							try {
								JSONObject jo = new JSONObject(content);

								tagList.addAll(ocu.convert(jo.getString("tag")));
								currentGroupMapList.addAll(ocu.convert(jo.getString("data")));
								if (!pageType.equals("1")) {
									doShowUserFouceOnly();
								}
								editTag();

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String id = Float.valueOf(clasId).intValue() + "";
							if (id.equals("26") || id.equals("28") || id.equals("31") || id.equals("30")) {
								tagGroupInit();// 26 28 30
												// 31国内出厂价，主营批发价，国内市场价，国际市场价
												// //以Tag为分组依据进行分组
							} else {
								normalGroupInit();
							}
							// expendAdapter.notifyDataSetChanged();

							ptep_lv.onRefreshComplete();
						}

						private void doShowUserFouceOnly() {
							// TODO Auto-generated method stub
							int currentListSize = currentGroupMapList.size();
							for (int i = 0; i < currentListSize; i++) {
								String tempId = ((Map<String, Object>) currentGroupMapList.get(i)
										.get("productTemplate")).get("temp_id").toString();
								Log.e("...", tempId);
								if (!temFouce.isFouced(tempId)) {
									currentGroupMapList.remove(i);
									currentListSize--;
									i--;
									// i--;
								} else {
									Log.e("...", "ddd");
								}
							}

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

									tagList.get(i).put("template", temList);// 将获取到的template
																			// 按标签放在标签列同级别中
								}

							}
						}

					});

		}
	}

}
