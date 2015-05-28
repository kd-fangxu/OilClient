package com.oil.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.example.oilclient.R;
import com.oil.utils.StringUtils;

public class WebViewShowActivity extends Activity {
	public static String PAGE_TITLE = "title";
	public static String PAGE_URL = "url";

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
		webView = (WebView) findViewById(R.id.wv_show);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.getSettings().setBlockNetworkImage(false);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		// webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.getSettings().setLoadWithOverviewMode(true);

		// webView.setWebViewClient(new WebViewClient() {
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		//
		// @Override
		// public void onReceivedError(WebView view, int errorCode,
		// String description, String failingUrl) {
		// // TODO Auto-generated method stub
		// // Log.e("aaaaa", "webº”‘ÿ ß∞‹");
		// view.stopLoading();
		// view.clearView();
		// view.setVisibility(view.GONE);
		// }
		// });
		// webView.loadUrl(Url);
		try {
			String content = StringUtils.convertStreamToString(getAssets()
					.open("newdetails.txt"));
			webView.loadData(content, "text/html", "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
