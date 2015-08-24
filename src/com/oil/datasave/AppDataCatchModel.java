package com.oil.datasave;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import android.content.Context;
import com.loopj.android.http.RequestParams;
import com.oil.iface.OnDataReturnListener;
import com.oil.inter.OnReturnListener;
import com.oil.utils.FileUtils;
import com.oil.utils.HttpTool;
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
	public void setOnDataReturnListener(String fileName, boolean isAutoCatched,
			boolean isCatchFirst, OnDataReturnListener returnListener) {
		// TODO Auto-generated method stub
		this.returnListener = returnListener;
		Path = context.getExternalCacheDir().getAbsolutePath() + "/" + fileName;
		if (fileName != null) {
			if (isCatchFirst) {
				
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
private String getDataViaTimeCheck(String fileName)
{
	return fileName;}
	private void getNetData(final boolean isAutoCatched) {
		// TODO Auto-generated method stub
		HttpTool.netRequest(context, params, new OnReturnListener() {

			@Override
			public void onReturn(String jsString) {
				// TODO Auto-generated method stub
				try {
					if (isAutoCatched) {
						new FileUtils().savaData(Path,
								StringUtils.convertStringToIs(jsString));
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
		FileUtils.deleteFile(new File(context.getExternalCacheDir()
				.getAbsolutePath()));
	}

}
