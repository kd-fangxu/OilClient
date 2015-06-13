package com.oil.event;

/**
 * 用户关注改变事件
 * 
 * @author Administrator
 *
 */
public class UserFouceChangeEvent {
	boolean isAdded = false;// 是否为添加 否则 删除
	int changedPosition;

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
