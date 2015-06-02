package com.oil.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.example.oilclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.oil.activity.NetworkDialog;
import com.oil.activity.RequestFailDialog;
import com.oil.bean.AsyncHttpClientUtil;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.bean.OilUser.onLoginListener;
import com.oil.event.FinishDialog;
import com.oil.inter.OnReturnListener;

import de.greenrobot.event.EventBus;

public class HttpTool {
	public static void netRequest(final Context context,
			com.loopj.android.http.RequestParams params,
			final OnReturnListener listener, final String url,
			final boolean showDia) {

		AsyncHttpClient client = AsyncHttpClientUtil.getInstance(context);
		AsyncHttpResponseHandler hd = new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				if (showDia) {
					CommonUtil.createLoadingDialog(
							context,
							context.getResources().getString(
									R.string.loadingtext)).show();
				}

				super.onStart();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d("value", "success==" + content);
				CommonUtil.cancleDialog();

				if (null != content) {
					if (DataUtil.CheckJson(false, content, context)) {
						if (null != listener) {
							listener.onReturn(content);
						}
						try {
							JSONObject obj = new JSONObject(content);
							if (obj.has("login")
									&& obj.getString("login").equals("0")) {
								Toast.makeText(context, "当前登录已失效，请重新登录",
										Toast.LENGTH_SHORT).show();
								OilUser.logOut(context);

							} else if (obj.has("data")
									&& obj.getJSONObject("data").has(
											"accessToken")) {
                                //更新accessToken
								Editor editor = context.getSharedPreferences(
										Constants.USER_INFO_SHARED,
										Activity.MODE_PRIVATE).edit();
								editor.putString(
										Constants.ACCESS_TOKEN,
										new JSONObject(content).getJSONObject(
												"data")
												.getString("accessToken"))
										.commit();

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						if (!Constants.isRequestFailDialogExist) {
							Intent intent = new Intent(context,
									RequestFailDialog.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						}
					}
				}

				super.onSuccess(statusCode, content);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d("value", "fail==" + content);
				CommonUtil.cancleDialog();
				if (!Constants.isRequestFailDialogExist) {
					Intent intent = new Intent(context, RequestFailDialog.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
				super.onFailure(error, content);
			}
		};

		if (CommonUtil.isNetAvailable(context)) {
			client.get(url, params, hd);

			if (Constants.isNetWorkDialogExist) {
				EventBus.getDefault().post(new FinishDialog());
			}
		} else if (!Constants.isNetWorkDialogExist) {
			Intent intent = new Intent(context, NetworkDialog.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}

	}
}
