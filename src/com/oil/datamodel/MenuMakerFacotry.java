package com.oil.datamodel;

import java.util.ArrayList;
import java.util.List;

import com.example.oilclient.R;

/**
 * menu������
 * 
 * @author Administrator
 *
 */
public class MenuMakerFacotry {

	public static List<OilMenuItem> getMenuList() {
		OilMenuItem oilMenuItem;
		List<OilMenuItem> itemList = new ArrayList<OilMenuItem>();
		oilMenuItem = new OilMenuItem("��Ա����", R.drawable.icon_menu_usercenter,
				1);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("��������", R.drawable.icon_menu_mesion, 2);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("��Ϣ", R.drawable.icon_menu_msg, 3);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("����", R.drawable.icon_menu_setting, 4);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("����", R.drawable.icon_menu_about, 5);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("�������", R.drawable.icon_menu_suggestion,
				6);
		itemList.add(oilMenuItem);
		oilMenuItem = new OilMenuItem("ע��", R.drawable.icon_menu_logout, 7);
		itemList.add(oilMenuItem);
		return itemList;

	}
}
