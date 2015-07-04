//package com.oil.adapter;
//
//import java.util.List;
//import java.util.Random;
//
//import android.content.Context;
//import android.util.SparseArray;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.etsy.android.grid.util.DynamicHeightImageView;
//import com.etsy.android.grid.util.DynamicHeightTextView;
//import com.example.oilclient.R;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.oil.bean.NewsClumns;
//
//public class StaggeredAdapter extends BaseAdapter {
//	List<NewsClumns> contentList;
//	Context context;
//	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
//	Random mRandom;
//
//	// mark ÃÌº”Õº∆¨ª∫¥Ê
//	public StaggeredAdapter(Context context, List<NewsClumns> newsList) {
//		// TODO Auto-generated constructor stub
//		mRandom = new Random();
//
//		this.contentList = (newsList);
//		this.context = context;
//		initImageLoader();
//	}
//
//	DisplayImageOptions options;
//	ImageLoader imageLoader;
//
//	private void initImageLoader() {
//		options = new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.icon_pic_default)
//				.showStubImage(R.drawable.icon_pic_default).cacheInMemory(true)
//				.cacheOnDisc(true).build();
//		imageLoader = ImageLoader.getInstance();
//
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return contentList.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return contentList.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		SaViewHoder sViewHoder = null;
//		if (convertView == null) {
//			convertView = View.inflate(context, R.layout.view_ada_companyinfo,
//					null);
//			sViewHoder = new SaViewHoder();
//			sViewHoder.iv_show = (DynamicHeightImageView) convertView
//					.findViewById(R.id.iv_item_show);
//			sViewHoder.tv_content = (DynamicHeightTextView) convertView
//					.findViewById(R.id.tv_content);
//			convertView.setTag(sViewHoder);
//		}
//		double positionHeight = getPositionRatio(position);
//		sViewHoder = (SaViewHoder) convertView.getTag();
//		// sViewHoder.iv_show.setImageResource(R.drawable.ic_launcher);
//		imageLoader.displayImage(contentList.get(position).getImageresource(),
//				sViewHoder.iv_show, options);
//		sViewHoder.tv_content.setText(contentList.get(position).getTitle());
//		sViewHoder.iv_show.setHeightRatio(positionHeight);
//		// sViewHoder.tv_content.setHeightRatio(positionHeight);
//		return convertView;
//	}
//
//	private double getRandomHeightRatio() {
//		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
//													// the width
//	}
//
//	private double getPositionRatio(final int position) {
//		double ratio = sPositionHeightRatios.get(position, 0.0);
//		// if not yet done generate and stash the columns height
//		// in our real world scenario this will be determined by
//		// some match based on the known height and width of the image
//		// and maybe a helpful way to get the column height!
//		if (ratio == 0) {
//			ratio = getRandomHeightRatio();
//			sPositionHeightRatios.append(position, ratio);
//			// Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
//		}
//		return ratio;
//	}
//
//	class SaViewHoder {
//		DynamicHeightImageView iv_show;
//		DynamicHeightTextView tv_content;
//
//	}
// }
