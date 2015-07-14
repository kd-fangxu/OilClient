package com.oil.workmodel;

import java.util.HashMap;
import java.util.List;

/**
 * 用户收藏操作
 * 
 * @author Administrator
 *
 */
public class UserFouceModel {

	private static UserFouceModel model;
	List<HashMap<String, String>> fouceList;

	public List<HashMap<String, String>> getFouceList() {
		return fouceList;
	}

	public void setFouceList(List<HashMap<String, String>> fouceList) {
		this.fouceList = fouceList;
	}

	public static UserFouceModel getInstance() {
		if (model == null) {
			model = new UserFouceModel();
		}
		return model;
	};

	/**
	 * 产品是否已经收藏
	 * 
	 * @param pro_id
	 * @return
	 */
	public boolean isFouced(String pro_id) {
		if (fouceList != null) {
			for (int i = 0; i < fouceList.size(); i++) {
				if (fouceList.get(i).get("pro_id")
						.equals(Float.valueOf(pro_id).intValue() + "")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据id移除收藏
	 * 
	 * @param proId
	 */
	public void removeItemByProId(String proId) {
		if (fouceList != null) {
			for (int i = 0; i < fouceList.size(); i++) {
				if (fouceList.get(i).get("pro_id")
						.equals(Float.valueOf(proId).intValue() + "")) {
					fouceList.remove(i);
				}
			}
		}
	}
}
