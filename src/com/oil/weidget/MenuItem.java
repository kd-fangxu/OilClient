package com.oil.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oilclient.R;

public class MenuItem extends LinearLayout {

	ImageView iv_icon;
	TextView tv_content;

	public MenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_mainmenuitem, MenuItem.this);
		iv_icon = (ImageView) this.findViewById(R.id.iv_menuitem_icon);
		tv_content = (TextView) this.findViewById(R.id.tv_menuitem_content);
		// tv_content.setText("contents");
	}

	public void init(int imageResourseID, String menuItemName) {
		iv_icon.setImageResource(imageResourseID);
		tv_content.setText(menuItemName);

	}

	public TextView getTextView() {
		return tv_content;
	}

	public ImageView getImageView() {
		return iv_icon;
	};

	public int getId() {
		return super.getId();
	}
}
