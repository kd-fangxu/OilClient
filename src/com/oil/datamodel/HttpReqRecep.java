package com.oil.datamodel;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.oil.bean.AsyncHttpClientUtil;
import com.oil.bean.MyConfig;
import com.oil.bean.MyRequestParams;
import com.oil.bean.ProductBean;
import com.oil.inter.OnReturnListener;
import com.oil.utils.HttpTool;

/**
 * http请求接待类
 * 
 * @author Administrator
 *
 */
public class HttpReqRecep {
	/**
	 * 发布供求
	 * 
	 */
	public static void publish(final Context context, ProductBean product,
			final OnReturnListener listener) {
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance(context);
		MyRequestParams params = new MyRequestParams(context);
		params.put("act", "update");
		params.put("sdid", product.sdid);
		params.put("tb", product.tb);
		params.put("name", java.net.URLEncoder.encode(java.net.URLEncoder
				.encode(product.name)));
		params.put("phone", product.phone);
		params.put("cpname", java.net.URLEncoder.encode(java.net.URLEncoder
				.encode(product.cpName)));
		params.put("cpId", product.cpId);
		params.put("salesArea", product.areaId);
		params.put("province", product.provincesId);
		params.put("model", java.net.URLEncoder.encode(java.net.URLEncoder
				.encode(product.model)));
		params.put("price", product.price);
		params.put("stock", product.stock);
		params.put("remark", java.net.URLEncoder.encode(java.net.URLEncoder
				.encode(product.remark)));
		HttpTool.netRequest(context, params, listener, MyConfig.PUBLISH, true);
	}

	/**
	 * 获取供求详情
	 * 
	 * @param context
	 * @param sdid
	 *            供求id
	 * 
	 * @param listener
	 */
	public static void getProductInfo(final Context context, String sdid,
			final OnReturnListener listener) {
		MyRequestParams params = new MyRequestParams(context);
		params.put("sdid", sdid);

		HttpTool.netRequest(context, params, listener,
				MyConfig.GET_PRODUCT_INFO, true);
	}

	/**
	 * 删除供求
	 */
	public static void deleteProduct(final Context context, String sdid,
			final OnReturnListener listener) {
//		com.loopj.android.http.RequestParams params = new com.loopj.android.http.RequestParams();
		MyRequestParams params = new MyRequestParams(context);
		params.put("sdid", sdid);

		HttpTool.netRequest(context, params, listener, MyConfig.DELETE_PRODUCT,
				true);
	}
	
	/**
	 * 获取产品种类
	 */
	public static void getProductList(final Context context, String key,
			final OnReturnListener listener) {
		MyRequestParams params = new MyRequestParams(context);
		params.put("q",
				java.net.URLEncoder.encode(java.net.URLEncoder.encode(key)));
		params.put("pagesize", 40 + "");

		HttpTool.netRequest(context, params, listener, MyConfig.GET_PRODUCT_LIST, false);
	}

}
