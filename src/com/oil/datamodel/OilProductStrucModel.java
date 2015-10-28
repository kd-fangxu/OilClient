package com.oil.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.oil.bean.Constants;
import com.oil.utils.FileUtils;
import com.oil.utils.ObjectConvertUtils;
import com.oil.utils.StringUtils;
import com.oil.workmodel.AppInit;

import android.util.Log;

/**
 * 产品结构model
 * 
 * @author Administrator
 *
 */
public class OilProductStrucModel {
	public List<Map<String, Object>> productWangList;
	public List<Map<String, Object>> productList;
	public List<Map<String, Object>> productChainList;

	public OilProductStrucModel() {
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

		String jsonContent = StringUtils.convertStreamToString(FileUtils
				.openFile(Constants.PathAppInit + "/"
						+ AppInit.fileName_proStruct));
		Log.e(jsonContent, jsonContent);
		ObjectConvertUtils<List<Map<String, Object>>> ocu = new ObjectConvertUtils<List<Map<String, Object>>>();
		JSONObject jo;
		try {
			jo = new JSONObject(jsonContent);
			setProductWangList(ocu.convert(jo.getString("productWangList")));
			setProductList(ocu.convert(jo.getString("productList")));
			setProductChainList(ocu.convert(jo.getString("productChainList")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Map<String, Object>> getProductWangList() {
		return productWangList;
	}

	public void setProductWangList(List<Map<String, Object>> productWangList) {
		this.productWangList = productWangList;
	}

	public List<Map<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(List<Map<String, Object>> productList) {
		this.productList = productList;
	}

	public List<Map<String, Object>> getProductChainList() {
		return productChainList;
	}

	public void setProductChainList(List<Map<String, Object>> productChainList) {
		this.productChainList = productChainList;
	}

	public Map<String, Object> getItemFromWangList(String wang_id) {
		for (int i = 0; i < productWangList.size(); i++) {
			if (productWangList.get(i).get("wang_id").toString()
					.equals(wang_id + "")) {
				return productWangList.get(i);

			}
		}
		return null;
	}

	public Map<String, Object> getItemFromChainList(String chan_id) {
		for (int i = 0; i < productChainList.size(); i++) {
			if (productChainList.get(i).get("chan_id").toString()
					.equals(chan_id + "")) {
				return productChainList.get(i);
			}
		}
		return null;
	}

	public Map<String, Object> getItemFromProductList(String pro_id) {

		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).get("pro_id").toString().equals(pro_id + "")) {
				return productList.get(i);
			}
		}

		return null;

	}

	public List<Map<String, Object>> getChainListByWangId(String wang_id) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < productChainList.size(); i++) {
			if (productChainList.get(i).get("wang_id").toString()
					.equals(wang_id)) {
				mapList.add(productChainList.get(i));
			}
		}
		return mapList;
	}

	public List<Map<String, Object>> getProductListByChainId(String chain_id) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).get("chan_id").toString()
					.equals(chain_id + "")) {
				mapList.add(productList.get(i));
			}
		}
		return mapList;
	}

	/**
	 * 根据产品关键词获取产品列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getProductListsByProName(String keyWord) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).get("pro_cn_name").toString()
					.contains(keyWord)) {
				resultList.add(productList.get(i));
			}
		}
		return resultList;
	}

	/**
	 * 根据pro-id获取单个产品数据
	 * 
	 * @param pro_id
	 * @return
	 */
	public Map<String, Object> getProItem(String pro_id) {
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).get("pro_id").toString().equals(pro_id)) {
				return productList.get(i);
			}
		}
		return null;

	}

	/**
	 * 根据chan-id获取产品组数据
	 * 
	 * @param pro_id
	 * @return
	 */
	public Map<String, Object> getChanItem(String chan_id) {
		for (int i = 0; i < productChainList.size(); i++) {
			if (productChainList.get(i).get("chan_id").toString()
					.equals(chan_id)) {
				return productChainList.get(i);
			}
		}
		return null;

	}
}
