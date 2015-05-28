package com.oil.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// public static Integer[] getScreenData(Activity activity) {
	// DisplayMetrics mDisplayMetrics = new DisplayMetrics();
	//
	// activity.getWindowManager().getDefaultDisplay()
	// .getMetrics(mDisplayMetrics);
	// int Winth = mDisplayMetrics.widthPixels;
	// int Height = mDisplayMetrics.heightPixels;
	// Integer[] data = new Integer[] { Winth, Height };
	// return data;
	// }

	public static int getScreenWidth(Context context) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();

		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
		int Width = mDisplayMetrics.widthPixels;
		int Height = mDisplayMetrics.heightPixels;
		return Width;
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();

		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
//		int Width = mDisplayMetrics.widthPixels;
		 int Height = mDisplayMetrics.heightPixels;
		return Height;
	}
}
