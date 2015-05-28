package com.oil.datamodel;

import java.util.List;

public class NewsListPage {
	private String PageName;// 模块名
	private List<Object> pointList;// 新闻列表

	public String getPageName() {
		return PageName;
	}

	public void setPageName(String pageName) {
		PageName = pageName;
	}

	public List<Object> getPointList() {
		return pointList;
	}

	public void setPointList(List<Object> pointList) {
		this.pointList = pointList;
	}
}
