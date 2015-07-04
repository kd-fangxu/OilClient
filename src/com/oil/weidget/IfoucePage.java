package com.oil.weidget;

public interface IfoucePage<T> {
	public String getCurrentImageUrl(T t);

	public void onItemClick(T t);

}
