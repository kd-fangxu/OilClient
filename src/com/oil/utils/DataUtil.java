package com.oil.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

public class DataUtil {

	/**
	 * ��֤�����Ƿ��쳣
	 * 
	 * @param isneedtoshow
	 * @param content
	 * @param context
	 * @return
	 */
	public static boolean CheckJson(boolean isneedtoshow, String content,
			Context context) {

		if (null != content
				&& (content.contains("stat") || content.contains("login"))) {
			JSONObject js = null;
			try {
				js = new JSONObject(content);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (js == null) {
				if (isneedtoshow) {
					Toast.makeText(context, "�����쳣!", Toast.LENGTH_SHORT).show();
				}
				return false;
			} else {
				return true;
			}

		} else {
			if (isneedtoshow)
				Toast.makeText(context, "�����쳣!", Toast.LENGTH_SHORT).show();
		}
		return false;

	}
}
