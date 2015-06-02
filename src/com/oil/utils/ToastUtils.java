package com.oil.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	private static Toast toast;
	private static ToastUtils toastUtils;

	@SuppressLint("ShowToast")
	public static ToastUtils getInstance(Context context) {
		if (null == toast) {
			toastUtils = new ToastUtils();
			toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
		return toastUtils;
	}

	public void showText(String contents) {
		toast.setText(contents);
		toast.show();
	};
}
