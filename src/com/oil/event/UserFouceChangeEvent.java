package com.oil.event;

import java.util.List;

/**
 * 用户关注改变事件
 * 
 * @author Administrator
 *
 */
public class UserFouceChangeEvent {
	boolean isAdded = false;// 是否为添加 否则 删除
	int changedPosition;
	List<String> addedProIds;// 添加收藏 产品id列

	public List<String> getAddedProIds() {
		return addedProIds;
	}

	public void setAddedProIds(List<String> addedProIds) {
		this.addedProIds = addedProIds;
	}

	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	public int getChangedPosition() {
		return changedPosition;
	}

	public void setChangedPosition(int changedPosition) {
		this.changedPosition = changedPosition;
	}

}
