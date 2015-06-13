package com.oil.activity;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.oilclient.R;
import com.oil.adapter.CommonAdapter;
import com.oil.adapter.CommonViewHolder;
import com.oil.datamodel.OilProductStrucModel;

public class ProductSelectActivity extends Activity implements OnClickListener {
	ListView lv_chain, lv_product;
	Button btn_fouce;
	ImageView iv_back;
	String wang_id;
	CommonAdapter<Map<String, Object>> chainAdapter, proAdapter;
	List<Map<String, Object>> chainList, proList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_selected);
		initWeidget();
		Intent intent = getIntent();
		if (intent != null) {
			wang_id = intent.getStringExtra("wangId");
			initChainData();
		}
	}

	private void initChainData() {
		// TODO Auto-generated method stub
		chainList = new OilProductStrucModel().getChainListByWangId(Integer
				.valueOf(wang_id));
		chainAdapter = new CommonAdapter<Map<String, Object>>(
				ProductSelectActivity.this, chainList,
				R.layout.item_simple_text) {

			@Override
			public void convert(CommonViewHolder helper,
					Map<String, Object> item, int Position) {
				// TODO Auto-generated method stub
				if (Position == chainSelectionPositon) {

				}
			}
		};
	}

	int chainSelectionPositon = 0;

	private void initWeidget() {
		// TODO Auto-generated method stub
		lv_chain = (ListView) findViewById(R.id.lv_chainlist);
		lv_product = (ListView) findViewById(R.id.lv_prolist);
		btn_fouce = (Button) findViewById(R.id.btn_fouce);
		iv_back = (ImageView) findViewById(R.id.iv_mainback);
		btn_fouce.setOnClickListener(this);
		iv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_fouce:

			break;
		case R.id.iv_mainback:

			break;
		default:
			break;
		}
	}
}
