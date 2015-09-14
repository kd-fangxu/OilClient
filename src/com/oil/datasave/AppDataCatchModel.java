package com.oil.datasave;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.R.string;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.oil.iface.OnDataReturnListener;
import com.oil.inter.OnReturnListener;
import com.oil.utils.FileUtils;
import com.oil.utils.HttpTool;
import com.oil.utils.SharedPreferenceUtils;
import com.oil.utils.StringUtils;

/**
 * 数据缓存相关
 * 
 * @author Administrator
 *
 */
public class AppDataCatchModel {
	Context context;
	String url;
	RequestParams params;
	OnDataReturnListener returnListener;
	String Path;
	String fileName;

	private String Tag_CreateTime = "createTime";// 创建时间
	private String Tag_TimeEffect = "timeEffect";// 有效时间
	private int Days = 24 * 60 * 60 * 1000;// 天数毫秒换算
	private long DefaultTimestay = 1 * Days;// 默认有效时间为一天
	private long defaultCreateTime = 0;

	public AppDataCatchModel(Context context) {
		this.context = context;
	}

	public AppDataCatchModel(Context context, String url, RequestParams param) {
		this.context = context;
		this.url = url;
		this.params = param;
	}

	/**
	 * 获取缓存数据（私有调用）
	 * 
	 * @param fileName
	 * @return
	 */
	private String getCatchData(String fileName) {

		// File file = new File(Path);
		if (new File(Path).exists()) {
			InputStream is = FileUtils.openFile(Path);
			if (is != null) {

				return StringUtils.convertStreamToString(is);
			}
		}

		return null;
	}

	/**
	 * 
	 * @param fileName
	 *            ：文件名（唯一）
	 * @param isAutoCatched
	 *            ：是否自动缓存
	 * @param isCatchFirst
	 *            ：是否从缓存取数据
	 * @param returnListener
	 *            ：回掉接口
	 */
	public void setOnDataReturnListener(String fileName, boolean isAutoCatched, boolean isCatchFirst,
			OnDataReturnListener returnListener) {
		// TODO Auto-generated method stub
		this.returnListener = returnListener;
		this.fileName = fileName;
		Path = context.getExternalCacheDir().getAbsolutePath() + "/" + fileName;
		if (fileName != null) {
			if (isCatchFirst && isDataViaTimeCheck(fileName)) {

				String content = getCatchData(fileName);
				if (content == null) {
					if (params != null && url != null) {
						getNetData(isAutoCatched);
					}

				} else {
					returnListener.onDataReturn(content);
				}
			} else {
				if (params != null && url != null) {
					getNetData(isAutoCatched);
				}
			}

		}

	}

	/**
	 * 缓存文件时候失效
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean isDataViaTimeCheck(String fileName) {

		long createTime = (Long) SharedPreferenceUtils.getParam(context, sp_name, fileName + Tag_CreateTime,
				defaultCreateTime);
		long temTimeStay = (Long) SharedPreferenceUtils.getParam(context, sp_name, fileName + Tag_TimeEffect,
				DefaultTimestay);
		long currentTime = System.currentTimeMillis();

		// Log.e("timecheck", fileName + createTime + "__" + temTimeStay + "__"
		// + currentTime);
		if ((currentTime - createTime) <= temTimeStay) {
			return true;
		} else {
			new File(Path).deleteOnExit();
		}
		return false;
	}

	private String sp_name = "app_catch";

	private void getNetData(final boolean isAutoCatched) {
		// TODO Auto-generated method stub
		HttpTool.netRequest(context, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				try {
					if (isAutoCatched) {
						SharedPreferenceUtils.setParam(context, sp_name, fileName + Tag_CreateTime,
								System.currentTimeMillis());// 记录文件创建时间
						new FileUtils().savaData(Path, StringUtils.convertStringToIs(jsString));

						Log.e("timecheck", fileName + " Created ");
					}
					returnListener.onDataReturn(jsString);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, url, false);
	}

	/**
	 * 当无缓冲时操作 what will be done when no catch
	 */
	// abstract void onNoCatch();
	/**
	 * 清空缓存
	 */
	public void clearCatch() {
		FileUtils.deleteFile(new File(context.getExternalCacheDir().getAbsolutePath()));
	}

}
