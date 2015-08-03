package com.oil.bean;

import android.os.Environment;

public class Constants {

	public static int PageType_info = 1;// ��Ѷ
	public static int PageType_data = 2;// ���ݡ������ݿ�
	public static int PageType_price = 3;// ���ݡ����۸��
	public static int pageType_shangji = 4;// �̻�

	public static boolean isNetWorkDialogExist = false; // �������ѵĶԻ����Ƿ��Ѿ�����
	public static boolean isRequestFailDialogExist = false; // ����ʧ�ܵ����ѶԻ����Ƿ��Ѿ�����
	/**
	 * url
	 */
	public static String IP = "http://192.168.1.91:8080/oilWeb/"; //
	// "http://192.168.1.234:8080/oilWeb/";
	// public static String IP = "http://info.oilchem.net/";
	public static final String LOGIN = IP + "app/user/mobileLogin";// ��¼
	public static final String REGIST = IP + "app/reg/regist"; // ע��
	public static final String MULTI_ACCOUNT_LOGIN = IP + "app/user/selLogUser"; // ���û�ѡ���¼
	public static final String LOGOUT = IP + "app/user/userLogout"; // ע��
	public static final String URL_CHANGEPWD = IP + "app/user/updPwd";// �����û�����
	public static final String URL_GET_SMS_CODE = IP + "app/user/sendSmsVkey/1";// ��ȡ������֤��
	public static final String URL_SMS_LOGIN = IP + "app/user/smsLogin";// ��֤���¼

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
	public static final String URl_ProChart = IP
			+ "app/oilinfo/queryProductChainAllChart";// ��ȡ��Ʒͼ������
	public static final String URL_APPUODATE = IP + "app/sys/chkAppVer";// app������
	public static final String URL_GRTSHANGJI = IP + "app/gq/searchgq";// ��ȡ�̻�������Դ

	public static final String URL_getGQ = IP + "app/gq/gqdetail";// ��ȡ��������
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
	public static final String USER_PWD = "userpwd";
	/**
	 * path
	 */
	public static final String BasePath = Environment
			.getExternalStorageDirectory().getAbsolutePath();// ����ַ
	public static final String PathAppInit = BasePath + "/init";// ��ʼ���ļ���ַ

}
