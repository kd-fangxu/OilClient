package com.oil.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.activity.AppAboutActivty;
import com.oil.activity.UserCentenrActivity;
import com.oil.activity.UserLoginActivity;
import com.oil.activity.UserMsgActivity;
import com.oil.activity.UserSettingActivity;
import com.oil.activity.UserSuggestionActivty;
import com.oil.adapter.MenuAdapter;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;

/**
 * 左侧菜单界面
 * 
 * @author user
 *
 */
public class MenuFragment extends Fragment {
	public static MenuFragment getInstance() {
		MenuFragment menuFragment = new MenuFragment();
		return menuFragment;
	}

	// MenuItem mi_usercenter, mi_msg, mi_usersetting, mi_userabout,
	// mi_usersuggest;
	ListView lv_menu;
	MenuAdapter menuAdapter;
	TextView mUserName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_mainmenu,
				null);
		lv_menu = (ListView) view.findViewById(R.id.lv_menu);
		mUserName=(TextView) view.findViewById(R.id.userName);
		mUserName.setText(getActivity().getSharedPreferences(Constants.USER_INFO_SHARED, Activity.MODE_PRIVATE).getString(Constants.USER_NAME, ""));
		initMenu();
		return view;
	}

	private void initMenu() {
		// TODO Auto-generated method stub
		int[] iconList = new int[] { R.drawable.icon_menu_usercenter,
				R.drawable.icon_menu_msg, R.drawable.icon_menu_setting,
				R.drawable.icon_menu_about, R.drawable.icon_menu_suggestion,
				R.drawable.icon_menu_logout };
		Resources res = getResources();
		String[] menuName = res.getStringArray(R.array.menu);
		menuAdapter = new MenuAdapter(getActivity(), menuName, iconList);
		lv_menu.setAdapter(menuAdapter);
		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					startActivity(new Intent(getActivity(),
							UserCentenrActivity.class));
					break;
				case 1:
					startActivity(new Intent(getActivity(),
							UserMsgActivity.class));
					break;
				case 2:
					startActivity(new Intent(getActivity(),
							UserSettingActivity.class));
					break;
				case 3:
					startActivity(new Intent(getActivity(),
							AppAboutActivty.class));
					break;
				case 4:
					startActivity(new Intent(getActivity(),
							UserSuggestionActivty.class));
					break;
				case 5:
					OilUser.logOut(getActivity());
					// startActivity(new Intent(getActivity(),
					// UserLoginActivity.class));
					getActivity().finish();
					break;

				default:
					break;
				}
			}
		});
	}

	// private void initMenu(MenuItem mi) {
	// // TODO Auto-generated method stub
	// switch (mi.getId()) {
	// case R.id.mi_usercenter:
	// mi.init(R.drawable.ic_launcher, "用户中心");
	// break;
	// case R.id.mi_usermsg:
	// mi.init(R.drawable.ic_launcher, "消息");
	// break;
	// case R.id.mi_usersetting:
	// mi.init(R.drawable.ic_launcher, "设置");
	// break;
	// case R.id.mi_userabout:
	// mi.init(R.drawable.ic_launcher, "关于");
	// break;
	// case R.id.mi_suggest:
	// mi.init(R.drawable.ic_launcher, "用户反馈");
	// break;
	// default:
	// break;
	// }
	// }
}
