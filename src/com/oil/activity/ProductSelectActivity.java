package com.oil.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oilclient.R;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.datamodel.OilProductStrucModel;
import com.oil.event.UserFouceChangeEvent;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;

import de.greenrobot.event.EventBus;

public class ProductSelectActivity extends Activity implements OnClickListener {
	ListView lv_chain, lv_product;
	Button btn_fouce;
	ImageView iv_back;
	String wang_id;
	CommonAdapter<Map<String, Object>> chainAdapter, proAdapter;
	List<Map<String, Object>> chainList = new ArrayList<Map<String, Object>>(),
			proList = new ArrayList<Map<String, Object>>();
	OilProductStrucModel opsm = new OilProductStrucModel();
	int chainSelectionPositon = 0;
	String currentChainId = "";
	Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_selected);
		initWeidget();
		InitCommonAdapter();
		Intent intent = getIntent();
		if (intent != null) {
			wang_id = intent.getStringExtra("wangId");
			initChainData();
		}

	}

	private void initChainData() {
		// TODO Auto-generated method stub
		chainList.addAll(opsm.getChainListByWangId(wang_id));
		chainAdapter.notifyDataSetChanged();
		if (chainList.size() > 0) {
			updateProdate(chainList.get(chainSelectionPositon).get("chan_id")
					.toString());
		}

	}

	private void updateProdate(String chainId) {
		proList.clear();
		proList.addAll(opsm.getProductListByChainId(chainId));
		proAdapter.notifyDataSetChanged();
	}

	private void InitCommonAdapter() {
		chainAdapter = new CommonAdapter<Map<String, Object>>(
				ProductSelectActivity.this, chainList,
				R.layout.item_simple_text) {

			@Override
			public void convert(CommonViewHolder helper,
					final Map<String, Object> item, final int Position) {
				// TODO Auto-generated method stub
				TextView tv_content = helper.getView(R.id.tv_item_simple);
				// tv_content.setTextSize(ScreenUtils.dip2px(mContext, 12));
				if (Position == chainSelectionPositon) {
					tv_content.setSelected(true);
					tv_content.setTextColor(getResources().getColor(
							R.color.white));
				} else {
					tv_content.setSelected(false);
					tv_content.setTextColor(getResources().getColor(
							R.color.gray));
				}
				tv_content.setText(item.get("chan_name").toString());
				tv_content.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						chainSelectionPositon = Position;
						updateProdate(item.get("chan_id").toString());
						chainAdapter.notifyDataSetChanged();
					}
				});
			}
		};

		proAdapter = new CommonAdapter<Map<String, Object>>(
				ProductSelectActivity.this, proList,
				R.layout.item_simple_checktext) {

			@Override
			public void convert(CommonViewHolder helper,
					final Map<String, Object> item, int Position) {
				// TODO Auto-generated method stub
				helper.setText(R.id.tv_item_simple, item.get("pro_cn_name")
						.toString());
				// final ImageView iv_select =
				// helper.getView(R.id.iv_item_select);
				// iv_select.setImageResource(R.drawable.icon_add);
				// iv_select.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// if (iv_select.isSelected()) {
				// iv_select.setSelected(false);
				// item.put("isSelected", false);
				// } else {
				// iv_select.setSelected(true);
				// item.put("isSelected", true);
				// }
				// }
				// });
			}
		};

		lv_chain.setAdapter(chainAdapter);
		lv_product.setAdapter(proAdapter);
	}

	private void showToast(String content) {
		if (toast != null) {
			toast.setText(content);
			toast.show();
		}

	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		toast = Toast.makeText(ProductSelectActivity.this, "",
				Toast.LENGTH_SHORT);
		lv_chain = (ListView) findViewById(R.id.lv_chainlist);
		lv_product = (ListView) findViewById(R.id.lv_prolist);
		btn_fouce = (Button) findViewById(R.id.btn_fouce);
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		btn_fouce.setOnClickListener(this);
		iv_back.setOnClickListener(this);

		lv_product.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int proId = Double.valueOf(
						proList.get(position).get("pro_id").toString())
						.intValue();
				String userId = OilUser.getCurrentUser(
						ProductSelectActivity.this).getCuuid();
				final ImageView iv_add = (ImageView) view
						.findViewById(R.id.iv_item_select);
				if (iv_add.isSelected()) {// ȡ���ע
					// ��ӹ�ע
					String url = Constants.URL_USERFOUCECHANGE + "/" + userId
							+ "/" + proId + "/" + 0;
					HttpTool.netRequestNoCheck(ProductSelectActivity.this,
							null, new OnReturnListener() {

								@Override
								public void onReturn(String jsString) {
									// TODO Auto-generated method stub
									try {
										if (new JSONObject(jsString).get(
												"status").equals("1")) {
											showToast("ȡ��ɹ�");
											UserFouceChangeEvent event = new UserFouceChangeEvent();
											event.setAdded(true);
											EventBus.getDefault().post(event);
											iv_add.setSelected(false);
										} else {
											showToast("ȡ��ʧ��");
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}, url, false);

				} else {// ��ӹ�ע
					String url = Constants.URL_USERFOUCECHANGE + "/" + userId
							+ "/" + proId + "/" + 1;
					HttpTool.netRequestNoCheck(ProductSelectActivity.this,
							null, new OnReturnListener() {

								@Override
								public void onReturn(String jsString) {
									// TODO Auto-generated method stub
									try {
										if (new JSONObject(jsString).get(
												"status").equals("1")) {
											showToast("��ע�ɹ�");
											UserFouceChangeEvent event = new UserFouceChangeEvent();
											event.setAdded(true);
											EventBus.getDefault().post(event);
											iv_add.setSelected(true);
										} else {
											showToast("�ѹ�ע");
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}, url, false);
				}

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_fouce:
			List<String> proFocusList = new ArrayList<String>();
			for (int i = 0; i < proList.size(); i++) {
				boolean isSelected;
				if (proList.get(i).get("isSelected") != null) {
					isSelected = (Boolean) proList.get(i).get("isSelected");
					if (isSelected) {
						proFocusList.add(proList.get(i).get("pro_id")
								.toString());
						UserFouceChangeEvent userFouceChangeEvent = new UserFouceChangeEvent();
						userFouceChangeEvent.setAdded(true);
						userFouceChangeEvent.setAddedProIds(proFocusList);
						EventBus.getDefault().post(userFouceChangeEvent);
					}
				}
			}
			break;
		case R.id.iv_pageback:
			finish();
			break;
		default:
			break;
		}
	}
}
