package com.oil.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.oil.datamodel.OilMenuItem;

public class MenuAdapter extends BaseAdapter {
	Context context;

	// String[] menuName;
	// int[] iconList;

	// public MenuAdapter(Context context, String[] menuName, int[] iconList) {
	// // TODO Auto-generated constructor stub
	// this.context = context;
	// // this.menuName = menuName;
	// // this.iconList = iconList;
	// }

	List<OilMenuItem> menuList;

	public MenuAdapter(Context context, List<OilMenuItem> menuList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.menuList = menuList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MenuViewHolder mViewHolder = null;
		if (convertView == null) {
			mViewHolder = new MenuViewHolder();
			convertView = View.inflate(context, R.layout.view_mainmenuitem,
					null);
			mViewHolder.tv_menu_content = (TextView) convertView
					.findViewById(R.id.tv_menuitem_content);
			mViewHolder.iv_menu_icon = (ImageView) convertView
					.findViewById(R.id.iv_menuitem_icon);
			convertView.setTag(mViewHolder);
		}
		mViewHolder = (MenuViewHolder) convertView.getTag();
		mViewHolder.tv_menu_content.setText(menuList.get(position).getName());
		mViewHolder.iv_menu_icon.setImageResource(menuList.get(position)
				.getIconId());
		return convertView;
	}

	class MenuViewHolder {

		TextView tv_menu_content;
		ImageView iv_menu_icon;
	}
}
