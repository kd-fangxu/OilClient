package com.oil.weidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oilclient.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oil.adapter.HeadPageAdapter;
import com.oil.bean.HotPoint;
import com.viewpagerindicator.CirclePageIndicator;

public class HotHeaderPager extends LinearLayout {
	Handler handler;
	int count = 0;
	Context context;

	public HotHeaderPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initImageLoader();
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_hotheader, HotHeaderPager.this);
		pager = (FouceViewPager) findViewById(R.id.hhp_pager);
		cpIndicator = (CirclePageIndicator) findViewById(R.id.cp_indicator);
		tv_title = (TextView) findViewById(R.id.tv_title);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:
					count++;
					pager.setCurrentItem(count % pager.getAdapter().getCount(),
							true);
					break;

				default:
					break;
				}
			}
		};
	}

	FouceViewPager pager;
	CirclePageIndicator cpIndicator;
	TextView tv_title;

	// public HotHeaderPager(Context context, AttributeSet attrs) {
	// super(context, attrs);
	// // TODO Auto-generated constructor stub
	// LayoutInflater inflater = (LayoutInflater) getContext()
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// inflater.inflate(R.layout.view_hotheader, HotHeaderPager.this);
	// pager = (FouceViewPager) findViewById(R.id.hhp_pager);
	// cpIndicator = (CirclePageIndicator) findViewById(R.id.cp_indicator);
	// tv_title = (TextView) findViewById(R.id.tv_title);
	// }
	HeadPageAdapter hpAdapter;
	List<HotPoint> hotList;
	List<View> viewList = new ArrayList<View>();

	public void setHotList(List<HotPoint> hotList) {
		this.hotList = hotList;
		// init(hpAdapter);
		initWeidget();
	}

	DisplayImageOptions options;
	ImageLoader imageLoader;

	private void initImageLoader() {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.icon_pic_default)
				.showStubImage(R.drawable.icon_pic_default).cacheInMemory(true)
				.cacheOnDisc(true).build();
		imageLoader = ImageLoader.getInstance();

	}

	public void init(HeadPageAdapter hpAdapter) {
		if (null != hpAdapter) {
			this.hpAdapter = hpAdapter;
		}
		pager.setAdapter(this.hpAdapter);
		cpIndicator.setViewPager(pager);

		beginShow();
	}

	private void initWeidget() {
		hpAdapter = new HeadPageAdapter(viewList, context);
		pager.setAdapter(this.hpAdapter);
		cpIndicator.setViewPager(pager);

		beginShow();
	}

	public void update() {
		if (null != hotList) {
			hpAdapter.getContentList().clear();
			for (int i = 0; i < hotList.size(); i++) {
				ImageView iv_content = new ImageView(context);
				iv_content.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				iv_content.setScaleType(ScaleType.FIT_XY);
				// iv_content.setImageResource(R.drawable.icon_default);
				imageLoader.displayImage(hotList.get(i).getImagelink(),
						iv_content, options);
				hpAdapter.getContentList().add(iv_content);
			}
			hpAdapter.notifyDataSetChanged();
		}
	}

	Timer showTimer;

	public void beginShow() {
		showTimer = new Timer();
		showTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		}, 3000, 3000);
	}
}
