package com.oil.bean;

public class MyConfig {
	public static final String IP =  Constants.IP;
	public static final String LOGIN = IP + "app/user/mobileLogin";// ��¼
	public static final String MULTI_ACCOUNT_LOGIN = IP + "app/user/selLogUser"; // ���û�ѡ���¼
	public static final String GET_MY_INFO = IP + "app/user/obtainuser"; // ��ȡ�û���Ϣ
	public static final String GET_USER_INFO = IP + "app/user/compinterface"; // ��ҵ��ϸ
	public static final String GETCODE = IP + "app/user/sendSmsVkey/1"; // ��ȡ��֤��
	public static final String VERIFY_LOGIN = IP + "app/user/smsLogin"; // ��֤���¼
	public static final String VERIFY = IP + "app/user/updPwd"; // �޸�������֤
	public static final String CHECK_UPDATE = IP + "app/sys/chkAppVer";// ������
	public static final String RECORD_CALL_TIME = IP + "app/order/sendCallTime"; // ��¼����绰��ʱ��
	public static final String LOGOUT = IP + "app/user/userLogout"; // ע��
	public static final String REGIST = IP + "app/reg/regist"; // ע��
	public static final String GET_PRODUCT_LIST = IP
			+ "app/product/pronamelist"; // ���ݹؼ��ֻ�ȡ��Ʒ�б�
	public static final String GET_PRODUCT_INFO = IP + "app/gq/gqdetail"; // ��ȡ��������
	public static final String DELETE_PRODUCT = IP + "app/gq/delgq"; // ɾ������
	public static final String PUBLISH = IP + "app/gq/addmygq"; // �������޸Ĺ���
	public static final String SEARCH = IP + "app/gq/searchgq"; // ��ѯ�����б�
	public static final String GET_PUBLISHLIST = IP + "app/gq/getmygq/2"; // ��ȡ�ҵĻ��û��Ĺ����б�
	public static final String GET_PUBLISHLIST_SUPPLY = IP + "app/gq/getmygq/1";// ��ȡ�ҵĻ��û��Ĺ�Ӧ�б�
	public static final String GET_PUBLISHLIST_DEMAND = IP + "app/gq/getmygq/0"; // ��ȡ�ҵĻ��û������б�
	public static final String ORDER = IP + "app/order/placecancelOrder"; // �µ���ȡ������
	public static final String GET_MY_ORDERLIST = IP
			+ "app/order/getMyOrderList"; // ��ȡ���µĶ����б�
	public static final String GET_MY_RECEIVE_ORDERLIST = IP
			+ "app/order/receivedOrderList"; // ��ȡ�ҽӵ��Ķ����б�
	public static final String GET_COLLECTEDLIST = IP + ""; // ��ȡ�ղ��б�
	public static final String ADD_OR_CANCEL_COLLECT = IP + ""; // ��ӻ�ȡ���ղ�
	public static final String GET_CONCERNED_LIST = IP + ""; // ��ȡ��ע�б�
	public static final String ADD_OR_CANCEL_CONCERN = IP + ""; // ��ӻ�ȡ����ע
	public static final String DELETE_ORDER = IP + ""; // ɾ������
	public static final String MATCH = IP + "app/gq/gqmatchlist"; // ����ƥ��
	public static final String GET_NEWS = IP + "app/gq/getPushMessage"; // ��ѯ
	public static final String ADVERTISEMENT = "http://gl.oilchem.net/oilchem/newspageadv/queryPadByPpo/2"; // ���
	public static final String REFRESH_PUBLISHLIST = IP + "app/gq/refreshtime"; // ˢ�¹����б�
	public static final String GET_CONNECT_PHONE = IP + "app/gq/getPhone"; // �������Ӧ����ϵ�绰
	
	
	
	

	public static boolean isNetWorkDialogExist = false; // �������ѵĶԻ����Ƿ��Ѿ�����
	public static boolean isRequestFailDialogExist = false; // ����ʧ�ܵ����ѶԻ����Ƿ��Ѿ�����
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
	public static final String GET_ORDERLIST_TIMESTAMP="get_orderIdList_timestamp"; //��ȡ����id�б��ʱ��

	public static String ATTENT_TO_CALL_FLAG = "attent_to call_sharedPreference";
	public static final String ATTENT_TO_CALL_NUMBER = "attent_to call_number";
	public static final String ATTENT_TO_CALL_SDID = "attent_to_call_sdid";

	public static String GET_MY_PUBLISH_FLAG = "get_mypublish_sharedPreference";
	public static final String GET_MY_PUBLISH_ALL = "get_mypublish_all";
	public static final String GET_MY_PUBLISH_SUPPLY = "get_mypublish_supply";
	public static final String GET_MY_PUBLISH_DEMAND = "get_mypublish_demand";
	
	// ��ȡ��������Ϣ��ʱ��
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
	public static final int DELETE_MY_PUBLISH_RESULTCODE = 10; // ɾ���ҵĹ���
	public static final int GET_PRODUCTNAME_RESULTCODE = 20; // ��ò�Ʒ����
	public static final int GET_ORDERLIST_RESULTCODE = 30; // ��ҵ��ҳ/���������˻ص���ҳʱ���ض����б�
	public static final int DELETE_MY_RECEIVE_ORDER = 40; // ɾ�����յ��Ķ���

}
