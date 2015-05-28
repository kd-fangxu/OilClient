package com.oil.fragments;

import java.util.ArrayList;
import java.util.List;

import org.askerov.dynamicgrid.DynamicGridView;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.oil.adapter.CheeseDynamicAdapter;
import com.oil.adapter.PagerAdapter;
import com.oil.bean.Constants;
import com.oil.weidget.OilContentViewPager;
import com.oil.weidget.PagerSlidingTabStrip;

/**
 * 数据界面
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
		return view;
	}

	PagerSlidingTabStrip psts;
	OilContentViewPager ocvp;
	PagerAdapter<String> pagerAdapter;
	LinearLayout ll_titlebar;
	ImageView iv_userfouce;
	List<String> titleList;

	private void initWeidget(View view) {
		// TODO Auto-generated method stub
		titleList = new ArrayList<String>();
		titleList.add("石油");
		titleList.add("燃料油");
		titleList.add("沥青");
		titleList.add("润滑油");
		switch (tem_type) {
		case 0:
			// pagerAdapter = new PagerAdapter(getFragmentManager(), titleList,
			// Constants.PageType_data);
			pagerAdapter = new PagerAdapter<String>(getFragmentManager(),
					titleList, Constants.PageType_data) {

				@Override
				public String getName(String item) {
					// TODO Auto-generated method stub
					return item.toString();
				}
			};
			break;
		case 1:
			pagerAdapter = new PagerAdapter<String>(getFragmentManager(),
					titleList, 3) {

				@Override
				public String getName(String item) {
					// TODO Auto-generated method stub
					return item.toString();
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
