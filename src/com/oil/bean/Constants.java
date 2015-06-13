package com.oil.bean;

import android.os.Environment;

public class Constants {
	public static int PageType_info = 1;// ��Ѷ
	public static int PageType_data = 2;// ����

	public static boolean isNetWorkDialogExist = false; // �������ѵĶԻ����Ƿ��Ѿ�����
	public static boolean isRequestFailDialogExist = false; // ����ʧ�ܵ����ѶԻ����Ƿ��Ѿ�����
	/**
	 * url
	 */
	public static final String IP = "http://192.168.1.91:8080/oilWeb/"; // "http://192.168.1.234:8080/oilWeb/";
	public static final String LOGIN = IP + "app/user/mobileLogin";// ��¼
	public static final String MULTI_ACCOUNT_LOGIN = IP + "app/user/selLogUser"; // ���û�ѡ���¼
	public static final String LOGOUT = IP + "app/user/userLogout"; // ע��
	public static final String URL_GETPROSTRUCTURE = IP
			+ "app/oilinfo/getProStructure";// ��ȡ��Ʒ�ṹ
	public static final String URL_GETUSERFOUCE = IP
			+ "app/oilinfo/getUserFoucedProduct";// ��ȡ�û���ע��Ʒ
	public static final String URL_GETPRODDATA = IP// ��ȡ��Ʒ��������
			+ "app/oilinfo/getProductChainAll";
	public static final String URL_GETPRODATALIST = IP
			+ "app/oilinfo/getProDataHisList";// ��ȡ��Ʒ��ʷ�����б�
	public static final String URL_USERFOUCECHANGE = IP
			+ "app/oilinfo/proGzViaApp";// ��ע��ȡ����ע
	/**
	 * sharedPreference
	 */
	public static final String USER_INFO_SHARED = "userInfoSharedPreference";
	public static final String USER_PHONE = "userPhone"; // ������¼
	public static final String USER_NAME = "userName";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String LOGIN_STATE = "isLogin"; // ��¼״̬ true:�ѵ�¼
	public static final String CUUID = "cuuid";
	public static final String TIME_GAP = "timeGap";
	public static final String NAME = "name";
	public static final String CORP_NAME = "corpName";
	public static final String CORP_PROVINCE = "corpProvince";
	/**
	 * path
	 */
	public static final String BasePath = Environment
			.getExternalStorageDirectory().getAbsolutePath();// ����ַ
	public static final String PathAppInit = BasePath + "/init";// ��ʼ���ļ���ַ

}
