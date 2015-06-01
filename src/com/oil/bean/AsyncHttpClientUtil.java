package com.oil.bean;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

public class AsyncHttpClientUtil {
	private static  AsyncHttpClient client;


	public static AsyncHttpClient getInstance(Context context) {
		if (null == client) {
			client = new AsyncHttpClient();
			client.setTimeout(20 * 1000);
			PersistentCookieStore myCookieStore = new PersistentCookieStore(
					context);
			client.setCookieStore(myCookieStore);

		}
		return client;

	}

}
