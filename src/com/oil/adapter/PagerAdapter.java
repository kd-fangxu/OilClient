package com.oil.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oil.fragments.ItemFragmentData;
import com.oil.fragments.ItemFragmentNews;
import com.oil.fragments.ProductNewClumnTabFragment;

/**
 * fragment  ≈‰¿‡
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

		switch (type) {
		case 1:
			return ItemFragmentNews.getInstance();
		case 2:
			return ItemFragmentData.getInstance(0);
		case 3:
			return ItemFragmentData.getInstance(1);
		case 4:
			return ProductNewClumnTabFragment.getInstance();

		default:
			break;
		}
		return null;
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
