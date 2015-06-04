package com.oil.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.oilclient.R;
import com.oil.activity.MainActivity;
import com.oil.activity.MultiAccount;
import com.oil.bean.Constants;
import com.oil.bean.OilUser;
import com.oil.event.LoginSuccessEvent;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommonUtil {

	/**
	 * 得到应用信息
	 * 
	 * @param context
	 * @return
	 */
	public static HashMap<String, String> getAppInfo(Context context) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(
					pkName, 0).versionName;
			int versionCode = context.getPackageManager().getPackageInfo(
					pkName, 0).versionCode;
			map.put("name", versionName);
			map.put("code", versionCode + "");
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog LoadingDialog;

	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(true);// 不可以用“返回键”取�?
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		LoadingDialog = loadingDialog;
		return loadingDialog;
	}

	public static void cancleDialog() {
		try {
			LoadingDialog.cancel();
		} catch (Exception e) {
		}
	}

	/**
	 * 判断网络连接状态
	 */

	public static boolean isNetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 登录返回
	 * 
	 * @param jsString
	 * @param context
	 * 
	 */
	public static void loginSuccess(String jsString, Context context,
			String destination, String tag) {
		JSONObject js;
		try {
			js = new JSONObject(jsString).getJSONObject("data");

			String accessToken = js.getString("accessToken");
			String timeStamp = js.getString("timestamp");
			ArrayList<OilUser> accountList = getAccountList(js
					.getJSONArray("users"));

			if (accountList.size() == 1) {
				Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
				saveUserInfo(context, accessToken, timeStamp,
						accountList.get(0), destination);
			} else {
				// 选择用户

				Intent intent = new Intent(context, MultiAccount.class);
				intent.putExtra("accountList", (Serializable) accountList);
				intent.putExtra("destination", destination);
				intent.putExtra("tag", tag);
				context.startActivity(intent);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<OilUser> getAccountList(JSONArray array) {
		ArrayList<OilUser> accountList = new ArrayList<OilUser>();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				OilUser account = new OilUser();
				account.setUserName(obj.getString("userName"));
				account.setCuuid(obj.getString("cuuid"));
				account.setName(obj.getString("name"));
				account.setCorpName(obj.getString("corpName"));
				account.setPhone(obj.getString("phone"));

				accountList.add(account);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountList;
	}

	/**
	 * 保存用户信息并登录
	 */
	public static void saveUserInfo(Context context, String accessToken,
			String timeStamp, OilUser user, String destination) {

		SharedPreferences.Editor editor = context.getSharedPreferences(
				Constants.USER_INFO_SHARED, Activity.MODE_PRIVATE).edit();
		editor.putBoolean(Constants.LOGIN_STATE, true);
		editor.putString(Constants.ACCESS_TOKEN, accessToken);
		editor.putString(Constants.NAME, user.getName());
		editor.putString(Constants.CORP_NAME, user.getCorpName());
		editor.putString(Constants.USER_PHONE, user.getPhone());
		editor.putString(Constants.USER_NAME, user.getUserName());
		editor.putString(Constants.CUUID, user.getCuuid()); // 用户关键字段
		long timeGap = Long.parseLong(timeStamp) - System.currentTimeMillis();
		editor.putLong(Constants.TIME_GAP, timeGap);
		editor.commit();

		Intent intent;
		intent = new Intent(context, MainActivity.class);
		intent.putExtra("destination", destination);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
		EventBus.getDefault().post(new LoginSuccessEvent());
	}
}
