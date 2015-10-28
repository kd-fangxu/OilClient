package com.oil.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.activity.WebViewShowActivity;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.datasave.AppDataCatchModel;
import com.oil.iface.OnDataReturnListener;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.TimeUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;

/**
 * 企业介绍
 * 
 * @author Administrator
 *
 */
public class TabFragmentMainPage extends Fragment {
	public TabFragmentMainPage() {
	};

	public static TabFragmentMainPage getInstance() {

		return new TabFragmentMainPage();

	}

	RadioGroup rg_abort;
	PullToRefreshListView prLv;
	CommonAdapter<Map<String, Object>> commonAdapter;
	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();// 列表源

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.view_list, null);
		rg_abort = (RadioGroup) view.findViewById(R.id.rg_busType);
		rg_abort.setVisibility(View.GONE);
		prLv = (PullToRefreshListView) view.findViewById(R.id.prlv_data);
		initLv();
		return view;
	}

	int pageCount = 1;

	private void getData() {
		String url = Constants.URL_getCompantNews;
		MyRequestParams param = new MyRequestParams(getActivity());
		param.put("currentPage", pageCount + "");
		param.put("pagesize", "20");
		AppDataCatchModel catchModel = new AppDataCatchModel(getActivity(),
				url, param);
		catchModel.setOnDataReturnListener(
				"lzCompanyNew" + pageCount + ".json", false, false,
				new OnDataReturnListener() {

					@Override
					public void onDataReturn(String content) {
						// TODO Auto-generated method stub
						try {
							String dataString = new JSONObject(content)
									.getString("data");
							ObjectConvertUtils<List<Map<String, Object>>> convertUtils = new ObjectConvertUtils<List<Map<String, Object>>>();
							List<Map<String, Object>> temMapList = convertUtils
									.convert(dataString);
							if (temMapList != null) {
								if (pageCount == 1) {
									mapList.clear();
								}
								mapList.addAll(temMapList);
								commonAdapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		prLv.onRefreshComplete();
	}

	private void initLv() {
		// TODO Auto-generated method stub

		prLv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (refreshView.getRefreshableView().getLastVisiblePosition() == refreshView
						.getRefreshableView().getAdapter().getCount()) {
					// 加载
				} else {
					// 刷新
					pageCount = 1;
					getData();
				}
			}
		});
		commonAdapter = new CommonAdapter<Map<String, Object>>(getActivity(),
				mapList, R.layout.vitem_clumns) {

			@Override
			public void convert(CommonViewHolder helper,
					Map<String, Object> item, int Position) {
				// TODO Auto-generated method stub
				helper.setImageByUrl(R.id.iv_event_img, item.get("news_pic")
						.toString());
				helper.setText(R.id.tv_title, item.get("news_title").toString());
				helper.setText(R.id.tv_summary, item.get("news_summary")
						.toString());
				helper.setText(R.id.tv_time, TimeUtil
						.getDescriptionTimeFromTimestamp(((Double) item
								.get("Create_Time")).longValue()));

			}
		};

		prLv.setAdapter(commonAdapter);
		prLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String content = ((Map<String, Object>) prLv
						.getRefreshableView().getAdapter().getItem(position))
						.get("news_content").toString();
				String title = ((Map<String, Object>) prLv.getRefreshableView()
						.getAdapter().getItem(position)).get("news_title")
						.toString();
				Intent intent = new Intent(getActivity(),
						WebViewShowActivity.class);
				intent.putExtra(WebViewShowActivity.PAGE_TITLE, title);
				intent.putExtra(WebViewShowActivity.PAGR_CONTENT, content);
				getActivity().startActivity(intent);
			}
		});
		getData();
	}
}
