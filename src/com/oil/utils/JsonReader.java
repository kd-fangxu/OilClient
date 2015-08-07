package com.oil.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.oilclient.R;

public class JsonReader {

	/**
	 * 读取所有地区
	 * 
	 * @return
	 */
	public static ArrayList<String[]> getAreas(Context context) {
		ArrayList<String[]> areas = new ArrayList<String[]>();
		String[] area = new String[2];
		area[0] = "0";
		area[1] = "全国";
		areas.add(area);
		try {

			InputStream inputStream = context.getResources().openRawResource(
					R.raw.area);
			InputStreamReader reader = new InputStreamReader(inputStream, "GBK");
			BufferedReader bReader = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();

			String s = "";
			while ((s = bReader.readLine()) != null) {
				sb.append(s);
			}

			JSONArray array = new JSONArray(sb.toString());
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				area = new String[2];
				area[0] = obj.getString("cityid");
				area[1] = obj.getString("cityname");

				areas.add(area);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return areas;
	}

	public static ArrayList<String[]> getProvinces(String parentId,
			Context context) {
		ArrayList<String[]> provinces = new ArrayList<String[]>();
		String[] province = new String[2];

		try {
			InputStream inputStream = context.getResources().openRawResource(
					R.raw.province);
			InputStreamReader reader = new InputStreamReader(inputStream, "GBK");
			BufferedReader bReader = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();

			String s;
			s = "";
			while ((s = bReader.readLine()) != null) {
				sb.append(s);
			}

			JSONArray array = new JSONArray(sb.toString());
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				province = new String[3];
				province[0] = obj.getString("cityid");
				province[1] = obj.getString("cityname");
				province[2] = "false";
				if (!parentId.equals("-1")
						&& obj.getString("parentid").equals(parentId)) {

					provinces.add(province);
				} else if (parentId.equals("-1")) {// 全部省份
					provinces.add(province);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provinces;
	}

}
