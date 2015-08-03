package com.oil.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.askerov.dynamicgrid.DynamicGridView;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.example.oilclient.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.oil.activity.ProductSelectActivity;
import com.oil.adapter.CheeseDynamicAdapter;
import com.oil.adapter.PagerAdapter;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.event.UserFouceChangeEvent;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.weidget.OilContentViewPager;
import com.oil.weidget.PagerSlidingTabStrip;
import com.oil.workmodel.UserFouceModel;

import de.greenrobot.event.EventBus;

/**
 * data
 * 
 * @author user
 *
 */
public class TabFragmetLzDataPage extends Fragment implements OnClickListener {
	int tem_type = 0;

	/**
	 * 0:jia ge ku 价格库 1:shujuku 数据库
	 * 
	 * @param type
	 */
	public TabFragmetLzDataPage(int type) {
		this.tem_type = type;
		// Log
	};

	/**
	 * 0:jia ge ku 价格库 1:shujuku 数据库
	 * 
	 * @param type
	 */
	public static TabFragmetLzDataPage getInstance(int type) {
		return new TabFragmetLzDataPage(type);
	}

	public TabFragmetLzDataPage() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_tab_third,
				null);
		ll_titlebar = (LinearLayout) view.findViewById(R.id.ll_titlebar);
		iv_userfouce = (ImageView) view.findViewById(R.id.iv_userfouce_control);
		psts = (PagerSlidingTabStrip) view.findViewById(R.id.ps_indicator);
		psts.setTextColorResource(R.color.whitesmoke);
		ocvp = (OilContentViewPager) view.findViewById(R.id.vp_content);
		iv_userfouce.setOnClickListener(this);
		// initUserFoucePopu();
		initWeidget(view);
		getUserFouce();
		EventBus.getDefault().register(this);

		return view;
	}

	boolean isNeedUpdate = false;
	UserFouceChangeEvent event;

	public void onEvent(UserFouceChangeEvent event) {
		this.event = event;
		int changedPosition = event.getChangedPosition();
		if (event.isAdded()) {
			// tianjia
			isNeedUpdate = true;
			// pWindow.dismiss();
			getUserFouce();

		} else {
			// shanchu
			fouceRemoveChanged(changedPosition, 0);
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (isNeedUpdate && event != null) {
			// fouceAdd();
			if (pWindow != null) {
				pWindow.dismiss();
			}

			getUserFouce();
		}
		super.onResume();

	}

	// private void fouceAdd(List<String> addedProIds) {
	// // TODO Auto-generated method stub
	// for (int i = 0; i < addedProIds.size(); i++) {
	// int proId = Double.valueOf(addedProIds.get(i)).intValue();
	// String userId = OilUser.getCurrentUser(getActivity()).getCuuid();
	// String url = Constants.URL_USERFOUCECHANGE + "/" + userId + "/"
	// + proId + "/" + 1;
	// HttpTool.netRequestNoCheck(getActivity(), null, null, url, false);
	// }
	//
	// getUserFouce();
	// isNeedUpdate = false;
	// }

	private void fouceRemoveChanged(int changedPosition, int actionType) {
		String proId = mapList.remove(changedPosition).get("pro_id").toString();
		String userId = OilUser.getCurrentUser(getActivity()).getCuuid();
		String url = Constants.URL_USERFOUCECHANGE + "/" + userId + "/" + proId
				+ "/" + 0;
		HttpTool.netRequestNoCheck(getActivity(), null, null, url, false);
		pagerAdapter.notifyDataSetChanged();
		psts.notifyDataSetChanged();

	}

	/**
	 * 获取用户收藏
	 */
	private void getUserFouce() {
		// TODO Auto-generated method stub
		String url = Constants.URL_GETUSERFOUCE;
		// Log.e("url", url);
		// Log.e("cuuid", OilUser.getCurrentUser(getActivity()).getCuuid());
		RequestParams params = new RequestParams();

		params.put("UserId", OilUser.getCurrentUser(getActivity()).getCuuid());
		HttpTool.netRequestNoCheck(getActivity(), params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						// Log.e("info", jsString);

						JSONObject js;
						try {
							js = new JSONObject(jsString);
							List<Map<String, String>> templeMaplist = new ArrayList<Map<String, String>>();

							templeMaplist
									.addAll((Collection<? extends HashMap<String, String>>) new Gson().fromJson(
											js.getString("productList"),
											new TypeToken<List<HashMap<String, String>>>() {
											}.getType()));
							if (templeMaplist.size() > 0) {
								mapList.clear();
								for (int i = 0; i < templeMaplist.size(); i++) {
									if (checkMapList(templeMaplist.get(i).get(
											"pro_cn_name"))) {

										mapList.add((HashMap<String, String>) templeMaplist
												.get(i));

									}
									;
								}
								UserFouceModel.getInstance().setFouceList(
										mapList);
							}

							pagerAdapter.notifyDataSetChanged();
							psts.notifyDataSetChanged();
							if (cDynamicAdapter != null) {
								cDynamicAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, url, false);
	}

	/**
	 * 校验重复性
	 * 
	 * @param proName
	 * @return
	 */
	private boolean checkMapList(String proName) {
		// TODO Auto-generated method stub
		for (int j = 0; j < mapList.size(); j++) {
			if (proName.equals(mapList.get(j).get("pro_cn_name"))) {
				return false;
			}
		}
		return true;
	}

	PagerSlidingTabStrip psts;
	OilContentViewPager ocvp;
	PagerAdapter<HashMap<String, String>> pagerAdapter;
	LinearLayout ll_titlebar;
	ImageView iv_userfouce;
	List<String> titleList;
	List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

	public void initWeidget(View view) {
		// TODO Auto-generated method stub
		switch (tem_type) {
		case 1:// 数据库
			pagerAdapter = new PagerAdapter<HashMap<String, String>>(
					getFragmentManager(), mapList, Constants.PageType_data) {

				@Override
				public String getName(HashMap<String, String> item) {
					// TODO Auto-generated method stub
					return item.get("pro_cn_name");
				}

			};
			break;
		case 0:// 价格库
			pagerAdapter = new PagerAdapter<HashMap<String, String>>(
					getFragmentManager(), mapList, Constants.PageType_price) {

				@Override
				public String getName(HashMap<String, String> item) {
					// TODO Auto-generated method stub
					return item.get("pro_cn_name");
				}

			};
			break;
		default:
			break;
		}

		ocvp.setAdapter(pagerAdapter);
		psts.setViewPager(ocvp);
	}

	private void initDemoData() {
		// TODO Auto-generated method stub

	}

	PopupWindow pWindow;
	DynamicGridView dgView;
	CheeseDynamicAdapter cDynamicAdapter;
	Button btn_edit_com;
	LinearLayout ll_item1, ll_item2, ll_item3, ll_item4, ll_item5, ll_item6,
			ll_item7, ll_item8, ll_item9;;
	List<String> fouceList = new ArrayList<String>();

	private void initUserFoucePopu() {
		// TODO Auto-generated method stub
		fouceList.clear();
		for (int i = 0; i < mapList.size(); i++) {
			fouceList.add(mapList.get(i).get("pro_cn_name"));
		}
		View popuView = View.inflate(getActivity(),
				R.layout.view_popu_userfouce, null);
		dgView = (DynamicGridView) popuView.findViewById(R.id.dynamic_grid);
		dgView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					dgView.requestDisallowInterceptTouchEvent(true);
					break;

				default:
					break;
				}
				return false;
			}
		});

		btn_edit_com = (Button) popuView.findViewById(R.id.btn_edit_com);
		ll_item1 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item1);
		ll_item2 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item2);
		ll_item3 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item3);
		ll_item4 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item4);
		ll_item5 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item5);
		ll_item6 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item6);
		ll_item7 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item7);
		ll_item8 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item8);
		ll_item9 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item9);
		ll_item1.setOnClickListener(this);
		ll_item2.setOnClickListener(this);
		ll_item3.setOnClickListener(this);
		ll_item4.setOnClickListener(this);
		ll_item5.setOnClickListener(this);
		ll_item6.setOnClickListener(this);
		ll_item7.setOnClickListener(this);
		ll_item8.setOnClickListener(this);
		ll_item9.setOnClickListener(this);
		cDynamicAdapter = new CheeseDynamicAdapter(getActivity(), fouceList, 3);
		dgView.setAdapter(cDynamicAdapter);

		dgView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				dgView.startEditMode(position);
				btn_edit_com.setVisibility(view.VISIBLE);
				return true;
			}
		});
		btn_edit_com.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dgView.stopEditMode();
				btn_edit_com.setVisibility(View.GONE);
			}
		});
		pWindow = new PopupWindow(popuView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		pWindow.setFocusable(true);
		pWindow.setBackgroundDrawable(new ColorDrawable(0));
		pWindow.setOutsideTouchable(true);
		pWindow.setAnimationStyle(R.style.popwin_anim_style);
		pWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				isPopuShow = false;
			}
		});

	}

	boolean isPopuShow = false;

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_userfouce_control:
			if (isPopuShow) {
				pWindow.dismiss();

			} else {

				// pWindow.showAsDropDown(ll_titlebar, 0,
				// -ll_titlebar.getHeight(), Gravity.TOP);
				initUserFoucePopu();
				pWindow.showAsDropDown(ll_titlebar);
				updataUserfouce();

				isPopuShow = true;
			}

			break;
		case R.id.ll_pro_item1:
			proSelect(1.0 + "");

			break;
		case R.id.ll_pro_item2:
			proSelect(2.0 + "");
			break;
		case R.id.ll_pro_item3:
			proSelect(3.0 + "");
			break;
		case R.id.ll_pro_item4:
			proSelect(4.0 + "");
			break;
		case R.id.ll_pro_item5:
			proSelect(8.0 + "");
			break;
		case R.id.ll_pro_item6:
			proSelect(5.0 + "");
			break;
		case R.id.ll_pro_item7:
			proSelect(6.0 + "");
			break;
		case R.id.ll_pro_item8:
			proSelect(9.0 + "");
			break;
		case R.id.ll_pro_item9:
			proSelect(7.0 + "");
			break;
		default:
			break;
		}
	}

	public void proSelect(String wang_id) {
		Intent intent = new Intent(getActivity(), ProductSelectActivity.class);
		intent.putExtra("wangId", wang_id);
		startActivity(intent);
	}

	private void updataUserfouce() {
		// TODO Auto-generated method stub
		cDynamicAdapter.notifyDataSetChanged();
	}
}
