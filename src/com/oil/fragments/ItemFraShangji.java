package com.oil.fragments;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.oilclient.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oil.activity.ShangjiDetailsActivity;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;

/**
 * 商机单界面
 * 
 * @author Administrator
 *
 */
public class ItemFraShangji extends Fragment {
	int dataType = 0;// 1:供应 0：求购
	RadioGroup rg_type;
	int pageCount = 1;
	HashMap<String, String> mapString;
	PullToRefreshListView prlv;
	// HorizontalListView hlv;

	CommonAdapter<Map<String, Object>> commonAdapter;
	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();// 填充数据

	// JSONArray jArray = new JSONArray();
	public ItemFraShangji() {
	};

	public ItemFraShangji(HashMap<String, String> mapString) {
		this.mapString = mapString;
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.view_list, null);
		prlv = (PullToRefreshListView) view.findViewById(R.id.prlv_data);
		// hlv = (HorizontalListView) view.findViewById(R.id.hlv_type);
		rg_type = (RadioGroup) view.findViewById(R.id.rg_busType);
		rg_type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_busGy:
					dataType = 1;
					pageCount = 1;
					updateDate();
					break;
				case R.id.rb_busQg:
					dataType = 0;
					pageCount = 1;
					updateDate();
					break;

				default:
					break;
				}
			}
		});

		initLv();
		rg_type.check(R.id.rb_busGy);
		return view;
	}

	private void initLv() {
		// TODO Auto-generated method stub
		prlv.setMode(Mode.BOTH);
		prlv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				if (refreshView.getRefreshableView()
						.getLastVisiblePosition() == refreshView.getRefreshableView().getAdapter().getCount() - 1) {
					// 加载更多
					pageCount++;
					updateDate();
				} else {
					// 刷新
					pageCount = 1;
					updateDate();
				}

			}
		});
		commonAdapter = new CommonAdapter<Map<String, Object>>(getActivity(), mapList, R.layout.item_shangji_data) {

			@Override
			public void convert(CommonViewHolder helper, Map<String, Object> item, int Position) {
				try {

					// TODO Auto-generated method stub
					// helper.setText(R.id.tv_item_content, item.get("cpname")
					// .toString());
					int td = Float.valueOf(item.get("tb").toString()).intValue();
					// helper.setText(R.id.tv_itemName, td == 10 ? "求购" : "供应");

					TextView tv_buType, tv_comName, tv_busInfo, tv_price, tv_updateTime;
					tv_buType = helper.getView(R.id.tv_itemType);
					tv_comName = helper.getView(R.id.tv_item_comName);
					tv_busInfo = helper.getView(R.id.tv_item_proInfo);
					tv_price = helper.getView(R.id.tv_item_price);
					tv_updateTime = helper.getView(R.id.tv_item_upTime);
					if (td == 1) {
						tv_buType.setBackgroundResource(R.drawable.selector_item_selcted_bgreen);
						tv_buType.setText("供");
					} else {

						tv_buType.setBackgroundResource(R.drawable.selector_selected_textview);
						tv_buType.setText("求");
					}
					String conName = item.get("corpname").toString();
					if (conName != null) {
						tv_comName.setText(conName);

					} else {
						tv_comName.setText("信息未录入");
					}
					StringBuilder sb = new StringBuilder();
					Object pro_name = item.get("cpname");
					Object stock = item.get("stock");
					Object price = item.get("price");
					Object unit = item.get("unit");
					if (pro_name != null && unit != null && stock != null) {
						sb.append(pro_name.toString() + stock.toString() + unit.toString());
						tv_busInfo.setText(sb.toString());

					} else if (pro_name != null) {
						tv_busInfo.setText(pro_name.toString());
					} else {
						tv_busInfo.setText("无数据");
					}

					if (price != null) {
						tv_price.setText(price.toString() + "元");
					} else {
						tv_price.setText("面议");
					}
					String upTime = item.get("refreshtime").toString().split(" ")[0];
					if (upTime != null) {
						tv_updateTime.setText(upTime);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		};
		prlv.setAdapter(commonAdapter);

		prlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String sdid = Float.valueOf(mapList.get(position - 1).get("sdid").toString()).intValue() + "";

				Intent intent = new Intent(getActivity(), ShangjiDetailsActivity.class).putExtra("sdid", sdid);

				getActivity().startActivity(intent);
			}
		});
		// updateDate();
	}

	private void updateDate() {
		// TODO Auto-generated method stub
		Log.e("tag", "updateDate");
		MyRequestParams params = new MyRequestParams(getActivity());
		params.put("tb", dataType + "");
		params.put("page", pageCount + "");
		params.put("pagesize", "20");
		// params.put("keyword", mapString.get("pro_cn_name"));
		params.put("keytype", 1 + "");
		params.put("ggxh", "");
		params.put("keyword", URLEncoder.encode(URLEncoder.encode(mapString.get("pro_cn_name"))));
		HttpTool.netRequest(getActivity(), params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				try {
					JSONObject jo = new JSONObject(jsString);
					String data = jo.getJSONObject("data").getString("message");
					// jaArray.
					List<Map<String, Object>> temMapList;
					ObjectConvertUtils<List<Map<String, Object>>> ocUtils = new ObjectConvertUtils<List<Map<String, Object>>>();
					if (pageCount == 1) {
						mapList.clear();

					}
					temMapList = ocUtils.convert(data);
					if (temMapList != null) {
						if (temMapList.size() > 0 && mapList.size() > 0 && temMapList.get(0).get("sdid").toString()
								.equals(mapList.get(0).get("sdid").toString())) {

						} else {
							mapList.addAll(temMapList);
						}

					}

					commonAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				prlv.onRefreshComplete();

			}
		}, Constants.URL_GRTSHANGJI, false);

	}
}
