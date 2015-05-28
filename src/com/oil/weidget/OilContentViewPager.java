package com.oil.weidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class OilContentViewPager extends ViewPager {
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	public OilContentViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(new XYScrollDetector());
		mGestureDetector.setIsLongpressEnabled(false);
		// setOnPageChangeListener(pageChangeListener);
		// TODO Auto-generated constructor stub
	}

	public OilContentViewPager(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(new XYScrollDetector());
		mGestureDetector.setIsLongpressEnabled(false);
		// setOnPageChangeListener(pageChangeListener);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// return true;
		
		return super.onInterceptTouchEvent(event);
//		return super.onInterceptTouchEvent(event)
//				&& mGestureDetector.onTouchEvent(event);

	}

	class XYScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (distanceY != 0 && distanceX != 0) {
			}
//			Log.e("", "distanceY:" + distanceY + "    distanceX:" + distanceX);
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return false;
			}
			return true;
		}
	}
}
