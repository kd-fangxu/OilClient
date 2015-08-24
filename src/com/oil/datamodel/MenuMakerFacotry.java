package com.oil.datamodel;

import java.util.ArrayList;
import java.util.List;

import com.example.oilclient.R;

/**
 * menu构造器
 * 
 * @author Administrator
 *
 */
public class MenuMakerFacotry {

	public static List<OilMenuItem> getMenuList() {
		OilMenuItem oilMenuItem;
		List<OilMenuItem> itemList = new ArrayList<OilMenuItem>();
		oilMenuItem = new OilMenuItem("会员中心", R.drawable.icon_menu_usercenter,
				1);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("积分任务", R.drawable.icon_menu_mesion, 2);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("消息", R.drawable.icon_menu_msg, 3);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("设置", R.drawable.icon_menu_setting, 4);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("关于", R.drawable.icon_menu_about, 5);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("意见反馈", R.drawable.icon_menu_suggestion,
				6);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("注销", R.drawable.icon_menu_logout, 7);
		itemList.add(oilMenuItem);
		return itemList;

	}
}
