package com.oil.workmodel;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.loopj.android.http.RequestParams;
import com.oil.bean.Constants;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.SharedPreferenceUtils;

/**
 * app��װ��Ϣ�ύ
 * 
 * @author Administrator
 *
 */
public class InstallInfoManager {

	String shareFlag_isPostIntallInfo = "isPostIntallInfo";
	String requestUrl = Constants.IP + "app/installmanage/addInstallLog";
	private String Device_Id; // �豸ID
	private String App_Id;// app��ʶ ����ͨ��1 ��Ѷ:2
	private String App_Version;// app�汾
	private String Device_Model;// �豸�ͺ�
	private String version_Releas;// �豸ϵͳ�汾��
	private String App_PackageName;// app����
	private Context context;

	public InstallInfoManager(Context context) {
		this.context = context;
	}

	public String getDevice_Id() {

		return ((TelephonyManager) context
				.getSystemService(Service.TELEPHONY_SERVICE)).getDeviceId();
	}

	public String getApp_Id() {
		return AppVersionManager.OilAppTag + "";
	}

	public String getApp_Version() {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode
					+ "";
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String getDevice_Model() {
		return Build.MODEL;
	}

	public String getVersion_Releas() {
		return Build.VERSION.RELEASE;
	}

	public String getApp_PackageName() {
		return context.getPackageName();
	}

	/**
	 * �ύ��װ��Ϣ
	 */
	public void doPostInstallInfo() {
		boolean isPost = (Boolean) SharedPreferenceUtils.getParam(context, this
				.getClass().getName(), shareFlag_isPostIntallInfo, false);
		if (!isPost) {
			RequestParams params = new RequestParams();
			params.put("Device_Id", this.getDevice_Id());
			params.put("App_Id", this.getApp_Id());
			params.put("App_Version", this.getApp_Version());
			params.put("Device_Model", this.getDevice_Model());
			params.put("version_Releas", this.getVersion_Releas());
			params.put("App_PackageName", this.getApp_PackageName());

			HttpTool.netRequestNoCheck(context, params, new OnReturnListener() {

				@Override
				public void onReturn(String jsString) {
					// TODO Auto-generated method stub
					JSONObject jObject;
					try {
						jObject = new JSONObject(jsString);
						if (jObject.getString("status").equals("1")) {
							SharedPreferenceUtils.setParam(context, this
									.getClass().getName(),
									shareFlag_isPostIntallInfo, true);
						} else {
							SharedPreferenceUtils.setParam(context, this
									.getClass().getName(),
									shareFlag_isPostIntallInfo, false);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}, requestUrl, false);
		}
	}
}
