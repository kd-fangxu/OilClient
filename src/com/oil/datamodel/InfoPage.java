package com.oil.datamodel;

import java.util.HashMap;
import java.util.List;

import com.oil.bean.HotPoint;
import com.oil.bean.NewsClumns;

public class InfoPage {
	private List<HotPoint> pointList;
	private List<NewsClumns> newsList;
	private List<HashMap<String, Object>> adMapList;

	public List<HashMap<String, Object>> getAdMapList() {
		return adMapList;
	}

	public void setAdMapList(List<HashMap<String, Object>> adMapList) {
		this.adMapList = adMapList;
	}

	public List<HotPoint> getPointList() {
		return pointList;
	}

	public void setPointList(List<HotPoint> pointList) {
		this.pointList = pointList;
	}

	public List<NewsClumns> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsClumns> newsList) {
		this.newsList = newsList;
	}
}
