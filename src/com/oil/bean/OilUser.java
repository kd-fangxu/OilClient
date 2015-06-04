package com.oil.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.oil.activity.UserLoginActivity;
import com.oil.event.FinishEvent;
import com.oil.inter.OnReturnListener;
import com.oil.utils.CommonUtil;
import com.oil.utils.HttpTool;
import com.oil.utils.SharedPreferenceUtils;
import com.oilchem.weixin.mp.aes.AesException;
import com.oilchem.weixin.mp.aes.SHA1;

import de.greenrobot.event.EventBus;

public class OilUser implements Serializable {
	public static String Error_AccountInfoMiss = "101";
	public static String Shared_Key_currentUser = "currentUser";
	String userName, phone, cuuid, name, corpName, corpprovince;
	String accountPwd;

	public OilUser() {
	};

	public static void Login(Context context, String userName,
			String accountPwd, final onLoginListener loginListener) {
		if (null != userName && null != accountPwd) {

			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = telephonyManager.getDeviceId();

			MyRequestParams params = new MyRequestParams(context);
			params.put("phone", userName);
			params.put("password", accountPwd);
			params.put("imei", imei);
			params.put("ver", CommonUtil.getAppInfo(context).get("name"));
			// 实锟街碉拷陆锟斤拷锟斤拷锟界交锟斤拷
			HttpTool.netRequest(context, params, new OnReturnListener() {

				@Override
				public void onReturn(String jsString) {
					// TODO Auto-generated method stub
					try {
						JSONObject obj = new JSONObject(jsString)
								.getJSONObject("data");
						if (obj.getString("login").equals("1")) {
							// 锟斤拷录锟缴癸拷

							loginListener.onSuccess("100", jsString);

						} else {
							String errorMessage = obj.getString("message");
							loginListener.onError(Error_AccountInfoMiss,
									errorMessage);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}, Constants.LOGIN, true);

		} else {
			loginListener.onError(Error_AccountInfoMiss, "");
		}
	}

	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String account_Pwd) {
		this.accountPwd = account_Pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCuuid() {
		return cuuid;
	}

	public void setCuuid(String cuuid) {
		this.cuuid = cuuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpprovince() {
		return corpprovince;
	}

	public void setCorpprovince(String corpprovince) {
		this.corpprovince = corpprovince;
	}

	public static void signIn(String userName, String accountPwd,
			onRegistListener registListener) {
		if (null != userName && null != accountPwd) {

			// 实锟斤拷注锟斤拷锟斤拷锟斤拷缃伙拷锟�

		} else {
			registListener
					.onError(Error_AccountInfoMiss, "account info missed");
		}
	}

	public static void logOut(Context context) {
		MyRequestParams params = new MyRequestParams(context);
		SharedPreferences sp = context.getSharedPreferences(
				Constants.USER_INFO_SHARED, Activity.MODE_PRIVATE);
		params.put("cuuid", sp.getString(Constants.CUUID, ""));

		String accessToken = sp.getString(Constants.ACCESS_TOKEN, "");
		String userName = sp.getString(Constants.USER_NAME, "");
		params.put("accesstoken", accessToken);
		String timestamp = String.valueOf(System.currentTimeMillis()
				+ sp.getLong(Constants.TIME_GAP, 0));
		params.put("timestamp", timestamp);
		params.put("username", userName);

		try {
			params.put(
					"signature",
					SHA1.getSHA1(new String[] { userName, timestamp,
							accessToken }));
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpTool.netRequest(context, params, null, Constants.LOGOUT, true);

		SharedPreferences.Editor editor = sp.edit();
		String userPhone = sp.getString(Constants.USER_PHONE, "");
		editor.clear();
		editor.commit();
		editor.putString(Constants.USER_PHONE, userPhone);
		editor.commit();

		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, UserLoginActivity.class);
		context.startActivity(intent);
		Toast.makeText(context, "注锟斤拷晒锟�", 1).show();
		EventBus.getDefault().post(new FinishEvent());

	}

	/**
	 * 鑾峰彇 褰撳墠鐢ㄦ埛
	 * 
	 * @param context
	 * @return
	 */
	public static OilUser getCurrentUser(Context context) {
		// 锟斤拷取锟斤拷前锟矫伙拷
		// String json_user = (String) SharedPreferenceUtils.getParam(context,
		// Shared_Key_currentUser, "null");
		// Log.e("getJson", json_user);
		// // JSONObject jo = JSONObject.fromObject(json_user);
		//
		// // Log.e("current", msg)
		// return new Gson().fromJson(json_user, OilUser.class);
		OilUser oilUser = new OilUser();
		String name = (String) SharedPreferenceUtils.getParam(context,
				Constants.USER_INFO_SHARED, Constants.NAME, "");
		String corpName = (String) SharedPreferenceUtils.getParam(context,
				Constants.USER_INFO_SHARED, Constants.CORP_NAME, "");
		String userPhone = (String) SharedPreferenceUtils.getParam(context,
				Constants.USER_INFO_SHARED, Constants.USER_PHONE, "");
		String userName = (String) SharedPreferenceUtils.getParam(context,
				Constants.USER_INFO_SHARED, Constants.USER_NAME, "");
		String cuuid = (String) SharedPreferenceUtils.getParam(context,
				Constants.USER_INFO_SHARED, Constants.CUUID, "");
		oilUser.setName(name);
		oilUser.setCorpName(corpName);
		oilUser.setCuuid(cuuid);
		oilUser.setPhone(userPhone);
		oilUser.setUserName(userName);
		return oilUser;
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
