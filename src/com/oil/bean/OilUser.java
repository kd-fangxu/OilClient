package com.oil.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.oilclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.oil.activity.UserLoginActivity;
import com.oil.event.FinishEvent;
import com.oil.iface.OnCommonBusListener;
import com.oil.inter.OnReturnListener;
import com.oil.utils.CommonUtil;
import com.oil.utils.HttpTool;
import com.oil.utils.SharedPreferenceUtils;
import com.oil.utils.StringUtils;
import com.oil.utils.ToastUtils;
import com.oil.workmodel.AppVersionManager;
import com.oil.workmodel.UserFouceModel;
import com.oil.workmodel.UserRegisterManager;
import com.oilchem.weixin.mp.aes.AesException;
import com.oilchem.weixin.mp.aes.SHA1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import de.greenrobot.event.EventBus;

public class OilUser implements Serializable {
	public static String Error_AccountInfoMiss = "101";
	public static String Shared_Key_currentUser = "currentUser";
	String userName, phone, cuuid, name, corpName, corpprovince;
	String accountPwd;

	public OilUser() {
	};

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

	/**
	 * 用户登录
	 * 
	 * @param context
	 * @param userName
	 * @param name
	 * @param password
	 * @param phone
	 * @param corpName
	 * @param registListener
	 */
	public static void signIn(final Context context, final String userName, String name, final String password,
			String phone, String corpName, final onRegistListener registListener) {
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance(context);
		com.loopj.android.http.RequestParams params = new com.loopj.android.http.RequestParams();

		params.put("userName", userName);
		params.put("phone", phone);
		params.put("passWord", StringUtils.MD5(password));
		params.put("name", java.net.URLEncoder.encode(java.net.URLEncoder.encode(name)));
		params.put("corpName", java.net.URLEncoder.encode(java.net.URLEncoder.encode(corpName)));
		HttpTool.netRequest(context, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub

				try {
					JSONObject js = new JSONObject(jsString).getJSONObject("data");

					if (js.getString("register").equals("1")) {
						registListener.onSuccess(1 + "", "regSuccess");
						UserRegisterManager urManager = new UserRegisterManager(context, userName, password,
								AppVersionManager.OilAppTag + "");

						urManager.doPostRegisterInfo();
					} else {
						registListener.onError(Error_AccountInfoMiss, js.getString("message"));

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, Constants.REGIST, true);

	}

	public static void logOut(Context context) {
		MyRequestParams params = new MyRequestParams(context);
		SharedPreferences sp = context.getSharedPreferences(Constants.USER_INFO_SHARED, Activity.MODE_PRIVATE);
		params.put("cuuid", sp.getString(Constants.CUUID, ""));

		String accessToken = sp.getString(Constants.ACCESS_TOKEN, "");
		String userName = sp.getString(Constants.USER_NAME, "");
		params.put("accesstoken", accessToken);
		String timestamp = String.valueOf(System.currentTimeMillis() + sp.getLong(Constants.TIME_GAP, 0));
		params.put("timestamp", timestamp);
		params.put("username", userName);

		try {
			params.put("signature", SHA1.getSHA1(new String[] { userName, timestamp, accessToken }));
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
		new UserFouceModel(context).reset();
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, UserLoginActivity.class);
		context.startActivity(intent);
		ToastUtils.getInstance(context).showText(context.getResources().getString(R.string.logoutSuccess));
		EventBus.getDefault().post(new FinishEvent());

	}

	/**
	 * 获取当前用户
	 * 
	 * @param context
	 * @return
	 */
	public static OilUser getCurrentUser(Context context) {

		OilUser oilUser = new OilUser();
		String name = (String) SharedPreferenceUtils.getParam(context, Constants.USER_INFO_SHARED, Constants.NAME, "");
		String corpName = (String) SharedPreferenceUtils.getParam(context, Constants.USER_INFO_SHARED,
				Constants.CORP_NAME, "");
		String userPhone = (String) SharedPreferenceUtils.getParam(context, Constants.USER_INFO_SHARED,
				Constants.USER_PHONE, "");
		String userName = (String) SharedPreferenceUtils.getParam(context, Constants.USER_INFO_SHARED,
				Constants.USER_NAME, "");
		String cuuid = (String) SharedPreferenceUtils.getParam(context, Constants.USER_INFO_SHARED, Constants.CUUID,
				"");
		String pwd = (String) SharedPreferenceUtils.getParam(context, Constants.USER_INFO_SHARED, Constants.USER_PWD,
				"");
		oilUser.setName(name);
		oilUser.setCorpName(corpName);
		oilUser.setCuuid(cuuid);
		oilUser.setPhone(userPhone);
		oilUser.setUserName(userName);
		oilUser.setAccountPwd(pwd);
		return oilUser;
	}

	/**
	 * 用户登录
	 * 
	 * @param context
	 * @param userName
	 * @param accountPwd
	 * @param loginListener
	 */
	public static void Login(final Context context, final String userName, final String accountPwd,
			final onLoginListener loginListener) {
		if (null != userName && null != accountPwd) {

			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = telephonyManager.getDeviceId();

			MyRequestParams params = new MyRequestParams(context);
			params.put("phone", userName);
			params.put("password", StringUtils.MD5(accountPwd));
			params.put("imei", imei);
			params.put("ver", CommonUtil.getAppInfo(context).get("name"));
			HttpTool.netRequest(context, params, new OnReturnListener() {

				@Override
				public void onReturn(String jsString) {
					// TODO Auto-generated method stub
					try {
						JSONObject obj = new JSONObject(jsString).getJSONObject("data");
						if (obj.getString("login").equals("1")) {

							loginListener.onSuccess("100", jsString);

						} else {
							// 登录失败
							String errorMessage = obj.getString("message");
							loginListener.onError(Error_AccountInfoMiss, errorMessage);
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

	public static void changeUserPwd(final Context context, String newPwd,
			final OnCommonBusListener commonBusListener) {
		MyRequestParams oilParams = new MyRequestParams(context);
		oilParams.put("password", StringUtils.MD5(newPwd));
		HttpTool.netRequestNoCheck(context, oilParams, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				try {
					JSONObject jo = new JSONObject(jsString);
					String modify = jo.getJSONObject("data").getString("modify");
					if (modify.equals("1")) {
						commonBusListener
								.onSucceed(context.getResources().getText(R.string.resetPwdSucceed).toString());
					} else {
						commonBusListener
								.onSucceed(context.getResources().getText(R.string.resetPwdUnSucceed).toString());
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, Constants.URL_CHANGEPWD, false);
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
