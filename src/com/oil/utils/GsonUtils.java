package com.oil.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oil.bean.HotPoint;
import com.oil.bean.NewsClumns;
import com.oil.datamodel.InfoPage;
import com.oil.datamodel.MainPage;
import com.oil.datamodel.NewsListPage;

public class GsonUtils {
	/**
	 * 初始化首页信息内容
	 * 
	 * @param json数据
	 * @return 首页数据模型
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
	 * 初始化资讯信息内容
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
}
