package com.oil.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oil.datamodel.NewsListPage;

public class GsonParserFactory {

	public NewsListPage getNewsListPage(String json) {
		NewsListPage nlisListPage = new NewsListPage();
		List<Object> hotPointList;
		Gson gson = new Gson();
		HashMap<String, Object> map = gson.fromJson(json,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		for (String key : map.keySet()) {
			if (key.equals("name")) {
				nlisListPage.setPageName((String) map.get(key));
			} else if (key.equals("newslist")) {
				hotPointList = gson.fromJson(gson.toJson(map.get(key)),
						new TypeToken<List<Object>>() {
						}.getType());
				if (null != hotPointList) {
					nlisListPage.setPointList(hotPointList);
				}
			}
		}
		return nlisListPage;
	}

	/**
	 * 解析产品数据详情界面
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> getProDataDatails(String json) {
		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		try {
			JSONObject js = new JSONObject(json);
			if (js.has("data")) {
				Gson gson = new Gson();
				mapList.addAll((Collection<? extends HashMap<String, Object>>) gson
						.fromJson(js.getString("data"),
								new TypeToken<List<HashMap<String, Object>>>() {
								}.getType()));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapList;
	}

}
