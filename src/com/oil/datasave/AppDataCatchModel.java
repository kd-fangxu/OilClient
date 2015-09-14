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
 * ���ݻ������
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

	private String Tag_CreateTime = "createTime";// ����ʱ��
	private String Tag_TimeEffect = "timeEffect";// ��Чʱ��
	private int Days = 24 * 60 * 60 * 1000;// �������뻻��
	private long DefaultTimestay = 1 * Days;// Ĭ����Чʱ��Ϊһ��
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
	 * ��ȡ�������ݣ�˽�е��ã�
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
	 *            ���ļ�����Ψһ��
	 * @param isAutoCatched
	 *            ���Ƿ��Զ�����
	 * @param isCatchFirst
	 *            ���Ƿ�ӻ���ȡ����
	 * @param returnListener
	 *            ���ص��ӿ�
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
	 * �����ļ�ʱ��ʧЧ
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
								System.currentTimeMillis());// ��¼�ļ�����ʱ��
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
	 * ���޻���ʱ���� what will be done when no catch
	 */
	// abstract void onNoCatch();
	/**
	 * ��ջ���
	 */
	public void clearCatch() {
		FileUtils.deleteFile(new File(context.getExternalCacheDir().getAbsolutePath()));
	}

}
