package com.oil.datamodel;

import java.util.HashMap;
import java.util.List;

import com.oil.bean.HotPoint;
import com.oil.bean.NewsClumns;

/**
 * Ê×Ò³·ºÐÍ
 * 
 * @author user
 *
 */
public class MainPage {
	private HashMap<String, Object> hotPointMap;
	private List<NewsClumns> newsList;
	private List<HotPoint> hotList;

	public List<HotPoint> getHotList() {
		return hotList;
	}

	public void setHotList(List<HotPoint> hotList) {
		this.hotList = hotList;
	}

	public HashMap<String, Object> getHotPointMap() {
		return hotPointMap;
	}

	public void setHotPointMap(HashMap<String, Object> hotPointMap) {
		this.hotPointMap = hotPointMap;
	}

	public List<NewsClumns> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsClumns> newsList) {
		this.newsList = newsList;
	}
}
