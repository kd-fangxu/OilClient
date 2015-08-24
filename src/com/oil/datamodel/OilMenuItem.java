package com.oil.datamodel;

/**
 * menu²Ëµ¥
 * 
 * @author Administrator
 *
 */
public class OilMenuItem {
	String Name;
	int iconId;
	int MenuId;

	public OilMenuItem() {
	};

	/**
	 * 
	 * @param Name
	 * @param iconId
	 * @param MenuId
	 */
	public OilMenuItem(String Name, int iconId, int MenuId) {
		this.Name = Name;
		this.iconId = iconId;
		this.MenuId = MenuId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public int getMenuId() {
		return MenuId;
	}

	public void setMenuId(int menuId) {
		MenuId = menuId;
	}
}
