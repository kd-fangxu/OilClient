package com.oil.bean;

public class MyConfig {
	public static final String IP =  Constants.IP;
	public static final String LOGIN = IP + "app/user/mobileLogin";// 登录
	public static final String MULTI_ACCOUNT_LOGIN = IP + "app/user/selLogUser"; // 多用户选择登录
	public static final String GET_MY_INFO = IP + "app/user/obtainuser"; // 获取用户信息
	public static final String GET_USER_INFO = IP + "app/user/compinterface"; // 企业明细
	public static final String GETCODE = IP + "app/user/sendSmsVkey/1"; // 获取验证码
	public static final String VERIFY_LOGIN = IP + "app/user/smsLogin"; // 验证码登录
	public static final String VERIFY = IP + "app/user/updPwd"; // 修改密码验证
	public static final String CHECK_UPDATE = IP + "app/sys/chkAppVer";// 检查更新
	public static final String RECORD_CALL_TIME = IP + "app/order/sendCallTime"; // 记录拨打电话的时间
	public static final String LOGOUT = IP + "app/user/userLogout"; // 注销
	public static final String REGIST = IP + "app/reg/regist"; // 注册
	public static final String GET_PRODUCT_LIST = IP
			+ "app/product/pronamelist"; // 根据关键字获取产品列表
	public static final String GET_PRODUCT_INFO = IP + "app/gq/gqdetail"; // 获取供求详情
	public static final String DELETE_PRODUCT = IP + "app/gq/delgq"; // 删除供求
	public static final String PUBLISH = IP + "app/gq/addmygq"; // 发布或修改供求
	public static final String SEARCH = IP + "app/gq/searchgq"; // 查询供求列表
	public static final String GET_PUBLISHLIST = IP + "app/gq/getmygq/2"; // 获取我的或用户的供求列表
	public static final String GET_PUBLISHLIST_SUPPLY = IP + "app/gq/getmygq/1";// 获取我的或用户的供应列表
	public static final String GET_PUBLISHLIST_DEMAND = IP + "app/gq/getmygq/0"; // 获取我的或用户的求购列表
	public static final String ORDER = IP + "app/order/placecancelOrder"; // 下单或取消订单
	public static final String GET_MY_ORDERLIST = IP
			+ "app/order/getMyOrderList"; // 获取我下的订单列表
	public static final String GET_MY_RECEIVE_ORDERLIST = IP
			+ "app/order/receivedOrderList"; // 获取我接到的订单列表
	public static final String GET_COLLECTEDLIST = IP + ""; // 获取收藏列表
	public static final String ADD_OR_CANCEL_COLLECT = IP + ""; // 添加或取消收藏
	public static final String GET_CONCERNED_LIST = IP + ""; // 获取关注列表
	public static final String ADD_OR_CANCEL_CONCERN = IP + ""; // 添加或取消关注
	public static final String DELETE_ORDER = IP + ""; // 删除订单
	public static final String MATCH = IP + "app/gq/gqmatchlist"; // 供求匹配
	public static final String GET_NEWS = IP + "app/gq/getPushMessage"; // 轮询
	public static final String ADVERTISEMENT = "http://gl.oilchem.net/oilchem/newspageadv/queryPadByPpo/2"; // 广告
	public static final String REFRESH_PUBLISHLIST = IP + "app/gq/refreshtime"; // 刷新供求列表
	public static final String GET_CONNECT_PHONE = IP + "app/gq/getPhone"; // 请求供求对应的联系电话
	
	
	
	

	public static boolean isNetWorkDialogExist = false; // 网络提醒的对话框是否已经启动
	public static boolean isRequestFailDialogExist = false; // 请求失败的提醒对话框是否已经弹出
	public static final String WECHAT_APP_ID = "wx8f57f83936dde8a4",
			WECHAT_APP_SECRET = "233baf86d0baac1187236361dca89af0";
	public static final String BUGLY_APP_ID = "900003221",
			AppKey = "w5lIU4iG6RXSr9m7";

	/**
	 * sharedPreference
	 */
	public static String NOTIFY_FLAG = "notify_sharedPreference";
	public static final String NOTIFY_OR_NOT = "notify";
	public static final String NOTIFY_SOUND = "notify_sound";

	public static String MESSAGE_NUM_FLAG = "message_sharedPreference";
	public static final String NEW_MESSAGE_NUM = "newMessageNum";
	public static final String NEW_PLACEORDER_NUM = "newPlaceOrderNum";
	public static final String NEW_RECEIVEORDER_NUM = "newReceiveOrderNum";

	public static  String REFRESH_TIME_FLAG = "refreshTime_sharedPreference";
	public static final String HOME_REFRESH_TIME = "homeRefreshTime";
	public static final String GET_ORDERLIST_TIMESTAMP="get_orderIdList_timestamp"; //获取订单id列表的时间

	public static String ATTENT_TO_CALL_FLAG = "attent_to call_sharedPreference";
	public static final String ATTENT_TO_CALL_NUMBER = "attent_to call_number";
	public static final String ATTENT_TO_CALL_SDID = "attent_to_call_sdid";

	public static String GET_MY_PUBLISH_FLAG = "get_mypublish_sharedPreference";
	public static final String GET_MY_PUBLISH_ALL = "get_mypublish_all";
	public static final String GET_MY_PUBLISH_SUPPLY = "get_mypublish_supply";
	public static final String GET_MY_PUBLISH_DEMAND = "get_mypublish_demand";
	
	// 获取订单及消息的时间
	public static String GET_MESSAGE_FLAG = "get_message_sharedPreference";
	public static final String MESSAGE_TIMESTAMP = "messageTimestamp";
	public static final String MY_PLACE_ORDER_TIMESTAMP = "myPlaceOrder_timestamp";
	public static final String MY_RECEIVE_ORDER_TIMESTAMP = "myReceiveOrder_timestamp";
	

	// user sharedpreference
	public static final String USER_INFO_FLAG = "user_info_sharedpreference";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String LOGIN_STATE = "islogin";
	public static final String NAME = "name";
	public static final String CORP_NAME = "corpName";
	public static final String PHONE = "phone";
	public static final String USER_NAME = "userName";
	public static final String USER_ACCOUNT = "userAccount";
	public static final String CUUID = "cuuid";
	public static final String TIME_GAP = "timeGap";

	public static final String NETWORK_NOTIFY_FLAG = "netword_notify_sharedference";
	public static final String IS_NOTIFY = "isNetNotAvailableNotify";
	/**
	 * startActivityForResult
	 */
	public static final int DELETE_MY_PUBLISH_RESULTCODE = 10; // 删除我的供求
	public static final int GET_PRODUCTNAME_RESULTCODE = 20; // 获得产品名称
	public static final int GET_ORDERLIST_RESULTCODE = 30; // 企业主页/供求详情退回到首页时返回订单列表
	public static final int DELETE_MY_RECEIVE_ORDER = 40; // 删除我收到的订单

}
