package com.oil.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.oilclient.R;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;

public class SearchPageActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchpage);
		initWeidget();
	}

	ImageView iv_back;
	EditText et_edit;
	ListView lv_show;
	// RequestQueue rQueue;
	CommonAdapter<String> itemAdapter;
	List<String> itemList = new ArrayList<String>();

	private void initWeidget() {
		// TODO Auto-generated method stub
		itemAdapter = new CommonAdapter<String>(this, itemList,
				R.layout.item_searchview) {

			@Override
			public void convert(CommonViewHolder helper, String item,
					int positon) {
				// TODO Auto-generated method stub
				helper.setText(R.id.tv_item_content, item.toString());
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
				startActivity(new Intent(SearchPageActivity.this,
						ProductDetailsActivity.class));
			}
		});
		et_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				getListContent(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

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
