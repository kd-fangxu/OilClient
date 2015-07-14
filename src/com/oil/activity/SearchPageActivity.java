package com.oil.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.example.oilclient.R;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.datamodel.OilProductStrucModel;
import com.oil.event.UserFouceChangeEvent;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.workmodel.UserFouceModel;

import de.greenrobot.event.EventBus;

public class SearchPageActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchpage);
		toast = Toast.makeText(SearchPageActivity.this, "", Toast.LENGTH_SHORT);
		initWeidget();
	}

	Toast toast;
	ImageView iv_back;
	EditText et_edit;
	ListView lv_show;
	// RequestQueue rQueue;
	CommonAdapter<Map<String, Object>> itemAdapter;
	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	List<String> itemList = new ArrayList<String>();

	private void initWeidget() {
		// TODO Auto-generated method stub
		itemAdapter = new CommonAdapter<Map<String, Object>>(this, dataList,
				R.layout.item_searchview) {

			@Override
			public void convert(CommonViewHolder helper,
					Map<String, Object> item, int positon) {
				// TODO Auto-generated method stuima
				ImageView iv_fouce = helper.getView(R.id.iv_mark);
				helper.setText(R.id.tv_item_content, item.get("pro_cn_name")
						.toString());
				if (UserFouceModel.getInstance().isFouced(
						item.get("pro_id").toString())) {
					iv_fouce.setSelected(true);
				} else {
					iv_fouce.setSelected(false);
				}
			}
		};
		// rQueue = Volley.newRequestQueue(SearchPageActivity.this);
		et_edit = (EditText) findViewById(R.id.et_edit);
		lv_show = (ListView) findViewById(R.id.lv_show);
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(this);
		lv_show.setAdapter(itemAdapter);
		lv_show.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(SearchPageActivity.this,
				// ProductDetailsActivity.class);
				// intent.putExtra("chan_id", dataList.get(position)
				// .get("chan_id").toString());
				// intent.putExtra("pro_cn_name",
				// dataList.get(position).get("pro_cn_name").toString());
				// intent.putExtra("pro_id",
				// dataList.get(position).get("pro_id")
				// .toString());
				//
				// startActivity(intent);

				// TODO Auto-generated method stub
				int proId = Double.valueOf(
						dataList.get(position).get("pro_id").toString())
						.intValue();
				String userId = OilUser.getCurrentUser(SearchPageActivity.this)
						.getCuuid();
				final ImageView iv_add = (ImageView) view
						.findViewById(R.id.iv_mark);
				if (iv_add.isSelected()) {// 取锟斤拷锟阶�
					// 锟斤拷庸锟阶�
					String url = Constants.URL_USERFOUCECHANGE + "/" + userId
							+ "/" + proId + "/" + 0;
					HttpTool.netRequestNoCheck(SearchPageActivity.this, null,
							new OnReturnListener() {

								@Override
								public void onReturn(String jsString) {
									// TODO Auto-generated method stub
									try {
										if (new JSONObject(jsString).get(
												"status").equals("1")) {
											showToast(getResources().getText(
													R.string.unMarkSucceed)
													.toString());
											UserFouceChangeEvent event = new UserFouceChangeEvent();
											event.setAdded(true);
											EventBus.getDefault().post(event);
											iv_add.setSelected(false);
										} else {
											showToast(getResources().getText(
													R.string.unMarkUnSucceed)
													.toString());
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}, url, false);

				} else {// 锟斤拷庸锟阶�
					String url = Constants.URL_USERFOUCECHANGE + "/" + userId
							+ "/" + proId + "/" + 1;
					HttpTool.netRequestNoCheck(SearchPageActivity.this, null,
							new OnReturnListener() {

								@Override
								public void onReturn(String jsString) {
									// TODO Auto-generated method stub
									try {
										if (new JSONObject(jsString).get(
												"status").equals("1")) {
											showToast(getResources().getText(
													R.string.markSucceed)
													.toString());
											UserFouceChangeEvent event = new UserFouceChangeEvent();
											event.setAdded(true);
											EventBus.getDefault().post(event);
											iv_add.setSelected(true);
										} else {
											showToast(getResources().getText(
													R.string.markUnSucceed)
													.toString());
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
		// et_edit.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// // TODO Auto-generated method stub
		// getListContent(s);
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		et_edit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				dataList.clear();
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					dataList.addAll(new OilProductStrucModel()
							.getProductListsByProName(et_edit.getText()
									.toString()));
				}
				itemAdapter.notifyDataSetChanged();
				return true;
			}
		});
		et_edit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {

				}
			}
		});
	}

	private void getListContent(CharSequence s) {
		// rQueue.cancelAll(this);
		// // TODO Auto-generated method stub
		// JsonObjectRequest joRequest = new JsonObjectRequest("www",
		// new Listener<JSONObject>() {
		//
		// @Override
		// public void onResponse(JSONObject arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// }, new ErrorListener() {
		//
		// @Override
		// public void onErrorResponse(VolleyError arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// }) {
		// @Override
		// protected Map<String, String> getParams() throws AuthFailureError {
		// // TODO Auto-generated method stub
		// return super.getParams();
		// }
		// };
		// joRequest.setTag(s);
		// rQueue.add(joRequest);
		itemList.clear();
		itemList.add(s + "item1");
		itemList.add(s + "item1");
		itemList.add(s + "item1");
		itemList.add(s + "item1");
		itemList.add(s + "item1");
		itemAdapter.notifyDataSetChanged();
	}

	private void showToast(String content) {
		if (toast != null) {
			toast.setText(content);
			toast.show();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_pageback:
			finish();
			break;

		default:
			break;
		}
	}
}
