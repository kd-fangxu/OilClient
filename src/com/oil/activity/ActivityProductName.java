package com.oil.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.oilclient.R;
import com.oil.bean.MyConfig;
import com.oil.datamodel.HttpReqRecep;
import com.oil.inter.OnReturnListener;
import com.oil.weidget.XListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActivityProductName extends Activity {
	XListView mListView;
	Myadapter mAdapter;
	String productid = "0";// haha
	String productname = "";
	ArrayList<String[]> productList = new ArrayList<String[]>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productname);

		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mListView = (XListView) findViewById(R.id.xListView);
		mAdapter = new Myadapter();
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false);
		mListView.setPullRefreshEnable(false);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int position = arg2 - 1;
				productname = productList.get(position)[1];
				productid = productList.get(position)[0];
				finish();
			}
		});
		final EditText name = (EditText) findViewById(R.id.qiyemingcheng);
		name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				getProductList(name.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// Timer timer = new Timer();
		//
		// timer.schedule(new TimerTask()
		//
		// {
		//
		// public void run()
		//
		// {
		//
		// InputMethodManager inputManager =
		//
		// (InputMethodManager) name.getContext().getSystemService(
		// Context.INPUT_METHOD_SERVICE);
		//
		// inputManager.showSoftInput(name, 0);
		//
		// }
		//
		// },
		//
		// 300);
		//
		// EventBus.getDefault().register(this);

	}

	// // ¹Ø±Õ
	// public void onEvent(FinishEvent event) {
	// finish();
	// }

	@Override
	public void finish() {
		if (productname.length() > 0) {
			Intent intent = new Intent();
			intent.putExtra("name", productname);
			intent.putExtra("id", productid);
			setResult(MyConfig.GET_PRODUCTNAME_RESULTCODE, intent);
		} else {
			Intent intent = new Intent();
			intent.putExtra("name", "");
			intent.putExtra("id", "0");
			setResult(MyConfig.GET_PRODUCTNAME_RESULTCODE, intent);
		}
		super.finish();
	}

	private class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return productList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return productList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			final int temp = position;
			LayoutInflater inflater = getLayoutInflater();

			convertView = inflater.inflate(R.layout.chanpinname_iterms, null);
			TextView tv = (TextView) convertView.findViewById(R.id.mark);
			tv.setText(productList.get(position)[1]);
			return convertView;
		}
	}

	public void getProductList(String str) {

		HttpReqRecep.getProductList(ActivityProductName.this, str, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				productList.clear();
				productList = parserProductList(jsString);
				mAdapter.notifyDataSetChanged();

			}

		});
	}

	private ArrayList<String[]> parserProductList(String jsString) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			JSONArray array = new JSONObject(jsString).getJSONObject("data").getJSONArray("message");
			for (int i = 0; i < array.length(); i++) {
				String[] product = new String[2];
				product[0] = array.getJSONObject(i).getString("productid");
				product[1] = array.getJSONObject(i).getString("productname");
				list.add(product);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}