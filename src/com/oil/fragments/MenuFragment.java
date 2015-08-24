package com.oil.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.activity.AppAboutActivty;
import com.oil.activity.UserCentenrActivity;
import com.oil.activity.UserMsgActivity;
import com.oil.activity.UserSettingActivity;
import com.oil.activity.UserSuggestionActivty;
import com.oil.adapter.MenuAdapter;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.datamodel.MenuMakerFacotry;
import com.oil.datamodel.OilMenuItem;
import com.oil.dialogs.CommontitleDialog;
import com.oil.dialogs.CommontitleDialog.onComDialogBtnClick;
import com.oil.weidget.CircleImageView;

/**
 * ï¿½ï¿½ï¿½Ëµï¿½ï¿½ï¿½ï¿½ï¿½
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
	CircleImageView civ_ucheader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_mainmenu,
				null);
		lv_menu = (ListView) view.findViewById(R.id.lv_menu);
		mUserName = (TextView) view.findViewById(R.id.userName);
		mUserName.setText(getActivity().getSharedPreferences(
				Constants.USER_INFO_SHARED, Activity.MODE_PRIVATE).getString(
				Constants.USER_NAME, ""));
		civ_ucheader = (CircleImageView) view.findViewById(R.id.civ_userheader);
		civ_ucheader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),
						UserCentenrActivity.class));
			}
		});
		initMenu();
		return view;
	}

	private void initMenu() {
		// TODO Auto-generated method stub

		// int[] iconList = new int[] { R.drawable.icon_menu_usercenter,
		// R.drawable.icon_menu_msg, R.drawable.icon_menu_mesion,
		// R.drawable.icon_menu_setting, R.drawable.icon_menu_about,
		// R.drawable.icon_menu_suggestion, R.drawable.icon_menu_logout };
		// Resources res = getResources();
		// String[] menuName = res.getStringArray(R.array.menu);

		menuAdapter = new MenuAdapter(getActivity(),
				MenuMakerFacotry.getMenuList());
		lv_menu.setAdapter(menuAdapter);
		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int menuId = ((OilMenuItem) lv_menu.getAdapter().getItem(
						position)).getMenuId();
				switch (menuId) {
				case 1:
					startActivity(new Intent(getActivity(),
							UserCentenrActivity.class));
					break;
				case 2:

					break;
				case 3:
					startActivity(new Intent(getActivity(),
							UserMsgActivity.class));
					break;
				case 4:
					startActivity(new Intent(getActivity(),
							UserSettingActivity.class));
					break;
				case 5:
					startActivity(new Intent(getActivity(),
							AppAboutActivty.class));
					break;
				case 6:
					startActivity(new Intent(getActivity(),
							UserSuggestionActivty.class));
					break;
				case 7:// ×¢Ïú
						// OilUser.logOut(getActivity());
						// // startActivity(new Intent(getActivity(),
						// // UserLoginActivity.class));
						// getActivity().finish();
					final CommontitleDialog logoutDialog = new CommontitleDialog(
							getActivity());
					logoutDialog.Init("ÌáÊ¾", "È·¶¨Òª×¢ÏúÂð£¿",
							new onComDialogBtnClick() {

								@Override
								public void onConfirmClick() {
									// TODO Auto-generated method stub
									OilUser.logOut(getActivity());
									// startActivity(new Intent(getActivity(),
									// UserLoginActivity.class));
									getActivity().finish();
								}

								@Override
								public void onCancelClick() {
									// TODO Auto-generated method stub
									logoutDialog.disMiss();
								}
							});
					logoutDialog.show();
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
	// mi.init(R.drawable.ic_launcher, "ï¿½Ã»ï¿½ï¿½ï¿½ï¿½ï¿½");
	// break;
	// case R.id.mi_usermsg:
	// mi.init(R.drawable.ic_launcher, "ï¿½ï¿½Ï¢");
	// break;
	// case R.id.mi_usersetting:
	// mi.init(R.drawable.ic_launcher, "ï¿½ï¿½ï¿½ï¿½");
	// break;
	// case R.id.mi_userabout:
	// mi.init(R.drawable.ic_launcher, "ï¿½ï¿½ï¿½ï¿½");
	// break;
	// case R.id.mi_suggest:
	// mi.init(R.drawable.ic_launcher, "ï¿½Ã»ï¿½ï¿½ï¿½ï¿½ï¿½");
	// break;
	// default:
	// break;
	// }
	// }

}
