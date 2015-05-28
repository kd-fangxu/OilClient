package com.oil.datamodel;

import java.util.HashMap;
import java.util.List;

/**
 * 数据页模型
 * 
 * @author user
 *
 */
public class DataPage {
	HashMap<String, List<Object>> dataMap;
	List<HashMap<String, String>> titleList;

	public HashMap<String, List<Object>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String, List<Object>> dataMap) {
		this.dataMap = dataMap;
	}

	public List<HashMap<String, String>> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<HashMap<String, String>> titleList) {
		this.titleList = titleList;
	}

}
