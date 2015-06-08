package com.oil.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oil.bean.HotPoint;
import com.oil.bean.NewsClumns;
import com.oil.datamodel.InfoPage;
import com.oil.datamodel.MainPage;
import com.oil.datamodel.NewsListPage;

public class GsonParserFactory {
	/**
	 * ��ʼ����ҳ��Ϣ����
	 * 
	 * @param json����
	 * @return ��ҳ����ģ��
	 */
	@SuppressWarnings("unchecked")
	public MainPage converMainPage(String jsonContents) {
		MainPage mPage = new MainPage();
		List<NewsClumns> newsList = null;
		List<HotPoint> hotList = null;
		HashMap<String, Object> hotMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		System.out.println(jsonContents);
		HashMap<String, Object> map = gson.fromJson(jsonContents,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		for (String key : map.keySet()) {
			if (key.equals("hotpoints")) {
				// hotMap.putAll((Map<? extends String, ? extends Object>) map
				// .get(key));
				hotList = (List<HotPoint>) (gson.fromJson(gson
						.toJson(((Map<? extends String, ? extends Object>) map
								.get(key)).get("contents")),
						new TypeToken<List<HotPoint>>() {
						}.getType()));
				if (null != hotList) {
					// mPage.setHotPointMap(hotMap);
					mPage.setHotList(hotList);
				}
			}
			if (key.equals("columns")) {
				// newsList = (List<NewsClumns>) map.get(key);
				newsList = gson.fromJson(gson.toJson(map.get(key)),
						new TypeToken<List<NewsClumns>>() {
						}.getType());
				if (null != newsList) {
					mPage.setNewsList(newsList);
				}
				// System.out.println(newsList);
			}
		}
		return mPage;
	}

	/**
	 * ��ʼ����Ѷ��Ϣ����
	 * 
	 * @param json
	 * @return
	 */
	public InfoPage getInfoPage(String json) {
		InfoPage infoPage = new InfoPage();
		// List<HashMap<String, Object>> adMapList = null;
		List<HotPoint> pointList = null;
		List<NewsClumns> newsList = null;
		Gson gson = new Gson();
		HashMap<String, Object> map = gson.fromJson(json,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		for (String key : map.keySet()) {
			if (key.equals("hotpoint")) {
				pointList = gson.fromJson(gson.toJson(map.get(key)),
						new TypeToken<List<HotPoint>>() {
						}.getType());
				;
				if (null != pointList) {
					infoPage.setPointList(pointList);
				}
			}
			if (key.equals("columns")) {
				newsList = gson.fromJson(gson.toJson(map.get(key)),
						new TypeToken<List<NewsClumns>>() {
						}.getType());
				if (null != newsList) {
					infoPage.setNewsList(newsList);
				}
			}
		}
		return infoPage;
	};

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
	 * ������Ʒ�����������
	 * 
	 * @param json
	 * @return
	 */
	public List<HashMap<String, Object>> getProDataDatails(String json) {
		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		try {
			JSONObject js = new JSONObject(json);
			Gson gson = new Gson();
			mapList.addAll((Collection<? extends HashMap<String, Object>>) gson
					.fromJson(js.getString("data"),
							new TypeToken<List<HashMap<String, Object>>>() {
							}.getType()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapList;
	}

}
