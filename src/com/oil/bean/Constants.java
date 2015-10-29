package com.oil.bean;

import android.os.Environment;
import android.renderscript.ScriptIntrinsicYuvToRGB;

public class Constants {

	public static int PageType_info = 1;// 资讯
	public static int PageType_data = 2;// 数据——数据库
	public static int PageType_price = 3;// 数据——价格库
	public static int pageType_shangji = 4;// 商机

	public static boolean isNetWorkDialogExist = false; // 网络提醒的对话框是否已经启动
	public static boolean isRequestFailDialogExist = false; // 请求失败的提醒对话框是否已经弹出
	/**
	 * url
	 */
	public static String baseIp = "http://192.168.1.91:8080/";
		public static String IP = baseIp + "oilWeb/"; //
//	 public static String IP = "http://info.oilchem.net/";
	public static String oilChemIp = "http://gl.oilchem.net/" + "oilchem/";

	// public static String oilChemIp = baseIp + "oilchem/";
	public static final String LOGIN = IP + "app/user/mobileLogin";// 登录
	public static final String REGIST = IP + "app/reg/regist"; // 注册
	public static final String MULTI_ACCOUNT_LOGIN = IP + "app/user/selLogUser"; // 多用户选择登录
	public static final String LOGOUT = IP + "app/user/userLogout"; // 注销
	public static final String URL_CHANGEPWD = IP + "app/user/updPwd";// 更改用户密码
	public static final String URL_GET_SMS_CODE = IP + "app/user/sendSmsVkey/1";// 获取短信验证码
	public static final String URL_SMS_LOGIN = IP + "app/user/smsLogin";// 验证码登录

	public static final String URL_GETPROSTRUCTURE = IP + "app/oilinfo/getProStructure";// 获取产品结构
	public static final String URL_GETUSERFOUCE = IP + "app/oilinfo/getUserFoucedProduct";// 获取用户关注产品
	public static final String URL_GETPRODDATA = IP// 获取产品数据详情
			+ "app/oilinfo/getProductChainAll";
	public static final String URL_GET_PRO_GROUP = IP// 获取产品数据单元组
			+ "app/oilinfo/getProductTemList";
	public static final String URL_GET_PRO_PAGEDATA = IP// 获取产品数据单元组下的数据
			+ "app/oilinfo/getDataItemByTemId";

	public static final String URL_GETPRODATALIST = IP + "app/oilinfo/getProDataHisList";// 获取产品历史数据列表
	public static final String URL_USERFOUCECHANGE = IP + "app/oilinfo/proGzViaApp";// 关注与取消关注
	public static final String URl_ProChart = IP + "app/oilinfo/queryProductChainAllChart";// 获取产品图表数据
	public static final String URL_APPUODATE = IP + "app/sys/chkAppVer";// app检查更新
	public static final String URL_GRTSHANGJI = IP + "app/gq/searchgq";// 获取商机供求资源

	public static final String URL_getGQ = IP + "app/gq/gqdetail";// 获取供求详情
	public static final String URl_getGqList = IP + "app/gq/getmygq/";// 获取供求列表
																		// 0：供
																		// 1：求2：gong3:供求
	public static final String URL_getGqPhone = IP + "app/gq/getPhone";// 获取供求电话

	public static final String URL_getCompantNews = oilChemIp + "app/getCompanyNew";// 获取公司新闻动态
	public static final String URL_ChangeTemFouce = IP + "app/oilinfo/changeUserTemFouce";// 添加
																							// 删除用户数据组
	public static final String URl_GetUserTemFou = IP + "app/oilinfo/getUserTemFouce";// 获取用户数据组关注
	/**
	 * sharedPreference
	 */
	public static final String USER_INFO_SHARED = "userInfoSharedPreference";
	public static final String USER_PHONE = "userPhone"; // 用来登录
	public static final String USER_NAME = "userName";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String LOGIN_STATE = "isLogin"; // 登录状态 true:已登录
	public static final String CUUID = "cuuid";
	public static final String TIME_GAP = "timeGap";
	public static final String NAME = "name";
	public static final String CORP_NAME = "corpName";
	public static final String CORP_PROVINCE = "corpProvince";
	public static final String USER_PWD = "userpwd";
	/**
	 * path
	 */
	public static final String BasePath = Environment.getExternalStorageDirectory().getAbsolutePath();// 基地址
	public static final String PathAppInit = BasePath + "/init";// 初始化文件地址

}
