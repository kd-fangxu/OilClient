package com.oil.event;

import java.util.List;

/**
 * �û���ע�ı��¼�
 * 
 * @author Administrator
 *
 */
public class UserFouceChangeEvent {
	boolean isAdded = false;// �Ƿ�Ϊ��� ���� ɾ��
	int changedPosition;
	List<String> addedProIds;// ����ղ� ��Ʒid��

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
