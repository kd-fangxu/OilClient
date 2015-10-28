package com.oil.workmodel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oil.bean.OilUser;
import com.oil.utils.FileUtils;
import com.oil.utils.StringUtils;

import android.content.Context;

/**
 * 用户收藏操作
 * 
 * @author Administrator
 *
 */
public class UserFouceModel {
	public boolean isNeedUpdate = false;// 是否需要更新
	List<HashMap<String, String>> fouceList;
	String path;
	Context context;
	FileUtils fileUtils;
	Gson gson;

	public UserFouceModel(Context context) {
		this.context = context;
		gson = new Gson();
		fileUtils = new FileUtils();
		path = context.getExternalCacheDir().getAbsolutePath() + "/" + OilUser.getCurrentUser(context).getCuuid()
				+ "userFouceModel.json";
	}

	/**
	 * 获取收藏列表
	 * 
	 * @return
	 */
	public List<HashMap<String, String>> getFouceList() {

		try {
			fouceList = gson.fromJson(StringUtils.convertStreamToString(FileUtils.openFile(path)),
					new TypeToken<List<HashMap<String, String>>>() {
					}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}

		return fouceList;
	}

	/**
	 * 设置收藏列表
	 * 
	 * @param fouceList
	 */
	public void setFouceList(List<HashMap<String, String>> fouceList) {
		// this.fouceList = fouceList;

		String jsonContent = gson.toJson(fouceList);

		try {
			fileUtils.savaData(path, StringUtils.convertStringToIs(jsonContent));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public static UserFouceModel getInstance() {
	//
	// if (model == null) {
	// model = new UserFouceModel();
	// }
	// return model;
	// };
	/**
	 * 清空收藏
	 */
	public void reset() {

		fileUtils.deleteFile(new File(path));
	}

	/**
	 * 产品是否已经收藏
	 * 
	 * @param pro_id
	 * @return
	 */
	public boolean isFouced(String pro_id) {
		if (getFouceList() != null) {
			for (int i = 0; i < fouceList.size(); i++) {
				if (fouceList.get(i).get("pro_id").equals(Float.valueOf(pro_id).intValue() + "")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据id移除收藏
	 * 
	 * @param proId
	 */
	public void removeItemByProId(String proId) {
		if (getFouceList() != null) {
			for (int i = 0; i < fouceList.size(); i++) {
				if (fouceList.get(i).get("pro_id").equals(Float.valueOf(proId).intValue() + "")) {
					fouceList.remove(i);
				}
			}
			setFouceList(fouceList);
		}
	}
}
