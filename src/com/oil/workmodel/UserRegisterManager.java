package com.oil.workmodel;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.oil.bean.Constants;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.SharedPreferenceUtils;
import com.oil.utils.StringUtils;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

/**
 * 用户注册管理类
 * 
 * @author Administrator
 *
 */
public class UserRegisterManager {
	String sp_Tag = "registerTag";
	String requestUrl = "app/UserRegLogManager/userRegLog";
	String userName;
	String pwd;
	String AppName;
	private String device_type = "android";
	Context context;

	public UserRegisterManager(Context context, String userName, String pwd, String appName) {
		this.userName = userName;
		this.pwd = pwd;
		this.AppName = appName;
		this.context = context;
	}

	public UserRegisterManager(Context context) {
		this.context = context;
	}

	public String getDevice_Id() {

		return ((TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE)).getDeviceId();
	}

	public String getApp_PackageName() {
		return context.getPackageName();
	}

	public String getApp_Version() {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode + "";
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 上传注册信息
	 */
	public void doPostRegisterInfo() {

		RequestParams param = new RequestParams();
		param.put("userName", userName);
		param.put("pwd", StringUtils.MD5(pwd));
		param.put("Appid", AppName);
		param.put("device_id", getDevice_Id());
		param.put("packageName", getApp_PackageName());
		param.put("app_version", getApp_Version());
		param.put("device_type", device_type);
		HttpTool.netRequestNoCheck(context, param, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				try {
					JSONObject jObject = new JSONObject(jsString);
					String status = jObject.getString("status");
					if (status.equals("1")) {
						removeRegInfo();
					} else {
						addRegInfo();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}, Constants.IP + requestUrl, false);

	}

	private void removeRegInfo() {
		// TODO Auto-generated method stub
		SharedPreferenceUtils.setParam(context, sp_Tag, shareKey_isHasNewUser, false);
		SharedPreferenceUtils.setParam(context, sp_Tag, "userName", null);
		SharedPreferenceUtils.setParam(context, sp_Tag, "pwd", null);
	}

	private void addRegInfo() {
		SharedPreferenceUtils.setParam(context, sp_Tag, shareKey_isHasNewUser, true);
		SharedPreferenceUtils.setParam(context, sp_Tag, "userName", userName);
		SharedPreferenceUtils.setParam(context, sp_Tag, "pwd", pwd);
	}

	String shareKey_isHasNewUser = "isHasNewUser";

	/**
	 * 自动上传失败的注册信息
	 */
	public void doAutoPost() {
		boolean isHasNewUser = (Boolean) SharedPreferenceUtils.getParam(context, sp_Tag, shareKey_isHasNewUser, false);
		if (isHasNewUser) {
			userName = (String) SharedPreferenceUtils.getParam(context, sp_Tag, "userName", "");
			pwd = (String) SharedPreferenceUtils.getParam(context, sp_Tag, "pwd", "");
			this.AppName = AppVersionManager.OilAppTag + "";
			this.doPostRegisterInfo();
		}
	}
}
