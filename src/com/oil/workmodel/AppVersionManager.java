package com.oil.workmodel;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.oil.bean.Constants;
import com.oil.dialogs.CommontitleDialog;
import com.oil.dialogs.CommontitleDialog.onComDialogBtnClick;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;

/**
 * 版本管理类
 * 
 * @author Administrator
 *
 */
public class AppVersionManager {
	public static int OilAppTag = 2;// 项目代号

	/**
	 * 获取程序名
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取版本信息
	 * 
	 * @param context
	 * @return 褰撳墠搴旂敤鐨勭増鏈悕绉?
	 */
	public static String getVersionName(Context context) {
		try {

			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void doCheckUpdate(final Context context) {
		doCheckUpdate(context, false);
	}

	public static void doCheckUpdate(final Context context,
			final boolean isShowInfo) {
		String url = Constants.URL_APPUODATE;
		RequestParams params = new RequestParams();
		params.put("version", getVersionName(context));
		params.put("appname", OilAppTag + "");
		HttpTool.netRequestNoCheck(context, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				JSONObject jObject;
				try {
					jObject = new JSONObject(jsString);
					String update = jObject.getJSONObject("data").getString(
							"update");
					final String downLoadUrl = jObject.getJSONObject("data")
							.getString("downloadUrl");
					if (update.equals("1")) {
						// gengxin
						final CommontitleDialog titleDialog = new CommontitleDialog(
								context);
						titleDialog.Init("提示", "检测到新版本，是否更新",
								new onComDialogBtnClick() {

									@Override
									public void onConfirmClick() {
										// TODO Auto-generated method stub
										openDownloadWeb(context, downLoadUrl);
										titleDialog.disMiss();
									}

									@Override
									public void onCancelClick() {
										// TODO Auto-generated method stub
										titleDialog.disMiss();
									}
								});
					} else {// bu gengxin
						if (isShowInfo) {
							Toast.makeText(context, "程序无需更新", 0).show();
						}
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, url, false);

	}

	/**
	 * 打开服务组件下载页面。
	 * 
	 * @param context
	 * @param url
	 */
	public static void openDownloadWeb(Context context, String url) {
		Uri uri = Uri.parse(url);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(it);
	}
}
