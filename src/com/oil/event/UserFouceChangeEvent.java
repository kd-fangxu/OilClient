package com.oil.event;

/**
 * �û���ע�ı��¼�
 * 
 * @author Administrator
 *
 */
public class UserFouceChangeEvent {
	boolean isAdded = false;// �Ƿ�Ϊ��� ���� ɾ��
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
