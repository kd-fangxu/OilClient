package com.oil.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.askerov.dynamicgrid.DynamicGridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.example.oilclient.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.oil.activity.FirstPageShowActivity;
import com.oil.adapter.CheeseDynamicAdapter;
import com.oil.adapter.PagerAdapter;
import com.oil.bean.Constants;
import com.oil.bean.HotPoint;
import com.oil.bean.OilUser;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.weidget.OilContentViewPager;
import com.oil.weidget.PagerSlidingTabStrip;

/**
 * data
 * 
 * @author user
 *
 */
public class TabFragmetLzDataPage extends Fragment implements OnClickListener {
	int tem_type = 0;

	/**
	 * 0:wo 1:data
	 * 
	 * @param type
	 */
	public TabFragmetLzDataPage(int type) {
		this.tem_type = type;
	};

	public static TabFragmetLzDataPage getInstance(int type) {
		return new TabFragmetLzDataPage(type);
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
		initUserFoucePopu();
		initWeidget(view);
		getUserFouce();
		return view;
	}

	private void getUserFouce() {
		// TODO Auto-generated method stub
		String url = Constants.URL_GETUSERFOUCE;
		Log.e("url", url);
		Log.e("cuuid", OilUser.getCurrentUser(getActivity()).getCuuid());
		RequestParams params = new RequestParams();

		params.put("UserId", "1");
		HttpTool.netRequestNoCheck(getActivity(), params,
				new OnReturnListener() {

					@Override
					public void onReturn(String jsString) {
						// TODO Auto-generated method stub
						// Log.e("info", jsString);

						try {
							JSONObject js = new JSONObject(jsString);
							List<Map<String, String>> templeMaplist = new ArrayList<Map<String, String>>();

							templeMaplist
									.addAll((Collection<? extends HashMap<String, String>>) new Gson().fromJson(
											js.getString("productList"),
											new TypeToken<List<HashMap<String, String>>>() {
											}.getType()));
							if (templeMaplist.size() > 0) {
								for (int i = 0; i < templeMaplist.size(); i++) {
									if (checkMapList(templeMaplist.get(i).get(
											"pro_cn_name"))) {

										mapList.add((HashMap<String, String>) templeMaplist
												.get(i));
									}
									;
								}
							}

							pagerAdapter.notifyDataSetChanged();
							psts.notifyDataSetChanged();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

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
							if (proName.equals(mapList.get(j)
									.get("pro_cn_name"))) {
								return false;
							}
						}
						return true;
					}
				}, url, true);
	}

	PagerSlidingTabStrip psts;
	OilContentViewPager ocvp;
	PagerAdapter<HashMap<String, String>> pagerAdapter;
	LinearLayout ll_titlebar;
	ImageView iv_userfouce;
	List<String> titleList;
	List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

	private void initWeidget(View view) {
		// TODO Auto-generated method stub
		titleList = new ArrayList<String>();
		titleList.add("item1");
		titleList.add("item2");
		titleList.add("item3");
		titleList.add("item4");
		pagerAdapter = new PagerAdapter<HashMap<String, String>>(
				getFragmentManager(), mapList, Constants.PageType_data) {

			@Override
			public String getName(HashMap<String, String> item) {
				// TODO Auto-generated method stub
				return item.get("pro_cn_name");
			}

		};
		// switch (tem_type) {
		// case 0:
		// // pagerAdapter = new PagerAdapter(getFragmentManager(), titleList,
		// // Constants.PageType_data);
		// pagerAdapter = new PagerAdapter<HashMap<String, String>>(
		// getFragmentManager(), mapList, Constants.PageType_data) {
		//
		// @Override
		// public String getName(HashMap<String, String> item) {
		// // TODO Auto-generated method stub
		// return item.get("pro_cn_name");
		// }
		//
		// };
		// break;
		// // case 1:
		// // pagerAdapter = new PagerAdapter<String>(getFragmentManager(),
		// // titleList, 3) {
		// //
		// // @Override
		// // public String getName(String item) {
		// // // TODO Auto-generated method stub
		// // return item.toString();
		// // }
		// // };
		// // break;
		// default:
		// break;
		// }

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
			ll_item7, ll_item8;

	private void initUserFoucePopu() {
		// TODO Auto-generated method stub

		List<String> fouceList = new ArrayList<String>();
		fouceList.add("item1");
		fouceList.add("item2");
		fouceList.add("item3");
		fouceList.add("item4");
		fouceList.add("item5");
		fouceList.add("item6");
		fouceList.add("item7");
		fouceList.add("item8");
		fouceList.add("item9");

		View popuView = View.inflate(getActivity(),
				R.layout.view_popu_userfouce, null);
		dgView = (DynamicGridView) popuView.findViewById(R.id.dynamic_grid);
		btn_edit_com = (Button) popuView.findViewById(R.id.btn_edit_com);
		ll_item1 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item1);
		ll_item2 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item2);
		ll_item3 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item3);
		ll_item4 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item4);
		ll_item5 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item5);
		ll_item6 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item6);
		ll_item7 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item7);
		ll_item8 = (LinearLayout) popuView.findViewById(R.id.ll_pro_item8);
		ll_item1.setOnClickListener(this);
		ll_item2.setOnClickListener(this);
		ll_item3.setOnClickListener(this);
		ll_item4.setOnClickListener(this);
		ll_item5.setOnClickListener(this);
		ll_item6.setOnClickListener(this);
		ll_item7.setOnClickListener(this);
		ll_item8.setOnClickListener(this);

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
				// pWindow.showAsDropDown(ll_titlebar, 0, 0, Gravity.top);
				pWindow.showAsDropDown(ll_titlebar, 0,
						-ll_titlebar.getHeight(), Gravity.TOP);
				// pWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
				isPopuShow = true;
			}

			break;
		case R.id.ll_pro_item1:
			Toast.makeText(getActivity(), "on click", Toast.LENGTH_SHORT)
					.show();

			break;
		case R.id.ll_pro_item2:
			break;
		case R.id.ll_pro_item3:
			break;
		case R.id.ll_pro_item4:
			break;
		case R.id.ll_pro_item5:
			break;
		case R.id.ll_pro_item6:
			break;
		case R.id.ll_pro_item7:
			break;
		case R.id.ll_pro_item8:
			break;

		default:
			break;
		}
	}
}
