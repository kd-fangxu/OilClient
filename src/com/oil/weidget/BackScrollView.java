package com.oil.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class BackScrollView extends ScrollView {
	private GestureDetector mGestureDetector;

	public BackScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(new XYScrollDetector());
		mGestureDetector.setIsLongpressEnabled(false);
	}

	public BackScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mGestureDetector = new GestureDetector(new XYScrollDetector());
		mGestureDetector.setIsLongpressEnabled(false);
	}

	public BackScrollView(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(new XYScrollDetector());
		mGestureDetector.setIsLongpressEnabled(false);
		// TODO Auto-generated constructor stub
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// // TODO Auto-generated method stub
	// return super.onInterceptTouchEvent(ev)
	// && mGestureDetector.onTouchEvent(ev);
	// }

	class XYScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (distanceY != 0 && distanceX != 0) {
			}
			// Log.e("", "distanceY:" + distanceY + "    distanceX:" +
			// distanceX);
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return false;
			}
			return true;
		}
	}
}
