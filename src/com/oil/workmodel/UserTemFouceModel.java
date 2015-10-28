package com.oil.workmodel;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.oil.bean.Constants;
import com.oil.bean.MyRequestParams;
import com.oil.bean.OilUser;
import com.oil.datasave.AppDataCatchModel;
import com.oil.iface.OnDataReturnListener;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;
import com.oil.utils.ObjectConvertUtils;

import android.content.Context;
import android.util.Log;

/**
 * 用户数据组收藏 相关操作
 * 
 * @author xu
 *
 */
public class UserTemFouceModel {
	String CatchName = "UserTemFouceModel.json";
	public List<Map<String, Object>> mapList;
	public static UserTemFouceModel temFouceModel;

	public static UserTemFouceModel getInstace() {
		if (temFouceModel == null) {
			temFouceModel = new UserTemFouceModel();
		}
		return temFouceModel;
	};

	private UserTemFouceModel() {
	}

	public void setUserTemList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}

	/**
	 * 获取用户数据组关注（内存==》缓存==》网络）
	 * 
	 * @param context
	 * @param temFouceCallBack
	 */
	public void getUserTemFou(Context context, onTemFouceCallBack temFouceCallBack) {
		if (mapList != null) {
			// return mapList;
			temFouceCallBack.onTemFouceReturn(mapList);
		} else {
			getData(context, temFouceCallBack, true, true);
		}
		// return mapList;

	}

	public void upDateUserTemFou(Context context, onTemFouceCallBack temFouceCallBack) {
		getData(context, temFouceCallBack, true, false);
	}

	/**
	 * 
	 * @param tempId
	 * @return
	 */
	public boolean isFouced(String tempId) {

		if (mapList != null) {
			for (int i = 0; i < mapList.size(); i++) {
				if (mapList.get(i).get("temp_id").toString().equals(tempId)) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * 
	 * @param tempId
	 *            (数据组id)
	 * @param userId
	 *            (用户id)
	 * @param action
	 *            (意图)1:添加关注 2：取消关注
	 */
	public void changeTemFouce(final Context context, String tempId, String userId, String action,
			final onTemFouceCallBack temFouceCallBack) {
		String url = Constants.URL_ChangeTemFouce;
		MyRequestParams params = new MyRequestParams(context);
		params.put("tempId", Float.valueOf(tempId).intValue() + "");
		params.put("action", action);
		params.put("userId", userId);
		HttpTool.netRequestNoCheck(context, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				try {
					JSONObject jo = new JSONObject(jsString);
					String state = jo.getString("state");
					if (state.equals("1")) {
						upDateUserTemFou(context, temFouceCallBack);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, url, false);

	}

	private void getData(Context context, final onTemFouceCallBack temFouceCallBack, boolean isAutoCatch,
			boolean isCatchFirsh) {
		String url = Constants.URl_GetUserTemFou;
		MyRequestParams params = new MyRequestParams(context);
		OilUser oilUser = OilUser.getCurrentUser(context);
		params.put("userId", oilUser.getCuuid());

		AppDataCatchModel catchModel = new AppDataCatchModel(context, url, params);
		catchModel.setOnDataReturnListener(oilUser.getCuuid() + CatchName, isAutoCatch, isCatchFirsh,
				new OnDataReturnListener() {

					@Override
					public void onDataReturn(String content) {
						// TODO Auto-generated method stub
						Log.e("UserTemFouceModel====>", content);
						if (temFouceCallBack != null) {
							ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
							try {
								JSONObject jo = new JSONObject(content);
								setUserTemList(ocu.convert(jo.getString("data")));
								temFouceCallBack.onTemFouceReturn(mapList);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				});
	}

	public interface onTemFouceCallBack {
		void onTemFouceReturn(List<Map<String, Object>> maps);
	}
}
