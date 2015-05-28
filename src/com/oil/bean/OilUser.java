package com.oil.bean;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.oil.utils.SharedPreferenceUtils;

public class OilUser {
	public static String Error_AccountInfoMiss = "101";
	public static String Shared_Key_currentUser = "currentUser";
	onLoginListener onloginListener;
	onRegistListener registListener;
	String accout_Name;
	String account_Pwd;

	public OilUser(String name, String pwd) {

		this.account_Pwd = pwd;
		this.accout_Name = name;
	};

	public void Login(Context context, onLoginListener loginListener) {
		this.onloginListener = loginListener;
		if (null != accout_Name && null != account_Pwd) {

			// 实现登陆的网络交互
			OilUser currentUser = new OilUser(accout_Name, account_Pwd);

			Gson gson = new Gson();
			String info = gson.toJson(currentUser).toString();
			Log.e("savejson", info);
			SharedPreferenceUtils.setParam(context, Shared_Key_currentUser,
					info);
			onloginListener.onSuccess("100", "登陆成功");

		} else {
			onloginListener.onError(Error_AccountInfoMiss,
					"account info missed");
		}
	}

	public String getAccout_Name() {
		return accout_Name;
	}

	public void setAccout_Name(String accout_Name) {
		this.accout_Name = accout_Name;
	}

	public String getAccount_Pwd() {
		return account_Pwd;
	}

	public void setAccount_Pwd(String account_Pwd) {
		this.account_Pwd = account_Pwd;
	}

	public void signIn(onRegistListener registListener) {
		this.registListener = registListener;
		if (null != accout_Name && null != account_Pwd) {

			// 实现注册的网络交互

		} else {
			registListener
					.onError(Error_AccountInfoMiss, "account info missed");
		}
	}

	public void logOut(Context context) {
		SharedPreferenceUtils.setParam(context, Shared_Key_currentUser, null);
	}

	public static OilUser getCurrentUser(Context context) {
		// 获取当前用户
		String json_user = (String) SharedPreferenceUtils.getParam(context,
				Shared_Key_currentUser, "null");
		Log.e("getJson", json_user);
		// JSONObject jo = JSONObject.fromObject(json_user);

		// Log.e("current", msg)
		return new Gson().fromJson(json_user, OilUser.class);
	}

	public interface onLoginListener {
		void onSuccess(String resCode, String response);

		void onError(String resCode, String errorReason);
	}

	public interface onRegistListener {
		void onSuccess(String resCode, String response);

		void onError(String resCode, String errorReason);
	}
}
