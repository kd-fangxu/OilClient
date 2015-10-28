package com.oil.activity;

import com.example.oilclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 文章显示
 * 
 * @author Administrator
 *
 */
public class WebViewShowActivity extends Activity {
	public static String PAGE_TITLE = "title";
	public static String PAGE_URL = "url";
	public static String PAGR_CONTENT = "content";
	ImageView iv_pageback;
	TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webshow);
		initWeidget();
	}

	WebView webView;

	private void initWeidget() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String pageTitle = intent.getStringExtra(PAGE_TITLE);
		String Url = intent.getStringExtra(PAGE_URL);
		String content = intent.getStringExtra(PAGR_CONTENT);
		iv_pageback = (ImageView) findViewById(R.id.iv_pageback);
		tv_title = (TextView) findViewById(R.id.tv_page_title);
		iv_pageback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if (pageTitle != null) {
			tv_title.setText(pageTitle);
		}

		webView = (WebView) findViewById(R.id.wv_show);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setBlockNetworkImage(false);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		// webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.getSettings().setLoadWithOverviewMode(true);

		// String content = StringUtils.convertStreamToString(getAssets()
		// .open("newdetails.txt"));
		webView.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
		// webView.loadData(data, "text/html", "UTF -8");//API提供的标准用法，无法解决乱码问题
		webView.loadData(content, "text/html; charset=UTF-8", null);// 这种写法可以正确解码

		// webView.loadd
	}
}
