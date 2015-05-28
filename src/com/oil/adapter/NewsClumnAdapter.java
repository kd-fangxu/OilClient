package com.oil.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oilclient.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oil.bean.NewsClumns;

public class NewsClumnAdapter extends BaseAdapter {
	Context context;
	List<NewsClumns> newsList = new ArrayList<NewsClumns>();

	public NewsClumnAdapter(Context context, List<NewsClumns> newsList) {
		this.context = context;
		this.newsList.clear();
		this.newsList.addAll(newsList);
		initImageLoader();
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

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.vitem_clumns, null);
			vh = new ViewHolder();
			vh.tv_cluName = (TextView) convertView
					.findViewById(R.id.tv_clu_name);
			vh.tv_evName = (TextView) convertView
					.findViewById(R.id.tv_event_name);
			vh.iv_more = (ImageView) convertView.findViewById(R.id.iv_more);
			vh.iv_even_img = (ImageView) convertView
					.findViewById(R.id.iv_event_img);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		vh.tv_cluName.setText(newsList.get(position).getTitle());
		vh.tv_evName.setText(newsList.get(position).getPointnewtitle());
		imageLoader.displayImage(newsList.get(position).getImageresource(),
				vh.iv_even_img, options);
		return convertView;
	}

	class ViewHolder {
		TextView tv_cluName, tv_evName;
		ImageView iv_more, iv_even_img;
	}
}
