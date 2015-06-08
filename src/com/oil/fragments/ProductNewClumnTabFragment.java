package com.oil.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.example.oilclient.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.activity.WebViewShowActivity;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.HotPoint;
import com.oil.datamodel.NewsListPage;
import com.oil.utils.GsonParserFactory;
import com.oil.utils.StringUtils;

public class ProductNewClumnTabFragment extends Fragment {
	public static ProductNewClumnTabFragment getInstance() {

		return new ProductNewClumnTabFragment();
	};

	public ProductNewClumnTabFragment() {

	};

	LinearLayout ll_bar;
	List<Object> beanList = new ArrayList<Object>();
	PullToRefreshListView prListView;
	CommonAdapter<Object> commonAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.activity_commonlist,
				null);
		ll_bar = (LinearLayout) view.findViewById(R.id.ll_bar);
		ll_bar.setVisibility(View.GONE);
		prListView = (PullToRefreshListView) view.findViewById(R.id.prlv_news);
		prListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						WebViewShowActivity.class);
				Gson gson = new Gson();
				HotPoint hotPoint = gson.fromJson(
						gson.toJson(commonAdapter.getItem(position)),
						new TypeToken<HotPoint>() {
						}.getType());
				;
				intent.putExtra(WebViewShowActivity.PAGE_URL,
						hotPoint.getLink());
				startActivity(intent);
			}
		});
		initDemodata();
		return view;
	}

	private void initDemodata() {
		// TODO Auto-generated method stub
		NewsListPage newsListPage;
		try {
			newsListPage = new GsonParserFactory().getNewsListPage(StringUtils
					.convertStreamToString(getActivity().getAssets().open(
							"newslist.txt")));
			beanList = newsListPage.getPointList();
			commonAdapter = new CommonAdapter<Object>(getActivity(), beanList,
					R.layout.item_normalnewsitem) {
				Gson gson = new Gson();

				@Override
				public void convert(CommonViewHolder helper, Object item) {
					// TODO Auto-generated method stub
					HotPoint hotPoint = gson.fromJson(gson.toJson(item),
							new TypeToken<HotPoint>() {
							}.getType());
					Log.e("HotPointInfo", hotPoint.toString());
					helper.setImageByUrl(R.id.iv_item_pic,
							hotPoint.getImagelink());
					helper.setText(R.id.tv_item_title, hotPoint.getTitle());
				}
			};
			prListView.setAdapter(commonAdapter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
