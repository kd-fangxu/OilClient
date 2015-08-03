package com.oil.adapter;

import java.util.HashMap;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oil.bean.Constants;
import com.oil.fragments.ItemFraShangji;
import com.oil.fragments.ItemFragmentData;
import com.oil.fragments.ItemFragmentNews;

/**
 * fragment������
 * 
 * @author user
 *
 */
public abstract class PagerAdapter<T> extends FragmentStatePagerAdapter {
	public PagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public static int TYPE_PRODUCT_DETAILS = 4;
	List<T> itemList;
	int type;

	public PagerAdapter(FragmentManager fm, List<T> itemList, int type) {

		super(fm);
		this.itemList = itemList;
		this.type = type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		// public static int PageType_info = 1;// ��Ѷ
		// public static int PageType_data = 2;// ���ݡ������ݿ�
		// public static int PageType_price = 3;// ���ݡ����۸��
		// public static int pageType_shangji = 4;// �̻�
		switch (type) {
		case 1:// ��Ѷ
			return ItemFragmentNews.getInstance();
		case 2:
			return ItemFragmentData.getInstance(1,
					(HashMap<String, String>) itemList.get(position));
		case 3:
			return ItemFragmentData.getInstance(2,
					(HashMap<String, String>) itemList.get(position));
		case 4:
			// return ProductNewClumnTabFragment.getInstance();
			return new ItemFraShangji(
					(HashMap<String, String>) itemList.get(position));

		default:
			break;
		}
		return null;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub

		return getName(itemList.get(position));

	}

	public abstract String getName(T item);
}
